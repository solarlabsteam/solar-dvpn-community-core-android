package co.sentinel.dvpn.domain.features.dvpn.model

data class KeyPair(
    val privateKeyBase64: String,
    val publicKeyBase64: String
)