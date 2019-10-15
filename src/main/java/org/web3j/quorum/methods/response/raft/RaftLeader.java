package org.web3j.quorum.methods.response.raft;

import org.web3j.protocol.core.Response;

public class RaftLeader extends Response<String> {
	public String getLeader() {
		return getResult();
	}
}
