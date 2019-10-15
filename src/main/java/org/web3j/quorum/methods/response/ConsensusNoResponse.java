package org.web3j.quorum.methods.response;

import org.web3j.protocol.core.Response;

public class ConsensusNoResponse extends Response<String>{
	
	public String getNoResponse() {
		return "success";
	}

}
