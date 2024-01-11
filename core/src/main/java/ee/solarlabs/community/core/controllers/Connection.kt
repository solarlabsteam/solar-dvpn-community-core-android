package ee.solarlabs.community.core.controllers

import co.sentinel.cosmos.core.exception.WalletTaskError
import co.sentinel.dvpn.domain.core.exception.AccountError
import co.sentinel.dvpn.domain.core.exception.Failure
import co.sentinel.dvpn.domain.core.functional.requireLeft
import co.sentinel.dvpn.domain.core.functional.requireRight
import co.sentinel.dvpn.domain.features.dvpn.GetTunnel
import co.sentinel.dvpn.domain.features.dvpn.SetTunnelState
import co.sentinel.dvpn.domain.features.dvpn.model.ConnectionCredentials
import co.sentinel.dvpn.domain.features.dvpn.model.ConnectionEvent
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.ConnectionEventBus
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.DeleteConfiguration
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.DeleteConnection
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.DeleteSessions
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.GetConnection
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.PostConnection
import co.sentinel.dvpn.hub.exception.HubTaskError
import ee.solarlabs.community.core.extension.toError
import ee.solarlabs.community.core.mapper.connection.GetConnectionResponseMapper
import ee.solarlabs.community.core.model.ConnectionModelError.Companion.noQuotaLeft
import ee.solarlabs.community.core.model.ConnectionModelError.Companion.noSubscription
import ee.solarlabs.community.core.model.ConnectionModelError.Companion.nodeIsOffline
import ee.solarlabs.community.core.model.ConnectionModelError.Companion.signatureGenerationFailed
import ee.solarlabs.community.core.model.ConnectionModelError.Companion.tunnelIsAlreadyActive
import ee.solarlabs.community.core.model.Error
import ee.solarlabs.community.core.model.ErrorWrapper
import ee.solarlabs.community.core.model.HttpError
import ee.solarlabs.community.core.model.TunnelServiceError.Companion.tunnelNotFound
import ee.solarlabs.community.core.model.WalletServiceError.Companion.missingMnemonics
import ee.solarlabs.community.core.model.WalletServiceError.Companion.notEnoughTokens
import ee.solarlabs.community.core.model.connection.request.PostConnectionCredentialsRequest
import ee.solarlabs.community.core.model.connection.request.PostConnectionRequest
import ee.solarlabs.community.core.model.connection.response.ConnectionErrorResponse
import ee.solarlabs.community.core.model.connection.response.ConnectionResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

