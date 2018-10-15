package io.blk.konstellation.ipc

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.*
import io.blk.konstellation.http.RequestBuilder
import io.blk.konstellation.http.ResponseParser
import org.slf4j.LoggerFactory
import org.web3j.protocol.ipc.IOFacade
import org.web3j.protocol.ipc.IpcService

/**
 * IPC service layer
 */
class IpcService(val ioFacade: IOFacade) {

    private val log = LoggerFactory.getLogger(IpcService::class.java)
    private val objectMapper = jacksonObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)

    fun <S, T> send(request: S, path: String, responseType: Class<T>): T {
        val payload = objectMapper.writeValueAsString(request)
        val chunk = sendJsonRequest(payload, path)
        return objectMapper.readValue(chunk, responseType)
    }

    fun sendRaw(request: String, path: String, from: String, to: List<String>): String {
        return sendRawJsonRequest(request, path, from, to)
    }

    fun <S> send(request: S, path: String): Boolean {
        val payload = objectMapper.writeValueAsString(request)
        return sendJsonRequest(payload, path).isEmpty()
    }

    fun sendJsonRequest(payload: String, path: String): String {
        val data = RequestBuilder.encodeJsonRequest(path, payload)
        return performIO(data)
    }

    fun sendRawJsonRequest(payload: String, path: String, from: String, to: List<String>): String {
        val data = RequestBuilder.encodeRawJsonRequest(path, payload, from, to)
        return performIO(data)
    }

    fun send(path: String): String {
        val data = RequestBuilder.encodeGetRequest(path)
        return performIO(data)
    }

    private fun performIO(data: String): String {
        ioFacade.write(data)
        log.debug(">> " + data)

        val response = ioFacade.read()
        log.debug("<< " + response)

        return ResponseParser.parseChunkedResponse(response)
    }
}