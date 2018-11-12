package org.web3j.quorum.enclave.protocol.ipc

import org.web3j.protocol.ipc.IOFacade
import org.web3j.protocol.ipc.UnixDomainSocket

class UnixEnclaveIpcService(private val ipcSocketPath: String) : EnclaveIpcService() {

    override fun getIO(): IOFacade {
        return UnixDomainSocket(ipcSocketPath)
    }


}