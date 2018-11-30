package org.web3j.quorum.enclave.protocol


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