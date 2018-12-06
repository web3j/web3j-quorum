package org.web3j.quorum.enclave

import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.quorum.Quorum
import org.web3j.quorum.enclave.protocol.EnclaveService

class Tessera(private val service: EnclaveService, private val web3: Quorum) : Enclave {

    override fun sendRawRequest(payload: String, privateFor: List<String>): EthSendTransaction {
        return web3.ethSendRawPrivateTransaction(payload, privateFor).send()
    }

    override fun storeRawRequest(payload: String, from: String, to: List<String>): SendResponse {
        val storeRawRequest = StoreRawRequest(payload, from)
        return service.send(storeRawRequest, "storeraw", SendResponse::class.java)
    }

    override fun receiveRequest(key: String, to: String): ReceiveResponse {
        val receiveRequest = ReceiveRequest(key, to)
        return service.send(receiveRequest, "receive", ReceiveResponse::class.java)
    }

    override fun upCheck(): Boolean {
        val test = service.send("upcheck")
        return test == "I'm up!"
    }

    override fun deleteRequest(key: String): Boolean {
        throw NotImplementedError("Tessera delete not implemented")
    }
}