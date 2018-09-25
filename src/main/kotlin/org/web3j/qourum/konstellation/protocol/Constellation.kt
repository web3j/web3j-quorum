package io.blk.konstellation.protocol

import io.blk.konstellation.*
import io.blk.konstellation.ipc.IpcService

/**
 * Constellation API methods.
 */
class Constellation(
        val ipcService: IpcService) {

    /**
     * Send a new payload to Constellation for secure enclave.
     */
    fun sendRequest(payload: String, from: String, to: List<String>): SendResponse {
        val sendRequest = SendRequest(payload, from, to)
        return ipcService.send(sendRequest, "send", SendResponse::class.java)
    }

    /**
     * Retrieve a payload from Constellation's secure enclave.
     */
    fun receiveRequest(key: String, to: String): ReceiveResponse {
        val receiveRequest = ReceiveRequest(key, to)
        return ipcService.send(receiveRequest, "receive", ReceiveResponse::class.java)
    }

    /**
     * Delete a payload from Constellation's secure enclave.
     */
    fun deleteRequest(key: String): Boolean {
        val deleteRequest = DeleteRequest(key)
        return ipcService.send(deleteRequest, "delete")
    }

    /**
     * Verify that our node is running
     */
    fun upCheck() = ipcService.send("upcheck") == "I'm up!"
}