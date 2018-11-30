package org.web3j.quorum.enclave.protocol.ipc

import org.web3j.protocol.ipc.IOFacade
import org.web3j.protocol.ipc.WindowsNamedPipe

class WindowsEnclaveIpcService(private val ipcSocketPath: String) : EnclaveIpcService() {
    override fun getIO(): IOFacade {
        return WindowsNamedPipe(ipcSocketPath)
    }
}