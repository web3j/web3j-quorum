package org.web3j.quorum.core

import okhttp3.OkHttpClient
import org.junit.Test
import org.junit.Assert.assertTrue
import org.assertj.core.api.Assertions.*
import org.junit.Before
import org.web3j.quorum.*
import org.web3j.quorum.enclave.Constellation
import org.web3j.quorum.enclave.Enclave
import org.web3j.quorum.enclave.protocol.ipc.EnclaveIpcService
import java.io.File


class ConstellationEndpointsTest {

    val from = TM1_PUBLIC_KEY
    val to = TM2_PUBLIC_KEY
    val payload = PAYLOAD
    val key = "P2yURduJk7w6pHArUlOlAbi7JNlKWzJuJ3/F1onREjnn6Yw/+7JONkIbZfkp5yjrcSAsXAyUSDOrlFMTSk0juA=="

    @Test
    fun testUpCheck() {
        assertTrue(constellation[0].upCheck())
    }

    @Test
    fun testSend() {
        val response = constellation[0].storeRawRequest(payload, from, listOf(to))
        assertThat(response.key.length).isEqualTo(88)
    }

    @Test
    fun testReceive() {
        val response = constellation[0].receiveRequest(key, from)  // from is intentional here
        assertThat(response.payload).isEqualTo(payload)
    }
}
