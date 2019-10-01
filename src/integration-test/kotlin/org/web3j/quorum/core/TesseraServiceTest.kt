/*
 * Copyright 2019 Web3 Labs LTD.
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
package org.web3j.quorum.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import org.web3j.quorum.nodesT
import org.web3j.quorum.upCheckTessera
import org.web3j.quorum.PAYLOAD
import org.web3j.quorum.tessera

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
class TesseraServiceTest : Helper() {
    @Test
    fun testUpCheck() {
        val upCheckResponse = upCheckTessera.upCheck()
        assertTrue(upCheckResponse)
    }

    @Test
    fun testStoreRawRequest() {
        val payload = PAYLOAD
        val from = nodesT[0].publicKeys[0]

        val storeResponse = tessera[0].storeRawRequest(payload, from, emptyList())
        val key = storeResponse.key
        assertThat(key).hasSize(88)
    }

    @Test
    @Throws(Exception::class)
    fun testNodes() {
        for (count in 0..0) {
            for (i in 0..0) {
                val sourceNode = nodesT[i]
                val destNode = nodesT[(i + 1) % nodesT.size]

                val keyFile = "keyfiles/key" + (i + 1).toString()
                testRawTransactionsWithGreeterContract(sourceNode, destNode, keyFile, tessera[i])
                runPrivateHumanStandardTokenTest(sourceNode, destNode, keyFile, tessera[i])
            }
        }
    }
}
