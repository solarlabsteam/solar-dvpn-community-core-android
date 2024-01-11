package co.sentinel.dvpn.domain.features.hub.source

import co.sentinel.cosmos.dao.Account
import co.sentinel.dvpn.domain.core.exception.Failure
import co.sentinel.dvpn.domain.core.functional.Either
import co.sentinel.dvpn.domain.features.hub.model.*
import com.google.protobuf2.Any
import sentinel.node.v1.NodeOuterClass

interface HubRemoteRepository {

    // Fetch account
    suspend fun getAccount(): Either<Failure, Account>

    // Fetch stats
    suspend fun fetchNode(nodeAddress: String): Either<Failure, NodeOuterClass.Node>
    suspend fun fetchSubscriptions(): Either<Failure, List<Subscription>>
    suspend fun fetchQuota(subscriptionId: Long): Either<Failure, Quota>

    // Generate message
    suspend fun generateStopActiveSessionsMessage(): Either<Failure, List<Any>>
    suspend fun generateCreateNodeSubscriptionMessage(address: String, price: String): Any
    suspend fun generateCancelNodeSubscriptionMessage(subscriptionIds: List<Long>): List<Any>
    suspend fun generateConnectToNodeMessages(
        subscriptionId: Long,
        nodeAddress: String,
    ): Either<Failure, List<Any>>

    // Session
    suspend fun loadActiveSession(): Either<Failure, Session>
    suspend fun fetchActiveSessions(): Either<Failure, List<Session>>
    suspend fun fetchVpnProfile(
        session: Session,
        keyBase64: String,
        signature: String,
    ): Either<Failure, VpnProfile>

    suspend fun parseVpnProfile(payload: String): Either<Failure, VpnProfile>
}
