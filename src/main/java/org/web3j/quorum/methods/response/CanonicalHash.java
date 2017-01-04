package org.web3j.quorum.methods.response;

import org.web3j.protocol.core.Response;

/**
 * quorum_canonicalHash
 */
public class CanonicalHash extends Response<String> {
    public String getCanonicalHash() {
        return getResult();
    }
}
