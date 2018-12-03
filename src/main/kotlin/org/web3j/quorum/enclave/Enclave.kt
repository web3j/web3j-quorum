package org.web3j.quorum.enclave

import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.quorum.enclave.protocol.EnclaveService


/**
 * Enclave API methods.
 */
abstract class Enclave(
        private val Service: EnclaveService) {
    /**
     * Send a new raw payload to Enclave
     */
    abstract fun sendRawRequest(payload: String, privateFor: List<String>): EthSendTransaction

    /**
     * Store raw transaction in the secure Enclave
     */
    abstract fun storeRawRequest(payload: String, from: String, to: List<String>): SendResponse

    /**
     * Verify that our node is running
     */
    abstract fun upCheck() : Boolean

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
}
