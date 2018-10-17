package org.web3j.quorum.enclave.protocol

import org.web3j.quorum.enclave.*
import org.web3j.quorum.enclave.ipc.IpcService


/**
 * Constellation API methods.
 */
class Constellation(
        val ipcService: IpcService) {

    /**
     * Send a new raw payload to Constellation for secure enclave.
     */
    fun sendRawRequest(payload: String, from: String, to: List<String>): String {
        return ipcService.sendRaw(payload, "sendraw", from, to)
    }

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
    fun upCheck() : Boolean {
        val test = ipcService.sendJsonRequest("", "upcheck")
        return test  == "I'm up!"
    }
}
