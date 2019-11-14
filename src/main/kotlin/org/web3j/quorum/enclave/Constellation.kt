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
import org.web3j.quorum.Quorum
import org.web3j.quorum.enclave.protocol.EnclaveService

/**
 * Constellation implements the "privacy engine" of Quorum, a fork of Ethereum with support for private
 * transactions.
 *
 * <p> In this library it is used to encrypt the payload of a private transaction.
 */
class Constellation(private val ipcService: EnclaveService, private val web3: Quorum) : Enclave {

    override fun sendRawRequest(payload: String, privateFor: List<String>): EthSendTransaction {
        return web3.ethSendRawTransaction(payload).send()
    }

    // payload needs to be base64 encoded string
    // returned hash is base64 encoded
    override fun storeRawRequest(payload: String, from: String, to: List<String>): SendResponse {
        val sendRequest = SendRequest(payload, from, to)
        return ipcService.send(sendRequest, "send", SendResponse::class.java)
    }

    // key is base64 encoded directly returned from constellation storeRawRequest
    // returned string is base64 encoded payload
    override fun receiveRequest(key: String, to: String): ReceiveResponse {
        val receiveRequest = ReceiveRequest(key, to)
        return ipcService.send(receiveRequest, "receive", ReceiveResponse::class.java)
    }

    override fun upCheck(): Boolean {
        val test = ipcService.send("upcheck")
        return test == "OK"
    }

    override fun deleteRequest(key: String): Boolean {
        throw NotImplementedError("Constellation delete not implemented")
    }
}
