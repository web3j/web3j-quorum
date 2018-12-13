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
 */
@Ignore
class ConstellationServiceTest : Helper() {

    @Test
    fun testUpCheck() {
        val upCheckResponse = constellation[0].upCheck()
        assertTrue(upCheckResponse)
    }

    @Test
    fun testSendRequest() {
        val payload = PAYLOAD
        val from = TM1_PUBLIC_KEY
        val to = TM2_PUBLIC_KEY

        val sendResponse = constellation[0].storeRawRequest(payload, from, listOf(to))
        val key = sendResponse.key
        assertThat(key).hasSize(88)

        assertTrue(constellation[0].deleteRequest(key))
    }

    @Test
    @Throws(Exception::class)
    fun testNodes() {
        for (count in 1..1) {
            for (i in 1..1) {
                val sourceNode = nodesC[i]
                val destNode = nodesC[(i + 1) % nodesC.size]

                val keyFile = "keyfiles/key" + (i + 1).toString()
                testRawTransactionsWithGreeterContract(sourceNode, destNode, keyFile, constellation[i])
                runPrivateHumanStandardTokenTest(sourceNode, destNode, keyFile, constellation[i])
            }
        }
    }
}
