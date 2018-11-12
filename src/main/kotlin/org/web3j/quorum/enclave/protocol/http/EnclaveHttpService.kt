package org.web3j.quorum.enclave.protocol.http

import org.web3j.quorum.enclave.protocol.EnclaveService

class EnclaveHttpService : EnclaveService {
    override fun <S, T> send(request: S, path: String, responseType: Class<T>): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendRaw(request: String, path: String, from: String, to: List<String>): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <S> send(request: S, path: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendJsonRequest(payload: String, path: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendRawJsonRequest(payload: String, path: String, from: String, to: List<String>): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun send(path: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun performIO(data: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}