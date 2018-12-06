package org.web3j.quorum.enclave.protocol.ipc

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.*
import org.web3j.protocol.ipc.IOFacade
import org.web3j.protocol.ipc.IpcService
import org.web3j.quorum.enclave.protocol.EnclaveService
import org.web3j.quorum.enclave.protocol.utils.RequestBuilder
import org.web3j.quorum.enclave.protocol.utils.ResponseParser

/**
 * IPC service layer
 */
abstract class EnclaveIpcService : EnclaveService {
    private val objectMapper = jacksonObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)

    /**
     * Send a new raw payload to Enclave
     */
    override fun <S, T> send(request: S, path: String, responseType: Class<T>): T {
        val payload = objectMapper.writeValueAsString(request)
        val chunk = sendJsonRequest(payload, path)
        return objectMapper.readValue(chunk, responseType)
    }

    /**
     * Send a new raw request to Enclave
     */
    fun <S> send(request: S, path: String): Boolean {
        val payload = objectMapper.writeValueAsString(request)
        return sendJsonRequest(payload, path).isEmpty()
    }

    /**
     * Send a new raw request to Enclave for secure enclave.
     */
    fun sendJsonRequest(payload: String, path: String): String {
        val data = RequestBuilder.encodeJsonRequest(path, payload)
        return performIO(data)
    }

    /**
     * Send to a specific path
     */
    override fun send(path: String): String {
        val data = RequestBuilder.encodeGetRequest(path)
        return performIO(data)
    }

    /**
     * Perform IO read/write
     */
    private fun performIO(data: String): String {
        val io = getIO()
        io.write(data)

        val response = io.read()

        return ResponseParser.parseChunkedResponse(response)
    }

    abstract fun getIO(): IOFacade
}
