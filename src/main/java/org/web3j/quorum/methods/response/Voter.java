package org.web3j.quorum.methods.response;

import org.web3j.protocol.core.Response;

/**
 * quorum_isVoter
 */
public class Voter extends Response<Boolean> {
    public boolean isVoter() {
        return getResult();
    }
}
