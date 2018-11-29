package org.web3j.quorum.enclave

import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.quorum.Quorum
import org.web3j.quorum.enclave.protocol.ipc.EnclaveIpcService

class Constellation(private val ipcService: EnclaveIpcService, private val web3: Quorum) : Enclave(ipcService) {
    override fun sendRawRequest(payload: String, privateFor: List<String>): EthSendTransaction {
        return  web3.ethSendRawTransaction(payload).send()
    }


    /**
     * Delete a payload from Enclave's secure enclave.
     */
    fun deleteRequest(key: String): Boolean {
        val deleteRequest = DeleteRequest(key)
        return ipcService.send(deleteRequest, "delete")
    }

}