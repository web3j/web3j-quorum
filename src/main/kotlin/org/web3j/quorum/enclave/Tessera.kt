package org.web3j.quorum.enclave

import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.quorum.Quorum
import org.web3j.quorum.enclave.protocol.EnclaveService

class Tessera(private val service: EnclaveService, private val web3: Quorum) : Enclave(service) {
    /**
     * Send the encrypted payload to the Tessera's endpoint
     */
    override fun sendRawRequest(payload: String, privateFor: List<String>): EthSendTransaction {
        return web3.ethSendRawPrivateTransaction(payload, privateFor).send()
    }

    /**
     * Store a new payload to Enclave for secure enclave.
     */
    override fun storeRawRequest(payload: String, from: String, to: List<String>): SendResponse {
        val storeRawRequest = StoreRawRequest(payload, from)
        return service.send(storeRawRequest, "storeraw", SendResponse::class.java)
    }

    /**
     * Verify that our node is running
     */
    override fun upCheck(): Boolean {
        val test = service.send("upcheck")
        return test == "I'm up!"
    }
}