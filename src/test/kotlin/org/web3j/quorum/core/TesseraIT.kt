package org.web3j.quorum.core

import org.assertj.core.api.Assertions.*
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import org.web3j.quorum.PAYLOAD
import org.web3j.quorum.TM1_PUBLIC_KEY
import org.web3j.quorum.TM2_PUBLIC_KEY
import org.web3j.quorum.enclave.Constellation
import org.web3j.quorum.enclave.StoreRawRequest
import org.web3j.quorum.enclave.Tessera
import org.web3j.quorum.enclave.protocol.http.EnclaveHttpService
import org.web3j.quorum.enclave.protocol.ipc.UnixEnclaveIpcService

/**
 * Useful integration tests for verifying Enclave transactions.
 *
 * <p>
 *     To use, start up 2 Tessera instances with valid config and
 * hardcode the IPC path to connect to in constellationIpcPath1 and constellationIpcPath2
 * variables below.
 *
 * <p>
 */
@Ignore
class TesseraIT {

    @Test
    fun testUpCheck() {
        val url = "http://0.0.0.0"
        val tessera = Tessera(EnclaveHttpService(url, 8080))
        val upCheckResponse = tessera.upCheck()
        assertTrue(upCheckResponse)
    }

    @Test
    fun testStoreRawRequest() {
        val url = "http://0.0.0.0"
        val tessera = Tessera(EnclaveHttpService(url, 8090))
        // ASCII base 64 encoded payload
        val payload = PAYLOAD
        val from = "/+UuD63zItL1EbjxkKUljMgG8Z1w0AJ8pNOR4iq2yQc="

        val storeResponse = tessera.storeRawRequest(payload, from)
        println(storeResponse.key)
        val key = storeResponse.key
        assertThat(key).hasSize(88)
    }
}
