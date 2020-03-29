@file:JvmName("ContractErrorUtil")

package org.web3j.tx

import org.web3j.protocol.core.methods.response.EthCall

fun assertCallNotReverted(ethCall: EthCall) = TransactionManager.assertCallNotReverted(ethCall)