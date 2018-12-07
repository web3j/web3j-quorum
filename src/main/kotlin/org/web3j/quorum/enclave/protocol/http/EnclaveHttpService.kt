package org.web3j.quorum.enclave.protocol.http

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.web3j.quorum.enclave.protocol.EnclaveService
import okhttp3.*
import okhttp3.OkHttpClient



class EnclaveHttpService(private val url: String, private val port: Int) : EnclaveService {

    private val objectMapper = jacksonObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    private val JSON = MediaType.parse("application/json; charset=utf-8")


    /**
     * Send a new raw payload to Enclave
     */
    override fun <S, T> send(request: S, path: String, responseType: Class<T>): T {
        val payload = objectMapper.writeValueAsString(request)
        val client = OkHttpClient()
        val uri = "$url:$port/$path"
        val body = RequestBody.create(JSON, objectMapper.writeValueAsString(payload))
        val request = Request.Builder()
                .url(uri)
                .post(body)
                .build()

        val response = client.newCall(request).execute()

        val chunk = response.body().toString()
        return objectMapper.readValue(chunk, responseType)
    }

    /**
     * Send a new raw payload to Enclave to a specific path
     */
    override fun send(path: String): String {
        val client = OkHttpClient()
        val serverUri = "$url:$port/$path"
        val request = Request.Builder()
                .url(serverUri)
                .get()
                .build()
        val response = client.newCall(request).execute()
        return response.body().toString()
    }
}