fun Application.routeConnection() {
    val deleteConnection: DeleteConnection by inject(DeleteConnection::class.java)
    val deleteConfiguration: DeleteConfiguration by inject(DeleteConfiguration::class.java)
    val deleteSessions: DeleteSessions by inject(DeleteSessions::class.java)
    val getConnection: GetConnection by inject(GetConnection::class.java)

    val connectionEventBus: ConnectionEventBus by inject(ConnectionEventBus::class.java)


    routing {

        webSocket("/echo") {
            try {
                // Handle events
                connectionEventBus.events.filter { it is ConnectionEvent.ConnectionStateChanged || it is ConnectionEvent.ConnectionError }
                    .collectLatest {
                        if (it is ConnectionEvent.ConnectionStateChanged) {
                            val result = if (it.isConnected) {
                                ConnectionResponse.connected
                            } else {
                                ConnectionResponse.disconnected
                            }
                            launch { sendSerialized(result) }
                        } else {
                            it as ConnectionEvent.ConnectionError
                            val error = unpackConnectionError(it.failure)
                            launch { sendSerialized(ConnectionErrorResponse(value = error.reason)) }
                        }

                    }

            } catch (e: ClosedReceiveChannelException) {
                Timber.e(e)
            } catch (e: Throwable) {
                Timber.e(e)
            }
        }

        /**
         * This method is used to create a connection to the specific node.
         */
        post("/api/connection") {
            val request =
                kotlin.runCatching { call.receive<PostConnectionRequest>() }.getOrNull() ?: let {
                    return@post call.respond(HttpStatusCode.BadRequest, HttpError.badRequest)
                }

            call.respond(HttpStatusCode.Accepted)

            connectionEventBus.emitEvent(ConnectionEvent.AttemptToConnect(request.nodeAddress))
        }

        post("/api/connection/credentials") {
            val request =
                kotlin.runCatching { call.receive<PostConnectionCredentialsRequest>() }.getOrNull() ?: let {
                    return@post call.respond(HttpStatusCode.BadRequest, HttpError.badRequest)
                }

            call.respond(HttpStatusCode.Accepted)

            connectionEventBus.emitEvent(
                ConnectionEvent.AttemptToConnectWithCredentials(
                    ConnectionCredentials(
                        payload = request.payload,
                        privateKey = request.privateKey,
                        nodeAddress = request.nodeAddress,
                        subscriptionId = request.subscriptionId,
                    ),
                ),
            )
        }

        /**
         * This method is used to disconnect from the current node.
         */
        delete("api/connection") {
            deleteConnection()
            return@delete call.respond(HttpStatusCode.OK)
        }

        /**
         * This method is used to reset VPN profile.
         */
        delete("api/connection/configuration") {
            deleteConfiguration().let {
                if (it.isRight) {
                    return@delete call.respond(HttpStatusCode.OK)
                } else {
                    val error = when (val failure = it.requireLeft()) {
                        is WalletTaskError -> ErrorWrapper(failure.toError())
                        is HubTaskError -> ErrorWrapper(failure.toError())
                        is AccountError -> ErrorWrapper(
                            missingMnemonics,
                            HttpStatusCode.Unauthorized
                        )

                        is SetTunnelState.SetTunnelStateFailure.TunnelNotFound -> ErrorWrapper(
                            tunnelNotFound,
                            HttpStatusCode.NotFound
                        )

                        else -> ErrorWrapper(HttpError.internalServer)
                    }

                    return@delete call.respond(error.code, error.error)
                }
            }
        }

        /**
         * This method is used to stop all active sessions under current account.
         * This will also result in stopping VPN tunnel if it's active.
         */
        delete("api/connection/sessions") {
            deleteSessions().let {
                if (it.isRight) {
                    return@delete call.respond(HttpStatusCode.OK)
                } else {
                    val error = when (val failure = it.requireLeft()) {
                        is WalletTaskError -> ErrorWrapper(failure.toError())
                        is HubTaskError -> ErrorWrapper(failure.toError())
                        is AccountError -> ErrorWrapper(
                            missingMnemonics,
                            HttpStatusCode.Unauthorized
                        )

                        is SetTunnelState.SetTunnelStateFailure.TunnelNotFound -> ErrorWrapper(
                            tunnelNotFound,
                            HttpStatusCode.NotFound
                        )

                        else -> ErrorWrapper(HttpError.internalServer)
                    }

                    return@delete call.respond(error.code, error.error)
                }
            }
        }

        /**
         * This method is used to get current node address and connection status.
         */
        get("api/connection") {
            getConnection().let {
                if (it.isRight) {
                    return@get call.respond(
                        HttpStatusCode.OK,
                        GetConnectionResponseMapper.map(it.requireRight())
                    )
                } else {
                    val error = when (val failure = it.requireLeft()) {
                        GetTunnel.GetTunnelFailure.TunnelNotFound -> ErrorWrapper(
                            tunnelNotFound,
                            HttpStatusCode.NotFound
                        )

                        else -> ErrorWrapper(HttpError.internalServer)
                    }
                    return@get call.respond(error.code, error.error)
                }
            }
        }

    }
}

private fun unpackConnectionError(failure: Failure): Error.InnerError {
    val error = when (failure) {
        is WalletTaskError -> ErrorWrapper(failure.toError())
        is HubTaskError -> ErrorWrapper(failure.toError())
        is PostConnection.PostConnectionFailure.SignatureNotGenerated -> ErrorWrapper(
            signatureGenerationFailed
        )

        is PostConnection.PostConnectionFailure.NotEnoughTokens -> ErrorWrapper(
            notEnoughTokens,
            HttpStatusCode.PaymentRequired
        )

        is AccountError -> ErrorWrapper(
            missingMnemonics,
            HttpStatusCode.Unauthorized
        )

        is PostConnection.PostConnectionFailure.NodeIsOffline -> ErrorWrapper(
            nodeIsOffline
        )

        is PostConnection.PostConnectionFailure.SubscriptionNotFound -> ErrorWrapper(
            noSubscription, HttpStatusCode.NotFound
        )

        is PostConnection.PostConnectionFailure.NoQuotaLeft -> ErrorWrapper(
            noQuotaLeft, HttpStatusCode.Unauthorized
        )

        is PostConnection.PostConnectionFailure.ConnectionAlreadyActive -> ErrorWrapper(
            tunnelIsAlreadyActive
        )

        else -> ErrorWrapper(HttpError.internalServer)
    }

    val message = when (error.error) {
        is Error.GeneralError -> {
            error.error.reason
        }

        is Error.InnerError -> {
            error.error.reason.message
        }

    }

    val code = error.code.value

    return Error.InnerError(
        error = true,
        reason = Error.InnerError.FailureReason(
            message = message,
            code = code
        )
    )
}
