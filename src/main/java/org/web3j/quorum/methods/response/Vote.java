package org.web3j.quorum.methods.response;

import org.web3j.protocol.core.Response;

/**
 * quorum_vote
 */
public class Vote extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
