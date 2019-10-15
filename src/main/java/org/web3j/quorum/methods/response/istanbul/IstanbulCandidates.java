package org.web3j.quorum.methods.response.istanbul;

import java.util.Map;

import org.web3j.protocol.core.Response;

public class IstanbulCandidates extends Response<Map<String, Boolean>>{

	public Map<String, Boolean> getCandidates() {
		return getResult();
	}

}
