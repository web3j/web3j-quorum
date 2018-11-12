package org.web3j.quorum.enclave

import org.web3j.quorum.enclave.protocol.EnclaveService


/**
 * Enclave API methods.
 */
abstract class Enclave(
        private val Service: EnclaveService) {

    /**
     * Send a new raw payload to Enclave for secure enclave.
     */
    fun sendRawRequest(payload: String, from: String, to: List<String>): String {
        return Service.sendRaw(payload, "sendraw", from, to)
    }

    /**
     * Send a new payload to Enclave for secure enclave.
     */
    fun sendRequest(payload: String, from: String, to: List<String>): SendResponse {
        val sendRequest = SendRequest(payload, from, to)
        return Service.send(sendRequest, "send", SendResponse::class.java)
    }

    /**
     * Retrieve a payload from Enclave's secure enclave.
     */
    fun receiveRequest(key: String, to: String): ReceiveResponse {
        val receiveRequest = ReceiveRequest(key, to)
        return Service.send(receiveRequest, "receive", ReceiveResponse::class.java)
    }

    /**
     * Verify that our node is running
     */
    fun upCheck() : Boolean {
        val test = Service.send("upcheck")
        return test  == "I'm up!"
    }
}
