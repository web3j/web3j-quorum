package io.blk.konstellation.http

data class Response(
        val statusCode: StatusCode,
        val headers: Map<String, String>,
        val body: String
)
