package org.web3j.quorum.enclave

class SendRequest(
        val payload: String,
        val from: String,
        val to: List<String>
)

class SendResponse(
        var key: String
)

class ReceiveRequest(
        val key: String,
        val to: String
)

class ReceiveResponse(
        val payload: String
)

class DeleteRequest(
        val key: String
)

class StoreRawRequest(
        val payload: String,
        val from: String
)
