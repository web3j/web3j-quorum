package io.blk.konstellation.core

import io.blk.konstellation.ipc.IpcService
import io.blk.konstellation.PAYLOAD
import io.blk.konstellation.protocol.Constellation
import io.blk.konstellation.TM1_PUBLIC_KEY
import io.blk.konstellation.TM2_PUBLIC_KEY
import org.assertj.core.api.Assertions.*
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import org.web3j.protocol.ipc.UnixDomainSocket

@Ignore
class ConstellationIT {

    val constellation1 = Constellation(
            IpcService(UnixDomainSocket("/Users/sebastianraba/Desktop/work/web3j-quorum/constellation/data/constellation.ipc")))
    val constellation2 = Constellation(
            IpcService(UnixDomainSocket("/Users/sebastianraba/Desktop/work/web3j-quorum/constellation/data1/constellation.ipc")))

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

        // from address should always be our Constellation node's public key
        val sendResponse = constellation1.sendRequest(payload, from, listOf(to))
        val key = sendResponse.key
        assertThat(key).hasSize(88)

        // to address should always be our Constellation node's public key
        // we can't obtain the payload from tm1, for background as to why this is
        // see https://github.com/jpmorganchase/constellation/issues/47
        val receiveResponse = constellation2.receiveRequest(key, TM2_PUBLIC_KEY)
        assertThat(receiveResponse.payload).isEqualTo(payload)

        assertTrue(constellation1.deleteRequest(key))
    }
}