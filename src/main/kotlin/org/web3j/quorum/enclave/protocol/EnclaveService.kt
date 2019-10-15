package org.web3j.quorum.enclave.protocol

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.RequestBody
import org.web3j.quorum.enclave.EnclaveClientConnectionException

/**
 * EnclaveService implements send methods that support communication with the private enclave via Http.
 * Currently Http communication is only supported in Tessera.
 */
class EnclaveService(private val url: String, private val port: Int, private val client: OkHttpClient = OkHttpClient()) {

    private val objectMapper = jacksonObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    private val JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8")

    /**
     * Send a new raw payload to Enclave
     */
    fun <S, T> send(request: S, path: String, responseType: Class<T>): T {
        val payload = objectMapper.writeValueAsString(request)
        val uri = "$url:$port/$path"
        val body = RequestBody.create(JSON_MEDIA_TYPE, payload)
        val request = Request.Builder()
                .url(uri)
                .post(body)
                .build()

        val response = client.newCall(request).execute()

        if(response.isSuccessful) {
            val chunk = response.body()?.string()
            return objectMapper.readValue(chunk, responseType)
        } else {
            val statusCode = response.code()
            val text = if (response.body() == null) "N/A" else response.body()?.string()

            throw EnclaveClientConnectionException("Invalid response received from enclave: $statusCode $text")
        }
    }

    /**
     * Send a new raw payload to Enclave to a specific path
     */
    fun send(path: String): String {
        val serverUri = "$url:$port/$path"
        val request = Request.Builder()
                .url(serverUri)
                .get()
                .build()
        val response = client.newCall(request).execute()
        return response.body()?.string() ?: ""
    }
}