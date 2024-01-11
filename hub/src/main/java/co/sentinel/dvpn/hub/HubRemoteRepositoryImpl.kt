package co.sentinel.dvpn.hub

import co.sentinel.cosmos.base.BaseChain
import co.sentinel.cosmos.base.BaseCosmosApp
import co.sentinel.cosmos.dao.Account
import co.sentinel.dvpn.domain.core.exception.AccountError
import co.sentinel.dvpn.domain.core.exception.Failure
import co.sentinel.dvpn.domain.core.functional.Either
import co.sentinel.dvpn.domain.core.functional.getOrElse
import co.sentinel.dvpn.domain.core.functional.requireLeft
import co.sentinel.dvpn.domain.core.functional.requireRight
import co.sentinel.dvpn.domain.core.generateCoinFromPrice
import co.sentinel.dvpn.domain.features.dvpn.tasks.connection.PostConnection
import co.sentinel.dvpn.domain.features.hub.failure.VpnProfileFailure
import co.sentinel.dvpn.domain.features.hub.model.Quota
import co.sentinel.dvpn.domain.features.hub.model.Session
import co.sentinel.dvpn.domain.features.hub.model.VpnProfile
import co.sentinel.dvpn.domain.features.hub.source.HubRemoteRepository
import co.sentinel.dvpn.hub.mapper.ProfileMapper
import co.sentinel.dvpn.hub.mapper.SessionMapper
import co.sentinel.dvpn.hub.mapper.SubscriptionMapper
import co.sentinel.dvpn.hub.tasks.*
import com.google.protobuf2.Any
import sentinel.types.v1.StatusOuterClass
import timber.log.Timber


class HubRemoteRepositoryImpl(private val app: BaseCosmosApp) : HubRemoteRepository {

    override suspend fun getAccount(): Either<Failure, Account> {
        return app.baseDao.onSelectAccount(app.baseDao.lastUser)?.let {
            Either.Right(it)
        } ?: Either.Left(AccountError)
    }

    override suspend fun fetchNode(nodeAddress: String) = kotlin.runCatching {
        val account = getAccount()
        if (account.isLeft) return Either.Left(account.requireLeft())
        QueryNode.execute(nodeAddress, BaseChain.getChain(account.requireRight().baseChain))
    }.onFailure { Timber.e(it) }
        .getOrNull() ?: Either.Left(Failure.AppError)

    override suspend fun fetchSubscriptions() = kotlin.runCatching {
        val account = getAccount()
        if (account.isLeft) Either.Left(account.requireLeft())
        val subscriptions = FetchSubscriptions.execute(account.requireRight())
        if (subscriptions.isRight) {
            Either.Right(
                subscriptions.requireRight()
                    .map { SubscriptionMapper.map(it) }
            )
        } else {
            Either.Left(subscriptions.requireLeft())
        }
    }.onFailure { Timber.e(it) }
        .getOrNull() ?: Either.Left(Failure.AppError)

