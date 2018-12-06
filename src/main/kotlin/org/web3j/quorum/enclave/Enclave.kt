package org.web3j.quorum.enclave

import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.quorum.enclave.protocol.EnclaveService


/**
 * Enclave API methods.
 */
interface Enclave {
    /**
     * Send a new raw payload to the quorum node for signing and storing
     */
    fun sendRawRequest(payload: String, privateFor: List<String>): EthSendTransaction

    /**
     * Store raw transaction in the secure enclave only
     */
    fun storeRawRequest(payload: String, from: String, to: List<String>): SendResponse

    /**
     * Retrieve a payload from the secure enclave
     */
    fun receiveRequest(key: String, to: String): ReceiveResponse

    /**
     * Verify that our enclave node is running
     */
    fun upCheck() : Boolean

    /**
     * Delete a payload from the secure enclave only
     * @param key The enclave key
     */
    fun deleteRequest(key: String): Boolean
}
