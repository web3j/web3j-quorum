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
package org.web3j.quorum.core

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo

import org.web3j.quorum.PAYLOAD
import org.web3j.quorum.TM1_PUBLIC_KEY
import org.web3j.quorum.TM2_PUBLIC_KEY
import org.web3j.quorum.constellation
import org.web3j.quorum.nodesC

/**
 * Useful integration tests for verifying Enclave transactions.
 *
 * <p>
 *     To use, start up 2 constellation instances with valid config and
 * hardcode the IPC path to connect to in constellationIpcPath1 and constellationIpcPath2
 * variables below.
 *
 */
@Disabled
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
        println(key)
        assertThat(key.length, equalTo(88))
    }

    @Test
    @Throws(Exception::class)
    fun testNodes() {
        for (count in 0..0) {
            for (i in 0..0) {
                val sourceNode = nodesC[i]
                val destNode = nodesC[(i + 1) % nodesC.size]

                val keyFile = "keyfiles/key" + (i + 1).toString()
                testRawTransactionsWithGreeterContract(sourceNode, destNode, keyFile, constellation[i])
                runPrivateHumanStandardTokenTest(sourceNode, destNode, keyFile, constellation[i])
            }
        }
    }
}
