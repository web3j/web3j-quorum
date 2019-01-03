package org.web3j.quorum.enclave.protocol.ipc

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.web3j.quorum.enclave.EnclaveClientConnectionException
import org.web3j.quorum.enclave.protocol.EnclaveService

/**
 * IPC service layer
 */
class EnclaveIpcService(private val url: String, private val port: Int, private val client: OkHttpClient = OkHttpClient()) : EnclaveService {
    private val objectMapper = jacksonObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    private val JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8")
    /**
     * Send a new raw payload to Enclave
     */
    override fun <S, T> send(request: S, path: String, responseType: Class<T>): T {
        val payload = objectMapper.writeValueAsString(request)
        val uri = "$url:$port/$path"
        val body = RequestBody.create(JSON_MEDIA_TYPE, payload)
        val request = Request.Builder()
                .url(uri)
                .post(body)
                .build()

        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            val chunk = response.body()?.string()
            return objectMapper.readValue(chunk, responseType)
        } else {
            val statusCode = response.code()
            val text = if (response.body() == null) "N/A" else response.body()?.string()

            throw EnclaveClientConnectionException("Invalid response received from enclave: $statusCode $text")
        }
    }

    /**
     * Send to a specific path
     */
    override fun send(path: String): String {
        val client = OkHttpClient()
        val serverUri = "$url:$port/$path"
        val request = Request.Builder()
                .url(serverUri)
                .get()
                .build()
        val response = client.newCall(request).execute()
        return response.message()
    }
}
