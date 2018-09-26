package io.blk.konstellation.ipc

import org.web3j.protocol.ipc.IOFacade
import java.io.IOException

/**
 * Very simple IOFacade implementation for tests. *DO NOT* share a single
 * instance between multiple tests, as it is not thread safe.
 */
class MockIOFacade : IOFacade {

    private val store = hashMapOf<String, String>()
    private var response = ""

    fun add(request: String, response: String) {
        store[request] = response
    }

    override fun write(payload: String) {
        if (store.containsKey(payload)) {
            response = store.getOrDefault(payload, "")
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