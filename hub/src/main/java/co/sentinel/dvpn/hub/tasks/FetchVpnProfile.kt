package co.sentinel.dvpn.hub.tasks

import co.sentinel.cosmos.dao.Account
import co.sentinel.dvpn.domain.core.exception.Failure
import co.sentinel.dvpn.domain.core.functional.Either
import co.sentinel.dvpn.domain.features.hub.failure.VpnProfileFailure
import co.sentinel.dvpn.domain.features.hub.model.VpnProfile
import co.sentinel.dvpn.hub.core.extensions.await
import co.sentinel.dvpn.hub.mapper.ProfileMapper
import co.sentinel.dvpn.hub.mapper.VpnProfileErrorCodeMapper
import co.sentinel.dvpn.hub.model.VpnProfileResponse
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit


interface FetchVpnProfileInterface {
    suspend fun execute(
        account: Account,
        remoteUrl: String,
        sessionId: Long,
        key: String,
        signature: String
    ): Either<Failure, VpnProfile>
}

/**
 * Errors handled for vpn profile fetching can be found here:
 * {@link https://github.com/sentinel-official/dvpn-node/blob/6e21f5a9b7e7d24525d43466d3c0544af0e0cdbf/rest/session/handlers.go}
 */
object FetchVpnProfile : FetchVpnProfileInterface {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(3, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()

    override suspend fun execute(
        account: Account,
        remoteUrl: String,
        sessionId: Long,
        key: String,
        signature: String
    ): Either<Failure, VpnProfile> = kotlin.runCatching {
        val body = generateBodyObject(key, signature)

        val fetchProfileResponse = fetchVpnProfile(remoteUrl, account.address, sessionId, body)
            ?: return Either.Left(VpnProfileFailure.VpnProfileFetchFailure)

        if (fetchProfileResponse.isSuccessful.not() && fetchProfileResponse.code == HttpURLConnection.HTTP_UNAVAILABLE) {
            return Either.Left(VpnProfileFailure.VPNProfileNodeServiceUnavailableFailure)
        }

        // in case of an unsuccessful response that is not code 400, 404 or 500 return general error
        if (fetchProfileResponse.isSuccessful.not()
            && fetchProfileResponse.code != HttpURLConnection.HTTP_BAD_REQUEST
            && fetchProfileResponse.code != HttpURLConnection.HTTP_NOT_FOUND
            && fetchProfileResponse.code != HttpURLConnection.HTTP_INTERNAL_ERROR
        ) {
            return Either.Left(VpnProfileFailure.VpnProfileFetchFailure)
        }

        val responseBody = fetchProfileResponse.body?.string()
        fetchProfileResponse.close()

        responseBody?.let {
            val vpnProfileResponse =
                gson.fromJson(responseBody, VpnProfileResponse::class.java)

            if (vpnProfileResponse.success) {
                val mappedVpnProfile = ProfileMapper.map(vpnProfileResponse.result!!)
                Either.Right(mappedVpnProfile)
            } else {
                val mappedVpnProfileError =
                    VpnProfileErrorCodeMapper.map(vpnProfileResponse.error!!.code)
                Either.Left(mappedVpnProfileError)
            }
        } ?: Either.Left(VpnProfileFailure.VpnProfileFetchFailure)

    }.onFailure { Timber.e(it) }
        .getOrNull() ?: Either.Left(VpnProfileFailure.VpnProfileFetchFailure)

    private fun generateBodyObject(key: String, signature: String) =
        with(JsonObject()) {
            addProperty("key", key)
            addProperty("signature", signature)
            toString()
        }

    private suspend fun fetchVpnProfile(
        url: String,
        address: String,
        id: Long,
        body: String
    ): Response? {
        var response: Response? = null
        try {
            url.toHttpUrlOrNull()?.let {
                response = client.newCall(
                    Request.Builder().url(
                        it.newBuilder()
                            .scheme("http")
                            .addPathSegment("accounts")
                            .addPathSegment(address)
                            .addPathSegment("sessions")
                            .addPathSegment("$id")
                            .build()
                    ).post(body.toRequestBody("application/json".toMediaTypeOrNull())).build()
                ).await()
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
        return response
    }
}

// inner error code json examples
private const val HTTP_400_CODE_1 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 1,\n" +
        "\t\t\"message\": \"Request format error\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_400_CODE_2 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 2,\n" +
        "\t\t\"message\": \"failed to verify the signature %s\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_404_CODE_2 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 2,\n" +
        "\t\t\"message\": \"account %s does not exist\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_500_CODE_2 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 2,\n" +
        "\t\t\"message\": \"Undefined error\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_400_CODE_3 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 3,\n" +
        "\t\t\"message\": \"invalid status %s for session %d\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_404_CODE_3 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 3,\n" +
        "\t\t\"message\": \"session %d does not exist\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_500_CODE_3 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 3,\n" +
        "\t\t\"message\": \"Undefined error\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_400_CODE_4 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 4,\n" +
        "\t\t\"message\": \"invalid status %s for session %d\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_404_CODE_4 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 4,\n" +
        "\t\t\"message\": \"session %d does not exist\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_500_CODE_4 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 4,\n" +
        "\t\t\"message\": \"Undefined error\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_400_CODE_5 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 5,\n" +
        "\t\t\"message\": \"node address mismatch; expected %s, got %s\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_500_CODE_5 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 5,\n" +
        "\t\t\"message\": \"Undefined error\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_400_CODE_6 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 6,\n" +
        "\t\t\"message\": \"peer for session %d already exist\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_400_CODE_7 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 7,\n" +
        "\t\t\"message\": \"invalid status %s for session %d\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_404_CODE_7 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 7,\n" +
        "\t\t\"message\": \"session %d does not exist\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_500_CODE_7 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 7,\n" +
        "\t\t\"message\": \"Undefined error\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_500_CODE_8 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 8,\n" +
        "\t\t\"message\": \"Undefined error\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_404_CODE_9 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 9,\n" +
        "\t\t\"message\": \"quota for address %s does not exist\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_500_CODE_9 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 9,\n" +
        "\t\t\"message\": \"Undefined error\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_400_CODE_10 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 10,\n" +
        "\t\t\"message\": \"quota exceeded; allocated %s, consumed %s\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_400_CODE_11 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 11,\n" +
        "\t\t\"message\": \"reached maximum peers limit; maximum %d\"\n" +
        "\t}\n" +
        "}"

private const val HTTP_500_CODE_12 = "{\n" +
        "\t\"success\": false,\n" +
        "\t\"error\": {\n" +
        "\t\t\"code\": 12,\n" +
        "\t\t\"message\": \"Undefined error\"\n" +
        "\t}\n" +
        "}"