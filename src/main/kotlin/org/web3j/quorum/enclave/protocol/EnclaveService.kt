package org.web3j.quorum.enclave.protocol


interface EnclaveService {

    fun <S, T> send(request: S, path: String, responseType: Class<T>): T

    fun sendRaw(request: String, path: String, from: String, to: List<String>): String

    fun <S> send(request: S, path: String): Boolean

    fun sendJsonRequest(payload: String, path: String): String

    fun sendRawJsonRequest(payload: String, path: String, from: String, to: List<String>): String

    fun send(path: String): String

    fun performIO(data: String): String
}