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
package org.web3j.quorum.enclave

import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.quorum.PrivacyFlag
import org.web3j.quorum.Quorum
import org.web3j.quorum.enclave.protocol.EnclaveService

/**
 * Tessera is a stateless Java system that is used to enable the encryption, decryption,
 * and distribution of private transactions for Quorum.
 */
class Tessera(private val service: EnclaveService, private val web3: Quorum) : Enclave {

    override fun sendRawRequest(payload: String, privateFor: List<String>, privacyFlag: PrivacyFlag): EthSendTransaction {
        return web3.ethSendRawPrivateTransaction(payload, privateFor, privacyFlag).send()
    }

    // payload is hex encoded bytes
    // hash returned in response is base64 encoded string
    override fun storeRawRequest(payload: String, from: String, to: List<String>): SendResponse {
        val storeRawRequest = StoreRawRequest(payload, from)
        return service.send(storeRawRequest, "storeraw", SendResponse::class.java)
    }

    // not implemented in tessera
    override fun receiveRequest(key: String, to: String): ReceiveResponse {
        throw NotImplementedError("Tessera receive not supported")
    }

    override fun upCheck(): Boolean {
        val test = service.send("upcheck")
        return test == "I'm up!"
    }

    override fun deleteRequest(key: String): Boolean {
        throw NotImplementedError("Tessera delete not implemented")
    }
}
