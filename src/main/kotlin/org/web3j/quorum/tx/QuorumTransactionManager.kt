package org.web3j.quorum.tx

import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.quorum.tx.util.decode
import org.web3j.quorum.tx.util.encode
import org.web3j.quorum.Quorum
import org.web3j.quorum.enclave.Enclave
import org.web3j.quorum.enclave.SendResponse
import org.web3j.rlp.RlpDecoder
import org.web3j.rlp.RlpEncoder
import org.web3j.rlp.RlpList
import org.web3j.rlp.RlpString
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.TransactionManager
import org.web3j.utils.Numeric
import java.math.BigInteger

class QuorumTransactionManager(
        val web3j: Quorum, private val credentials: Credentials, private val publicKey: String,
        var privateFor: List<String> = listOf(),
        val enclave: Enclave,
        attempts: Int = TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH,
        sleepDuration: Long = TransactionManager.DEFAULT_POLLING_FREQUENCY) : RawTransactionManager(web3j, credentials, attempts, sleepDuration.toInt()) {

    // add extra constructor as java does not have optional parameters
    constructor(web3j: Quorum,
                credentials: Credentials,
                publicKey: String,
                privateFor: List<String> = listOf(),
                enclave: Enclave) : this(web3j, credentials, publicKey, privateFor, enclave, TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH, TransactionManager.DEFAULT_POLLING_FREQUENCY){
    }

    override fun sendTransaction(
            gasPrice: BigInteger?, gasLimit: BigInteger?, to: String?, data: String?,
            value: BigInteger?): EthSendTransaction {

        val nonce = nonce
        val rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, value, data)
        return signAndSend(rawTransaction)
    }

    override fun getFromAddress(): String {
        return credentials.address
    }
	
	fun storeRawRequest(payload: String, from: String, to: List<String>) : SendResponse {
		val payloadBase64 = encode(Numeric.hexStringToByteArray(payload))
		return enclave.storeRawRequest(payloadBase64, from, to)
	}
	
	override fun sign(rawTransaction: RawTransaction): String {
		var signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials)
		if(privateFor.isNotEmpty()) {
			signedMessage = setPrivate(signedMessage)
		}
		return Numeric.toHexString(signedMessage)
	}

    override fun signAndSend(rawTransaction: RawTransaction): EthSendTransaction {
        val signedMessage: ByteArray
        if (privateFor.isNotEmpty()) {
            val base64Encoded = encode(Numeric.hexStringToByteArray(rawTransaction.data))
            val response = enclave.storeRawRequest(base64Encoded, publicKey , privateFor)
            val responseDecoded = Numeric.toHexString(decode(response.key))

            val privateTransaction = RawTransaction.createTransaction(
                    rawTransaction.nonce, rawTransaction.gasPrice,
                    rawTransaction.gasLimit, rawTransaction.to,
                    rawTransaction.value, responseDecoded)

            val privateMessage = TransactionEncoder.signMessage(privateTransaction, credentials)

            signedMessage = setPrivate(privateMessage)
        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials)
        }
        val hexValue = Numeric.toHexString(signedMessage)
        return enclave.sendRawRequest(hexValue, privateFor)
    }
	
	fun sendRaw(signedTx: String, to: List<String>) : EthSendTransaction {
		return enclave.sendRawRequest(signedTx, to)
	}

    // If the byte array RLP decodes to a list of size >= 1 containing a list of size >= 3
    // then find the 3rd element from the last. If the element is a RlpString of size 1 then
    // it should be the V component from the SignatureData structure -> mark the transaction as private.
    // If any of of the above checks fails then return the original byte array.
    private fun setPrivate(message: ByteArray): ByteArray {
        var result = message
        val rlpWrappingList = RlpDecoder.decode(message)
        if (rlpWrappingList is RlpList) {
            if (!rlpWrappingList.values.isEmpty()) {
                val rlpList = rlpWrappingList.values[0]
                if (rlpList is RlpList) {
                    val rlpListSize = rlpList.values.size
                    if (rlpListSize > 3) {
                        val vField = rlpList.values[rlpListSize - 3]
                        if (vField is RlpString) {
                            if (1 == vField.bytes.size) {
                                when (vField.bytes[0]) {
                                    28.toByte() -> vField.bytes[0] = 38
                                    else -> vField.bytes[0] = 37
                                }
                                result = RlpEncoder.encode(rlpList)
                            }
                        }
                    }
                }
            }
        }
        return result
    }
}
