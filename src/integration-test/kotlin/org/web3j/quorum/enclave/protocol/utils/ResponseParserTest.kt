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

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo

class ResponseParserTest {

    val httpOkChunkedResponse = """
            HTTP/1.1 200 OK
            Transfer-Encoding: chunked
            Date: Wed, 02 Aug 2017 21:48:33 GMT
            Server: Warp/3.2.12

            0062
            {"key":"Uxev9Cj2I6yT1KPolXkNfe9+PsjAQkSROLhPkNCL/EpBVpsDxf2UZUEP7Ic64+0PkNOllRhZQ/m107ojcq8gIg=="}
            0


        """.trimIndent()

    val httpOkCHunkedResponseEmpty = """
            HTTP/1.1 200 OK
            Transfer-Encoding: chunked
            Date: Wed, 09 Aug 2017 21:04:54 GMT
            Server: Warp/3.2.12

            0


            """.trimIndent()

    val httpBadRequestResponse = """
            HTTP/1.1 400 Bad Request
            Transfer-Encoding: chunked
            Date: Thu, 03 Aug 2017 07:44:11 GMT
            Server: Warp/3.2.12

            000B
            Bad Request
            0


        """.trimIndent()

    val httpInternalServerErrorResponse = """
            HTTP/1.0 500 Internal Server Error
            Date: Thu, 03 Aug 2017 07:51:11 GMT
            Server: Warp/3.2.12
            Content-Type: text/plain; charset=utf-8

            Something went wrong

        """.trimIndent()

    @Test
    fun testParseHttpResponse() {
        val expected = Response(
                StatusCode(200, "OK"),
                hashMapOf(
                        Pair("Transfer-Encoding", "chunked"),
                        Pair("Server", "Warp/3.2.12"),
                        Pair("Date", "Wed, 02 Aug 2017 21:48:33 GMT")),
                """
                0062
                {"key":"Uxev9Cj2I6yT1KPolXkNfe9+PsjAQkSROLhPkNCL/EpBVpsDxf2UZUEP7Ic64+0PkNOllRhZQ/m107ojcq8gIg=="}
                0


                """.trimIndent())
        val result = ResponseParser.parseResponse(httpOkChunkedResponse)
        assertTrue(result == expected)
    }

    @Test
    fun testParseHttpResponseBadRequest() {
        val expected = Response(
                StatusCode(400, "Bad Request"),
                hashMapOf(
                        Pair("Transfer-Encoding", "chunked"),
                        Pair("Server", "Warp/3.2.12"),
                        Pair("Date", "Thu, 03 Aug 2017 07:44:11 GMT")),
                """
                000B
                Bad Request
                0


                """.trimIndent()
        )
        val result = ResponseParser.parseResponse(httpBadRequestResponse)
        assertTrue(result == expected)
    }

    @Test
    fun testParseHttpResponseInternalServerError() {
        val expected = Response(
                StatusCode(500, "Internal Server Error"),
                hashMapOf(
                        Pair("Content-Type", "text/plain; charset=utf-8"),
                        Pair("Server", "Warp/3.2.12"),
                        Pair("Date", "Thu, 03 Aug 2017 07:51:11 GMT")),
                """
                Something went wrong

                """.trimIndent()
        )
        val result = ResponseParser.parseResponse(httpInternalServerErrorResponse)
        assertTrue(result == expected)
    }

    @Test
    fun testParseHttpTextResponse() {
        val response = ResponseParser.parseResponse(httpInternalServerErrorResponse)
        val text = ResponseParser.parseTextPlainBody(response)
        assertThat(text, equalTo("Something went wrong"))
    }

    @Test
    fun testParseHttpChunkedResponse() {
        val chunk = ResponseParser.parseChunkedResponse(httpOkChunkedResponse)
        assertThat(chunk, equalTo(
                """
                {"key":"Uxev9Cj2I6yT1KPolXkNfe9+PsjAQkSROLhPkNCL/EpBVpsDxf2UZUEP7Ic64+0PkNOllRhZQ/m107ojcq8gIg=="}
                """.trim())
        )
    }

    @Test
    fun testParseHttpEmptyChunkedResponse() {
        val chunk = ResponseParser.parseChunkedResponse(httpOkCHunkedResponseEmpty)
        assertThat(chunk, equalTo(""))
    }
}
