/*
 * Copyright 2019 Web3 Labs LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j.quorum.enclave.protocol.utils

/**
 * HTTP Request builder.
 */
object RequestBuilder {

    fun encodeRawJsonRequest(path: String, payload: String, from: String, to: List<String>): String {

        // to be true to HTTP 1.1 we have to include the Host header, which may be blank
        // https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.23
        val length = payload.length
        val toString = to.joinToString()
        return """
            POST /$path HTTP/1.1
            Host: k
            User-Agent: constellation-client
            Content-Type: application/json
            Content-Length: $length
            c11n-from: $from
            c11n-to: $toString

            $payload
        """.trimIndent()
    }

    fun encodeJsonRequest(path: String, payload: String): String {

        // to be true to HTTP 1.1 we have to include the Host header, which may be blank
        // https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.23
        val length = payload.length
        return """
            POST /$path HTTP/1.1
            Host: k
            User-Agent: constellation-client
            Content-Type: application/json
            Content-Length: $length

            $payload
        """.trimIndent()
    }

    fun encodeGetRequest(path: String): String {
        return """
            GET /$path HTTP/1.1
            Host: k
            User-Agent: constellation-client

        """.trimIndent()
    }
}
