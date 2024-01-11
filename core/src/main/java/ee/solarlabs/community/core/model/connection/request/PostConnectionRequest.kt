package ee.solarlabs.community.core.model.connection.request

import com.google.gson.annotations.SerializedName

data class PostConnectionRequest(
    @SerializedName("node_address")
    val nodeAddress: String
)

data class PostConnectionCredentialsRequest(
    @SerializedName("payload")
    val payload: String,
    @SerializedName(value = "private_key")
    val privateKey: String,
    @SerializedName("node_address")
    val nodeAddress: String,
    @SerializedName("subscription_id")
    val subscriptionId: Long,
)
