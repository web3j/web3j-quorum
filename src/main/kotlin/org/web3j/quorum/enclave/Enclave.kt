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
package org.web3j.quorum.enclave

import org.web3j.protocol.core.methods.response.EthSendTransaction

/**
 * Enclave API methods.
 */
interface Enclave {
    /**
     * Send a new raw payload to the quorum node for signing and storing
     */
    fun sendRawRequest(payload: String, privateFor: List<String>): EthSendTransaction

    /**
     * Store raw transaction in the secure enclave only
     */
    fun storeRawRequest(payload: String, from: String, to: List<String>): SendResponse

    /**
     * Retrieve a payload from the secure enclave
     */
    fun receiveRequest(key: String, to: String): String

    /**
     * Verify that our enclave node is running
     */
    fun upCheck(): Boolean

    /**
     * Delete a payload from the secure enclave only
     * @param key The enclave key
     */
    fun deleteRequest(key: String): Boolean
}
