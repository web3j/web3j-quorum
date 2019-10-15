package org.web3j.quorum.methods.response.raft;

import java.math.BigInteger;

import org.web3j.protocol.core.Response;

public class RaftPeerId extends Response<String> {
	public BigInteger getAddedPeer() {
		return new BigInteger(getResult());
	}

}
