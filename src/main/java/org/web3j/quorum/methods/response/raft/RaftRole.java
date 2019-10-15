package org.web3j.quorum.methods.response.raft;

import org.web3j.protocol.core.Response;

public class RaftRole extends Response<String> {
	public String getRole() {
		return getResult();
	}

}
