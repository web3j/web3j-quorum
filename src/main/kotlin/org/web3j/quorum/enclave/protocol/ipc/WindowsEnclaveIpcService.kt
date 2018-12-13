package org.web3j.quorum.enclave.protocol.ipc

import org.web3j.protocol.ipc.IOFacade
import org.web3j.protocol.ipc.WindowsNamedPipe

/**
 * WindowsEnclaveIpcService supports connection to a private enclave via an IPC file residing on Windows machine.
 */
class WindowsEnclaveIpcService(private val ipcSocketPath: String) : EnclaveIpcService() {
    override fun getIO(): IOFacade {
        return WindowsNamedPipe(ipcSocketPath)
    }
}