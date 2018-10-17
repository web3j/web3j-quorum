package org.web3j.quorum.enclave.http


data class Response(
        val statusCode: StatusCode,
        val headers: Map<String, String>,
        val body: String
)
