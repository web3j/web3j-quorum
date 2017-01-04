package org.web3j.quorum.methods.response;

import org.web3j.protocol.core.Response;

/**
 * quorum_isBlockMaker
 */
public class BlockMaker extends Response<Boolean> {
    public boolean isBlockMaker() {
        return getResult();
    }
}
