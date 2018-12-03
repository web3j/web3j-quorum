package org.web3j.quorum.enclave

import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.quorum.Quorum
import org.web3j.quorum.enclave.protocol.ipc.EnclaveIpcService

class Constellation(private val ipcService: EnclaveIpcService, private val web3: Quorum) : Enclave(ipcService) {
    /**
     * Send the encrypted payload to the Constellation's endpoint
     */
    override fun sendRawRequest(payload: String, privateFor: List<String>): EthSendTransaction {
        return web3.ethSendRawTransaction(payload).send()
    }

    override fun storeRawRequest(payload: String, from: String, to: List<String>): SendResponse {
        return super.sendRequest(payload, from, to)
    }

    /**
     * Delete a payload from Enclave's secure enclave.
     */
    fun deleteRequest(key: String): Boolean {
        val deleteRequest = DeleteRequest(key)
        return ipcService.send(deleteRequest, "delete")
    }

    /**
     * Verify that our node is running
     */
    override fun upCheck(): Boolean {
        val test = ipcService.sendJsonRequest("", "upcheck")
        return test  == "I'm up!"
    }

}