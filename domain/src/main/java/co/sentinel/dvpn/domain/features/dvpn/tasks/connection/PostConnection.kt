package co.sentinel.dvpn.domain.features.dvpn.tasks.connection

import co.sentinel.dvpn.domain.core.DEFAULT_FEE_AMOUNT
import co.sentinel.dvpn.domain.core.DVPN_NODE_NAME
import co.sentinel.dvpn.domain.core.denom
import co.sentinel.dvpn.domain.core.exception.Failure
import co.sentinel.dvpn.domain.core.functional.Either
import co.sentinel.dvpn.domain.core.functional.requireLeft
import co.sentinel.dvpn.domain.core.functional.requireRight
import co.sentinel.dvpn.domain.core.interactor.BaseUseCase
import co.sentinel.dvpn.domain.features.dvpn.SetTunnelState
import co.sentinel.dvpn.domain.features.dvpn.model.DvpnTunnel
import co.sentinel.dvpn.domain.features.dvpn.source.DVPNRepository
import co.sentinel.dvpn.domain.features.hub.model.Subscription
import co.sentinel.dvpn.domain.features.hub.source.HubCacheRepository
import co.sentinel.dvpn.domain.features.hub.source.HubRemoteRepository
import co.sentinel.dvpn.domain.features.wallet.source.WalletRepository

class PostConnection(
    private val hubRepository: HubRemoteRepository,
    private val walletRepository: WalletRepository,
    private val cacheRepository: HubCacheRepository,
    private val dvpnRepository: DVPNRepository
) : BaseUseCase<PostConnection.Success, PostConnection.Params>() {

    data class Params(val nodeAddress: String)

    override suspend fun run(params: Params): Either<Failure, Success> {
        val fetchSubscriptionsResult = hubRepository.fetchSubscriptions()
        if (fetchSubscriptionsResult.isLeft) {
            return Either.Left(fetchSubscriptionsResult.requireLeft())
        }

        val subscription = fetchSubscriptionsResult.requireRight().lastOrNull {
            it.node == params.nodeAddress
        } ?: return Either.Left(PostConnectionFailure.SubscriptionNotFound)

        val lastSession = cacheRepository.getLastSession()
        var isSessionActive = false

        // check if there are active running sessions
        lastSession?.let {
            val loadActiveSession = hubRepository.fetchActiveSessions()
            // if other error than NoActiveSessionFound occurred during fetching active sessions
            // return the error, otherwise continue with connecting
            if (loadActiveSession.isLeft && loadActiveSession.requireLeft() != PostConnectionFailure.NoActiveSessionFound) {
                return Either.Left(loadActiveSession.requireLeft())
            }
            // do we need session id to be checked as well next to the node address
            isSessionActive = if (loadActiveSession.isRight) {
                loadActiveSession.requireRight()
                    .any { it.id == lastSession.id && it.node == params.nodeAddress }
            } else false
        }

        // check if tunnel is running
        val getTunnelStatus = dvpnRepository.getTunnel(DVPN_NODE_NAME)
        val isTunnelActive =
            !getTunnelStatus.isLeft && getTunnelStatus.requireRight().tunnel.state == DvpnTunnel.State.UP

        return when {
            isTunnelActive && isSessionActive -> {
                return Either.Left(PostConnectionFailure.ConnectionAlreadyActive)
            }

            !isTunnelActive && isSessionActive -> {
                val setTunnelStateResult = dvpnRepository.setTunnelState(
                    SetTunnelState.SetTunnelStateParams(
                        DVPN_NODE_NAME,
                        DvpnTunnel.State.UP
                    )
                )
                if (setTunnelStateResult.isLeft) {
                    connect(subscription)
                } else {
                    Either.Right(Success(setTunnelStateResult.requireRight().tunnel))
                }

            }

            else -> {
                connect(subscription)
            }

        }
    }

    private suspend fun connect(subscription: Subscription): Either<Failure, Success> {
        val fetchQuotaResult = hubRepository.fetchQuota(subscription.id)
        if (fetchQuotaResult.isLeft) {
            return Either.Left(fetchQuotaResult.requireLeft())
        }

        val quota = fetchQuotaResult.requireRight()
        val initialBandwidth = quota.allocated.runCatching { toLong() }.getOrElse { 0L }
        val bandwidthConsumed = quota.consumed.runCatching { toLong() }.getOrElse { 0L }
        val bandwidthLeft = initialBandwidth - bandwidthConsumed

        if (bandwidthLeft <= 0L) {
            return Either.Left(PostConnectionFailure.NoQuotaLeft)
        }

        val fetchBalance = walletRepository.fetchBalance()
        if (fetchBalance.isRight) {
            fetchBalance.requireRight().firstOrNull { it.denom == denom }?.let {
                if (it.amount.toLong() < DEFAULT_FEE_AMOUNT) {
                    return Either.Left(PostConnectionFailure.NotEnoughTokens)
                }
            }
        }

        // generate node session message
        val generatedConnectToNodeMessage =
            hubRepository.generateConnectToNodeMessages(subscription.id, subscription.node)
        if (generatedConnectToNodeMessage.isLeft) {
            return Either.Left(generatedConnectToNodeMessage.requireLeft())
        }

        // start node session
        val startNodeSession =
            walletRepository.startNodeSession(generatedConnectToNodeMessage.requireRight())
        if (startNodeSession.isLeft) {
            return Either.Left(startNodeSession.requireLeft())
        }

        // get active session
        val activeSession = hubRepository.loadActiveSession()
        if (activeSession.isLeft) {
            return Either.Left(activeSession.requireLeft())
        }

        val session = activeSession.requireRight()
        val keyPair = dvpnRepository.generateKeyPair()
        val signature = walletRepository.getSignature(activeSession.requireRight())

        if (signature.isLeft) return Either.Left(PostConnectionFailure.SignatureNotGenerated)

        // create vpn profile
        val fetchVpnProfile = hubRepository.fetchVpnProfile(
            session,
            keyPair.publicKeyBase64,
            signature.requireRight()
        )
        if (fetchVpnProfile.isLeft) {
            return Either.Left(fetchVpnProfile.requireLeft())
        }

        // create or update tunnel
        val createOrUpdateTunnel = dvpnRepository.createOrUpdate(
            DVPN_NODE_NAME,
            fetchVpnProfile.requireRight(),
            keyPair,
            subscription.node,
        ).also {
            cacheRepository.setLastSession(session)
        }

        if (createOrUpdateTunnel.isLeft) {
            return Either.Left(createOrUpdateTunnel.requireLeft())
        }

        // start the tunnel
        val setTunnelStateResult = dvpnRepository.setTunnelState(
            SetTunnelState.SetTunnelStateParams(
                DVPN_NODE_NAME,
                DvpnTunnel.State.UP
            )
        )

        if (setTunnelStateResult.isLeft) {
            return Either.Left(setTunnelStateResult.requireLeft())
        }

        return Either.Right(Success(setTunnelStateResult.requireRight().tunnel))
    }

    data class Success(val tunnel: DvpnTunnel)

    sealed class PostConnectionFailure : Failure.FeatureFailure() {
        object SubscriptionNotFound : PostConnectionFailure()
        object NoActiveSessionFound : PostConnectionFailure()
        object NodeIsOffline : PostConnectionFailure()
        object SignatureNotGenerated : PostConnectionFailure()
        object ConnectionAlreadyActive : PostConnectionFailure()
        object NoQuotaLeft : PostConnectionFailure()
        object NotEnoughTokens : PostConnectionFailure()
    }
}