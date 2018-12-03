package org.web3j.quorum.tx.util

import java.util.*

fun encode(payload: ByteArray) = Base64.getEncoder().encodeToString(payload)!!

fun encode(payload: String) = encode(payload.toByteArray())

fun decode(payload: ByteArray) = Base64.getDecoder().decode(payload)

fun decode(payload: String) = Base64.getDecoder().decode(payload)
