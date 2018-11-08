package org.web3j.quorum.enclave.http

import org.assertj.core.api.Assertions.*
import org.junit.Test

class RequestBuilderTest {

    @Test
    fun testEncodeJsonRequest() {
        val path = "send"
        val json = "{\"foo\":\"bar\"}"
        val result = RequestBuilder.encodeJsonRequest(path, json)

        assertThat(result).isEqualTo(
                """
                $POST /$path HTTP/1.1
                Host: k
                User-Agent: constellation-client
                Content-Type: application/json
                Content-Length: ${json.length}

                $json
                """.trimIndent()
        )
    }
}
