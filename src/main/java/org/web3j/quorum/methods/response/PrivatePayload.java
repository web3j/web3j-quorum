package org.web3j.quorum.methods.response;

import org.web3j.protocol.core.Response;

/**
 * quorum_getPrivatePayload
 */
public class PrivatePayload extends Response<String> {
    public String getPrivatePayload() {
        return getResult();
    }
}
