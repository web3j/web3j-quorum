package org.web3j.quorum.enclave

import org.web3j.quorum.enclave.protocol.EnclaveService

class Tessera(private val service: EnclaveService) : Enclave(service) {

    /**
     * Store a new payload to Enclave for secure enclave.
     */
    fun storeRawRequest(payload: String, from: String): SendResponse {
        val storerawRequest = StoreRawRequest(payload, from)
        return service.send(storerawRequest, "storeraw", SendResponse::class.java)
    }

    /**
     * Verify that our node is running
     */
    override fun upCheck() : Boolean {
        val test = service.send("upcheck")
        return test  == "I'm up!"
    }

}