package org.web3j.quorum.enclave.protocol.utils


data class Response(
        val statusCode: StatusCode,
        val headers: Map<String, String>,
        val body: String
)
