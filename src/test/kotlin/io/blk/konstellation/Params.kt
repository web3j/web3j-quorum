package io.blk.konstellation

import java.util.*

/**
 * Common parameters for unit tests.
 */

// ASCII base 64 encoded public keys for our transaction managers
const val TM1_PUBLIC_KEY = "3LnFRVF4O/qN3dhXA+XC2D+waQ3n8Z72UfBRW3orWRM="
const val TM2_PUBLIC_KEY = "9TU3ZPjDGr5fciMO4Bm0rXkpkQW5WlZozrs0IcJMWSM="

// ASCII base 64 encoded payload
val PAYLOAD: String = Base64.getEncoder().encodeToString("message payload1".toByteArray())

