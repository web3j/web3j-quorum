package org.web3j.quorum.enclave

import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.quorum.Quorum
import org.web3j.quorum.enclave.protocol.EnclaveService
import org.web3j.quorum.methods.response.PrivatePayload

/**
 * Tessera is a stateless Java system that is used to enable the encryption, decryption,
 * and distribution of private transactions for Quorum.
 */
class Tessera(private val service: EnclaveService, private val web3: Quorum) : Enclave {

    override fun sendRawRequest(payload: String, privateFor: List<String>): EthSendTransaction {
        return web3.ethSendRawPrivateTransaction(payload, privateFor).send()
    }

	//payload is hex encoded bytes
	//hash returned in response is base64 encoded string
    override fun storeRawRequest(payload: String, from: String, to: List<String>): SendResponse {
        val storeRawRequest = StoreRawRequest(payload, from)
        return service.send(storeRawRequest, "storeraw", SendResponse::class.java)
    }

	// key is hex encoded payload string
	//returned string is hex encoded byte string
    override fun receiveRequest(key: String, to: String): String {
        return web3.quorumGetPrivatePayload(key).send().getPrivatePayload()
    }

    override fun upCheck(): Boolean {
        val test = service.send("upcheck")
        return test == "I'm up!"
    }

    override fun deleteRequest(key: String): Boolean {
        throw NotImplementedError("Tessera delete not implemented")
    }
}