    override suspend fun fetchQuota(subscriptionId: Long): Either<Failure, Quota> {
        return kotlin.runCatching {
            val account = getAccount()
            if (account.isLeft) return Either.Left(account.requireLeft())
            val quotaResult = QueryQuota.execute(account.requireRight(), subscriptionId)
            if (quotaResult.isRight) {
                Either.Right(
                    Quota(
                        address = quotaResult.requireRight().address,
                        allocated = quotaResult.requireRight().allocated,
                        consumed = quotaResult.requireRight().consumed
                    )
                )
            } else {
                Either.Left(quotaResult.requireLeft())
            }
        }.onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)
    }

    // GENERATED MESSAGES

    override suspend fun generateStopActiveSessionsMessage(): Either<Failure, List<Any>> =
        kotlin.runCatching {
            val account = getAccount()
            if (account.isLeft) return Either.Left(account.requireLeft())
            val activeSessions = FetchActiveSessions.execute(account.requireRight())
            if (activeSessions.isRight) {
                Either.Right(
                    GenerateStopActiveSessionMessages.execute(
                        account.requireRight(),
                        activeSessions.requireRight()
                    )
                )
            } else {
                Either.Left(activeSessions.requireLeft())
            }
        }.onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)

    override suspend fun generateConnectToNodeMessages(
        subscriptionId: Long,
        nodeAddress: String
    ): Either<Failure, List<Any>> = kotlin.runCatching {
        val account = getAccount()
        if (account.isLeft) return Either.Left(account.requireLeft())
        val activeSessions = FetchActiveSessions.execute(account.requireRight())
        if (activeSessions.isRight) {
            val stopMessages = GenerateStopActiveSessionMessages.execute(
                account.requireRight(),
                activeSessions.getOrElse(listOf())
            )
            return stopMessages.toMutableList().apply {
                add(
                    GenerateStartActiveSessionMessage.execute(
                        account.requireRight(),
                        subscriptionId,
                        nodeAddress
                    )
                )
            }.let {
                Either.Right(it)
            }
        } else {
            Either.Left(activeSessions.requireLeft())
        }
    }.onFailure { Timber.e(it) }
        .getOrNull() ?: Either.Left(Failure.AppError)

    override suspend fun generateCreateNodeSubscriptionMessage(address: String, price: String) =
        kotlin.runCatching {
            val account = getAccount()
            if (account.isLeft) Either.Left(account.requireLeft())
            GenerateCreateNodeSubscriptionMessage.execute(
                account.requireRight(),
                address,
                generateCoinFromPrice(price)
            )
        }.onFailure { Timber.e(it) }
            .getOrThrow()

    override suspend fun generateCancelNodeSubscriptionMessage(subscriptionIds: List<Long>): List<Any> =
        kotlin.runCatching {
            val account = getAccount()
            if (account.isLeft) Either.Left(account.requireLeft())
            // fetch active sessions and stop them to successfully cancel subscription
            val activeSessions =
                FetchActiveSessions.execute(account.requireRight()).getOrElse(listOf()).filter {
                    subscriptionIds.contains(it.subscription)
                }
            val stopMessages =
                GenerateStopActiveSessionMessages.execute(account.requireRight(), activeSessions)
            stopMessages.toMutableList().apply {
                subscriptionIds.map { subscriptionId ->
                    add(
                        GenerateCancelNodeSubscriptionMessage.execute(
                            account.requireRight(),
                            subscriptionId
                        )
                    )
                }
            }
        }.onFailure { Timber.e(it) }
            .getOrThrow()

    // SESSION

    override suspend fun loadActiveSession() = kotlin.runCatching {
        val account = getAccount()
        if (account.isLeft) return Either.Left(account.requireLeft())
        val result = FetchActiveSessions.execute(account.requireRight())
        if (result.isRight) {
            result.requireRight().firstOrNull()?.let {
                Either.Right(SessionMapper.map(it))
            } ?: Either.Left(PostConnection.PostConnectionFailure.NoActiveSessionFound)
        } else {
            Either.Left(result.requireLeft())
        }

    }.onFailure { Timber.e(it) }
        .getOrNull() ?: Either.Left(Failure.AppError)

    override suspend fun fetchActiveSessions(): Either<Failure, List<Session>> =
        kotlin.runCatching {
            val account = getAccount()
            if (account.isLeft) return Either.Left(account.requireLeft())
            val result = FetchActiveSessions.execute(account.requireRight())
            if (result.isRight) {
                result.requireRight().map {
                    SessionMapper.map(it)
                }.let {
                    if (it.isEmpty()) Either.Left(PostConnection.PostConnectionFailure.NoActiveSessionFound)
                    else Either.Right(it)
                }
            } else {
                Either.Left(result.requireLeft())
            }
        }.onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)

    override suspend fun fetchVpnProfile(
        session: Session,
        keyBase64: String,
        signature: String
    ) = kotlin.runCatching {
        val account = getAccount()
        if (account.isLeft) return Either.Left(account.requireLeft())
        val nodeResult = fetchNode(session.node)
        if (nodeResult.isRight) {
            when (nodeResult.requireRight().status) {
                StatusOuterClass.Status.STATUS_ACTIVE -> {
                    FetchVpnProfile.execute(
                        account = account.requireRight(),
                        remoteUrl = nodeResult.requireRight().remoteUrl,
                        sessionId = session.id,
                        key = keyBase64,
                        signature = signature
                    )
                }
                else -> {
                    Either.Left(PostConnection.PostConnectionFailure.NodeIsOffline)
                }
            }
        } else {
            Either.Left(nodeResult.requireLeft())
        }
    }.onFailure { Timber.e(it) }
        .getOrNull() ?: Either.Left(Failure.AppError)

    override suspend fun parseVpnProfile(
        payload: String,
    ): Either<Failure, VpnProfile> = kotlin.runCatching {
        Either.Right(ProfileMapper.map(payload))
    }.onFailure { Timber.e(it) }
        .getOrNull() ?: Either.Left(VpnProfileFailure.VpnProfileFetchFailure)
}