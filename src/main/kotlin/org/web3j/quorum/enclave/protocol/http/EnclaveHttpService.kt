package org.web3j.quorum.enclave.protocol.http

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.web3j.quorum.enclave.protocol.EnclaveService
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.UriBuilder

class EnclaveHttpService(private val url: String, private val port: Int) : EnclaveService {

    private val objectMapper = jacksonObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)

    override fun <S, T> send(request: S, path: String, responseType: Class<T>): T {
        val payload = objectMapper.writeValueAsString(request)
        val client = ClientBuilder.newClient()
        val serverUri = UriBuilder.fromUri(url).port(port).build()
        println(payload)
        val response = client.target(serverUri)
                .path("/storeraw")
                .request()
                .post(Entity.entity<String>(payload, MediaType.APPLICATION_JSON))
        println(response)
        val chunk = response.readEntity(String::class.java)
        return objectMapper.readValue(chunk, responseType)
    }

    override fun sendRaw(request: String, path: String, from: String, to: List<String>): String {
        TODO("not implemented")
    }

    override fun <S> send(request: S, path: String): Boolean {
        TODO("not implemented")
    }

    override fun sendJsonRequest(payload: String, path: String): String {
        TODO("not implemented")
    }

    override fun sendRawJsonRequest(payload: String, path: String, from: String, to: List<String>): String {
        TODO("not implemented")
    }

    override fun send(path: String): String {
        val client = ClientBuilder.newClient()
        val serverUri = UriBuilder.fromUri(url).port(port).build()
        val response = client.target(serverUri)
                .path("/$path")
                .request()
                .get()
        return response.readEntity(String::class.java)
    }

    override fun performIO(data: String): String {
        TODO("not implemented")
    }

}