package org.web3j.quorum.tx

import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.quorum.tx.util.decode
import org.web3j.quorum.tx.util.encode
import org.web3j.quorum.Quorum
import org.web3j.quorum.enclave.Enclave
import org.web3j.tx.RawTransactionManager
import org.web3j.utils.Numeric
import java.math.BigInteger


class QuorumTransactionManager(
        val web3j: Quorum, val credentials: Credentials, val publicKey: String,
        var privateFor: List<String> = listOf(),
        val enclave: Enclave, val sleepDuration: Int = 500,
        val attempts: Int = DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH) : RawTransactionManager(web3j, credentials,
        attempts, sleepDuration) {

    override fun sendTransaction(
            gasPrice: BigInteger?, gasLimit: BigInteger?, to: String?, data: String?,
            value: BigInteger?): EthSendTransaction {

        val nonce = getNonce()
        val rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, value, data)
        return signAndSend(rawTransaction)
    }

    override fun getFromAddress(): String {
        return credentials.address
    }

    override fun signAndSend(rawTransaction: RawTransaction): EthSendTransaction {
        // See notes on this at https://github.com/jpmorganchase/quorum/pull/146
        val signedMessage: ByteArray
        if (privateFor.isNotEmpty()) {
            // only the data is encoded, must be converted to bytes
            val base64Encoded = encode(Numeric.hexStringToByteArray(rawTransaction.data))

            val response = enclave.sendRequest(base64Encoded, publicKey, privateFor)
            val responseDecoded = Numeric.toHexString(decode(response.key))

            val privateTransaction = RawTransaction.createTransaction(
                    rawTransaction.nonce, rawTransaction.gasPrice,
                    rawTransaction.gasLimit, rawTransaction.to,
                    rawTransaction.value, responseDecoded)

            val privateMessage = TransactionEncoder.signMessage(privateTransaction, credentials)

            signedMessage = setPrivate(privateMessage)
            val hexValue = Numeric.toHexString(signedMessage)

            return web3j.ethSendRawPrivateTransaction(hexValue, privateFor).send()

        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials)
        }
        val hexValue = Numeric.toHexString(signedMessage)

        return web3j.ethSendRawTransaction(hexValue).send()
    }

    fun isPrivate(v: Int) =
            when (v) {
                37 -> true
                38 -> true
                else -> false
            }

    fun setPrivate(message: ByteArray): ByteArray {
        // v is at offset 0 in our signed message
        val vOffset = message.size - 67
        when (message[vOffset]) {
            28.toByte() -> message[vOffset] = 38
            else -> message[vOffset] = 37
        }
        return message
    }
}