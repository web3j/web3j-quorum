package org.web3j.quorum.enclave

import org.web3j.quorum.enclave.protocol.ipc.EnclaveIpcService

class Constellation(private val ipcService: EnclaveIpcService) : Enclave(ipcService) {

    /**
     * Delete a payload from Enclave's secure enclave.
     */
    fun deleteRequest(key: String): Boolean {
        val deleteRequest = DeleteRequest(key)
        return ipcService.send(deleteRequest, "delete")
    }

}