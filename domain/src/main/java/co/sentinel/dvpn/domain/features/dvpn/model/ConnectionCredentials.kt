package co.sentinel.dvpn.domain.features.dvpn.model

data class ConnectionCredentials(
    val payload: String,
    val privateKey: String,
    val nodeAddress: String,
    val subscriptionId: Long,
)
