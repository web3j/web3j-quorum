package org.web3j.quorum.methods.response;

import org.web3j.protocol.core.Response;

/**
 * quorum_makeBlock
 */
public class MakeBlock extends Response<String> {
    public String getBlockHash() {
        return getResult();
    }
}
