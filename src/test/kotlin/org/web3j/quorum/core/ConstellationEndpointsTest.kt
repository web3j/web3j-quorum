package org.web3j.quorum.core

import org.junit.Test
import org.junit.Assert.assertTrue
import org.assertj.core.api.Assertions.*
import org.junit.Before
import org.web3j.quorum.PAYLOAD
import org.web3j.quorum.TM1_PUBLIC_KEY
import org.web3j.quorum.TM2_PUBLIC_KEY
import org.web3j.quorum.enclave.Constellation
import org.web3j.quorum.enclave.protocol.ipc.MockEnclaveIpcService
import org.web3j.quorum.quorumConstellation

class ConstellationEndpointsTest {

    val from = TM1_PUBLIC_KEY
    val to = TM2_PUBLIC_KEY
    val payload = PAYLOAD
    val key = "LE1xbvgYbXuKRJ5zcLVNc+UELD9OxGgcReAYHqXJfEB6gXVr5unZmogscY0KMPpun3L3aex/M23GjTkdvzAOFg=="

    val upCheckRequest = """
            POST /upcheck HTTP/1.1
            Host: k
            User-Agent: constellation-client
            Content-Type: application/json
            Content-Length: 0


    """.trimIndent()

    val upCheckResponse = """
            HTTP/1.1 200 OK
            Transfer-Encoding: chunked
            Date: Mon, 07 Aug 2017 15:33:26 GMT..
            Server: Warp/3.2.12

            0007
            I'm up!
            0

    """.trimIndent()

    val sendRequest = """
            POST /send HTTP/1.1
            Host: k
            User-Agent: constellation-client
            Content-Type: application/json
            Content-Length: 146

            {"payload":"$payload","from":"$from","to":["$to"]}
    """.trimIndent()

    val sendResponse = """
            HTTP/1.1 200 OK
            Transfer-Encoding: chunked
            Date: Thu, 10 Aug 2017 12:23:16 GMT
            Server: Warp/3.2.12

            0062
            {"key":"$key"}
            0

    """.trimIndent()

    // using $from here is intentional
    val recieveRequest = """
            POST /receive HTTP/1.1
            Host: k
            User-Agent: constellation-client
            Content-Type: application/json
            Content-Length: 150

            {"key":"$key","to":"$from"}
    """.trimIndent()

    val receiveResponse = """
            HTTP/1.1 200 OK
            Transfer-Encoding: chunked
            Date: Thu, 10 Aug 2017 12:23:16 GMT
            Server: Warp/3.2.12

            0026
            {"payload":"$payload"}
            0

    """.trimIndent()

    val deleteRequest = """
            POST /delete HTTP/1.1
            Host: k
            User-Agent: constellation-client
            Content-Type: application/json
            Content-Length: 98

            {"key":"$key"}
    """.trimIndent()

    val deleteResponse = """
            HTTP/1.1 200 OK
            Transfer-Encoding: chunked
            Date: Thu, 10 Aug 2017 12:49:02 GMT
            Server: Warp/3.2.12

            0

    """.trimIndent()

    private lateinit var enclaveIpcService: MockEnclaveIpcService
    private lateinit var constellation: Constellation

    @Before
    fun setUp() {
        enclaveIpcService = MockEnclaveIpcService()
        constellation = Constellation(enclaveIpcService, quorumConstellation)
    }

    @Test
    fun testUpCheck() {

        enclaveIpcService.add(upCheckRequest, upCheckResponse)
        assertTrue(constellation.upCheck())
    }

    @Test
    fun testSend() {
        enclaveIpcService.add(sendRequest, sendResponse)
        val response = constellation.sendRequest(payload, from, listOf(to))
        assertThat(response.key).isEqualTo(key)
    }

    @Test
    fun testReceive() {
        enclaveIpcService.add(recieveRequest, receiveResponse)
        val response = constellation.receiveRequest(key, from)  // from is intentional here
        assertThat(response.payload).isEqualTo(payload)
    }

    @Test
    fun testDelete() {
        enclaveIpcService.add(deleteRequest, deleteResponse)
        assertTrue(constellation.deleteRequest(key))
    }
}
