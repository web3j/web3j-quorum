package org.web3j.quorum.enclave.protocol

/**
 * EnclaveService defines a interface for the send methods that support communication
 * with the private enclave [Tessera/Constellation]
 */
interface EnclaveService {
    /**
     * Send a new request to Enclave for secure enclave.
     */
    fun <S, T> send(request: S, path: String, responseType: Class<T>): T

    /**
     * Send a new payload to Enclave for secure enclave.
     */
    fun send(path: String): String
}