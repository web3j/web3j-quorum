package org.web3j.quorum.enclave

import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.quorum.Quorum
import org.web3j.quorum.enclave.protocol.EnclaveService

/**
 * Constellation implements the "privacy engine" of Quorum, a fork of Ethereum with support for private
 * transactions.
 *
 * <p> In this library it is used to encrypt the payload of a private transaction.
 */
class Constellation(private val ipcService: EnclaveService, private val web3: Quorum) : Enclave {

    override fun sendRawRequest(payload: String, privateFor: List<String>): EthSendTransaction {
        return web3.ethSendRawTransaction(payload).send()
    }

	//payload needs to be base64 encoded string
	//returned hash is base64 encoded
    override fun storeRawRequest(payload: String, from: String, to: List<String>): SendResponse {
        val sendRequest = SendRequest(payload, from, to)
        return ipcService.send(sendRequest, "send", SendResponse::class.java)
    }

	// key is base64 encoded directly returned from constellation storeRawRequest
	//returned string is base64 encoded payload
    override fun receiveRequest(key: String, to: String): String {
        val receiveRequest = ReceiveRequest(key, to)
        return ipcService.send(receiveRequest, "receive", ReceiveResponse::class.java).payload
    }

    override fun upCheck(): Boolean {
        val test = ipcService.send( "upcheck")
        return test  == "OK"
    }

    override fun deleteRequest(key: String): Boolean {
        throw NotImplementedError("Constellation delete not implemented")
    }
}