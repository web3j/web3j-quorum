package org.web3j.quorum.core

import org.assertj.core.api.Assertions.*
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import org.web3j.quorum.PAYLOAD
import org.web3j.quorum.TM1_PUBLIC_KEY
import org.web3j.quorum.TM2_PUBLIC_KEY
import org.web3j.quorum.enclave.Constellation
import org.web3j.quorum.enclave.protocol.ipc.UnixEnclaveIpcService

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
class ConstellationIT {

    val constellationIpcPath1 = "/Users/sebastianraba/Desktop/work/web3js-quorum/constellation/data/constellation.ipc"
    val constellationIpcPath2 = "/Users/sebastianraba/Desktop/work/web3js-quorum/constellation/data1/constellation.ipc"
    val constellation1 = Constellation(UnixEnclaveIpcService(constellationIpcPath1))
    val constellation2 = Constellation(UnixEnclaveIpcService(constellationIpcPath2))

    @Test
    fun testUpCheck() {
        val upCheckResponse = constellation1.upCheck()
        assertTrue(upCheckResponse)
    }

    @Test
    fun testSendRequest() {
        // ASCII base 64 encoded payload
        val payload = PAYLOAD
        val from = TM1_PUBLIC_KEY
        val to = TM2_PUBLIC_KEY

        // from address should always be our Enclave node's public key
        val sendResponse = constellation1.sendRequest(payload, from, listOf(to))
        println(sendResponse.key)
        val key = sendResponse.key
        assertThat(key).hasSize(88)

        // to address should always be our Enclave node's public key
        // we can't obtain the payload from tm1, for background as to why this is
        // see https://github.com/jpmorganchase/constellation/issues/47


        val receiveResponse = constellation2.receiveRequest(key, TM2_PUBLIC_KEY)
        assertThat(receiveResponse.payload).isEqualTo(payload)

        assertTrue(constellation1.deleteRequest(key))
    }
}
