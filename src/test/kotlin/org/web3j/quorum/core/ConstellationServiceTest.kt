package org.web3j.quorum.core

import org.assertj.core.api.Assertions.*
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import org.web3j.quorum.*

/**
 * Useful integration tests for verifying Enclave transactions.
 *
 * <p>
 *     To use, start up 2 constellation instances with valid config and
 * hardcode the IPC path to connect to in constellationIpcPath1 and constellationIpcPath2
 * variables below.
 *
 * <p>
 */
@Ignore
class ConstellationServiceTest : Helper() {

    @Test
    fun testUpCheck() {
        val upCheckResponse = constellation1.upCheck()
        assertTrue(upCheckResponse)
    }

    @Test
    fun testSendRequest() {
        val payload = PAYLOAD
        val from = TM1_PUBLIC_KEY
        val to = TM2_PUBLIC_KEY

        val sendResponse = constellation1.sendRequest(payload, from, listOf(to))
        println(sendResponse.key)
        val key = sendResponse.key
        assertThat(key).hasSize(88)

        val receiveResponse = constellation2.receiveRequest(key, TM2_PUBLIC_KEY)
        assertThat(receiveResponse.payload).isEqualTo(payload)

        assertTrue(constellation1.deleteRequest(key))
    }

    @Test
    @Throws(Exception::class)
    fun testNodes() {
        for (count in 0..0) {
            for (i in 0..0) {
                val sourceNode = nodesC[i]
                val destNode = nodesC[(i + 1) % nodesC.size]

                val keyFile = "keyfiles/key" + (i + 1).toString()
                testRawTransactionsWithGreeterContract(sourceNode, destNode, keyFile, constellation1)
                runPrivateHumanStandardTokenTest(sourceNode, destNode, keyFile, constellation1)
            }
        }
    }
}
