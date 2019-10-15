package org.web3j.quorum.methods.response.istanbul;

import java.util.List;

import org.web3j.protocol.core.Response;

public class IstanbulValidators extends Response<List<String>>{

	public List<String> getValidators() {
		return getResult();
	}
}
