package io.blk.konstellation.http

/**
 * HTTP Request builder.
 */
object RequestBuilder {

    fun encodeJsonRequest(path: String, payload: String): String {

        // to be true to HTTP 1.1 we have to include the Host header, which may be blank
        // https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.23
        val length = payload.length
        return """
            POST /$path HTTP/1.1
            Host: k
            User-Agent: konstellation-client
            Content-Type: application/json
            Content-Length: $length

            $payload
        """.trimIndent()
    }

    fun encodeGetRequest(path: String): String {
        return """
            GET /$path HTTP/1.1
            Host: k
            User-Agent: konstellation-client

        """.trimIndent()
    }
}