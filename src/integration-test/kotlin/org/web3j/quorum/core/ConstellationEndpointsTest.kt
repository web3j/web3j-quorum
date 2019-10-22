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

import org.junit.Test
import org.junit.Assert.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.web3j.quorum.TM1_PUBLIC_KEY
import org.web3j.quorum.TM2_PUBLIC_KEY
import org.web3j.quorum.PAYLOAD
import org.web3j.quorum.constellation

@Ignore
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
        val response = constellation[0].receiveRequest(key, from) // from is intentional here
        assertThat(response).isEqualTo(payload)
    }
}
