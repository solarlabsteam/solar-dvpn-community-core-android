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
)

data class DataWrapper<T>(
    @SerializedName("data")
    val data: T,
)
