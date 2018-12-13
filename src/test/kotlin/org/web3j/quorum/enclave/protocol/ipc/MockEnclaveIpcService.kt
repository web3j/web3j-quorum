package org.web3j.quorum.enclave.protocol.ipc

import org.web3j.protocol.ipc.IOFacade

/**
 * Very simple IOFacade implementation for tests. *DO NOT* share a single
 * instance between multiple tests, as it is not thread safe.
 */
class MockEnclaveIpcService : EnclaveIpcService() {

    companion object {
        val store = hashMapOf<String, String>()
    }

    fun add(request: String, response: String) {
        store[request] = response
    }

    override fun getIO(): IOFacade {
        return MockIoFacade()
    }

}

private class MockIoFacade: IOFacade {

    private var response = ""

    override fun write(payload: String) {
        if (MockEnclaveIpcService.store.containsKey(payload)) {
            response = MockEnclaveIpcService.store.getOrDefault(payload, "")
        } else {
            throw RuntimeException("Unexpected request received: $payload")
        }
    }

    override fun read(): String {
        if (response.isEmpty()) {
            throw RuntimeException("No response provided")
        } else {
            val data = response
            response = ""
            return data
        }
    }
    override fun close(){}
}


