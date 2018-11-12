package org.web3j.quorum

import java.util.*

/**
 * Common parameters for unit tests.
 */

// ASCII base 64 encoded public keys for our transaction managers
const val TM1_PUBLIC_KEY = "BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo="
const val TM2_PUBLIC_KEY = "QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc="

// ASCII base 64 encoded payload
val PAYLOAD: String = Base64.getEncoder().encodeToString("message payload1".toByteArray())

