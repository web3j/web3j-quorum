/*
 * Copyright 2019 Web3 Labs Ltd.
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

import java.io.BufferedReader
import java.io.StringReader

/**
 * A simple HTTP response parser.
 */
object ResponseParser {
    fun parseResponse(response: String): Response {
        if (response.isEmpty()) {
            throw ResponseParserException("Empty response received")
        }

        val bufferedReader = BufferedReader(StringReader(response))

        val statusCode = parseStatusCode(bufferedReader.readLine())

        val headers = hashMapOf<String, String>()
        var header = bufferedReader.readLine()
        while (header.isNotEmpty()) {
            val kv = parseHeader(header)
            headers[kv.first] = kv.second
            header = bufferedReader.readLine()
        }

        val body = bufferedReader.readText()

        return Response(statusCode, headers, body)
    }

    private fun parseStatusCode(value: String): StatusCode {
        val params = value.split(" ".toRegex(), 3)

        if (params.size != 3) {
            throw RuntimeException("Unable to parse status code: $params")
        }

        val statusCode = Integer.parseInt(params[1])
        val message = params[2]

        return StatusCode(statusCode, message)
    }

    private fun parseHeader(value: String): Pair<String, String> {
        val kv = value.split(": ")
        return Pair(kv[0], kv[1])
    }

    fun parseTextPlainBody(response: Response): String {
        verifyHeader("Content-Type", "text/plain; charset=utf-8", response)

        val bufferedReader = BufferedReader(StringReader(response.body))
        return bufferedReader.readLine()
    }

    private fun parseChunkedBody(response: Response): List<String> {
        verifyHeader("Transfer-Encoding", "chunked", response)

        val chunks = arrayListOf<String>()
        val bufferedReader = BufferedReader(StringReader(response.body))

        var chunkLength = bufferedReader.readLine()
        while (chunkLength != "0") {
            val length = Integer.parseInt(chunkLength, 16)
            val chunk = bufferedReader.readLine()
            if (chunk.length != length) {
                throw ResponseParserException("Chunk length $length did not match actual")
            }
            chunks.add(chunk)
            chunkLength = bufferedReader.readLine()
        }
        return chunks
    }

    fun parseChunkedResponse(rawResponse: String): String {
        val response = parseResponse(rawResponse)
        verifyResponseCode(response)

        val chunks = parseChunkedBody(response)
        if (chunks.size == 1) {
            return chunks[0]
        } else if (chunks.isEmpty()) {
            return ""
        } else {
            throw ResponseParserException("Multiple chunks (${chunks.size}) returned, expected 1")
        }
    }

    private fun verifyHeader(
        headerName: String,
        expectedHeaderValue: String,
        response: Response
    ) {
        val transferEncoding = response.headers[headerName]
        if (transferEncoding != expectedHeaderValue) {
            throw ResponseParserException("Unexpected Transfer-Encoding: $transferEncoding")
        }
    }

    private fun verifyResponseCode(response: Response) {
        if (response.statusCode.code != 200) {
            throw ResponseParserException("Request failed: ${response.statusCode}")
        }
    }
}
