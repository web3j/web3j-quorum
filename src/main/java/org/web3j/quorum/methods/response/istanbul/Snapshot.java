package org.web3j.quorum.methods.response.istanbul;

import java.util.List;

public class Snapshot {
	
	private String epoch;
	private String hash;
	private String number;
	private String policy;
	private Tally tally;
	private List<String> validators;
	private List<Vote> votes;
	
	public Snapshot() {};
	
	public Snapshot(String epoch, String hash, String number, String policy, 
		Tally tally, List<String> validators, List<Vote> votes) {
	this.epoch = epoch;
	this.hash = hash;
	this.setNumber(number);
	this.setPolicy(policy);
	this.setTally(tally);
	this.setValidators(validators);
	this.setVotes(votes);
}
public String getEpoch() {
	return epoch;
}
public void setEpoch(String epoch) {
	this.epoch = epoch;
}
public String getHash() {
	return hash;
}
public void setHash(String hash) {
	this.hash = hash;
}

public String getNumber() {
	return number;
}

public void setNumber(String number) {
	this.number = number;
}

public String getPolicy() {
	return policy;
}

public void setPolicy(String policy) {
	this.policy = policy;
}

public Tally getTally() {
	return tally;
}

public void setTally(Tally tally) {
	this.tally = tally;
}

public List<String> getValidators() {
	return validators;
}

public void setValidators(List<String> validators) {
	this.validators = validators;
}

public List<Vote> getVotes() {
	return votes;
}

public void setVotes(List<Vote> votes) {
	this.votes = votes;
}

@Override
public boolean equals(Object o) {
	if (this == o)
		return true;
	if (!(o instanceof Snapshot)) {
		return false;
	}
	
	Snapshot that = (Snapshot) o;
	
	if(getEpoch() != null ? !getEpoch().equals(that.getEpoch()) : that.getEpoch() != null) {
		return false;
	}
	
	if(getHash() != null ? !getHash().equals(that.getHash()) : that.getHash() != null) {
		return false;
	}
	
	if(getNumber() != null ? !getNumber().equals(that.getNumber()) : that.getNumber() != null) {
		return false;
	}
	
	if(getPolicy() != null ? !getPolicy().equals(that.getPolicy()) : that.getPolicy() != null) {
		return false;
	}
	
	if(getTally() != null ? !getTally().equals(that.getTally()) : that.getTally() != null) {
		return false;
	}
	
	if(getValidators() != null ? !getValidators().equals(that.getValidators()) : that.getValidators() != null) {
		return false;
	}
	
	return getVotes() != null ? !getVotes().equals(that.getVotes()) : that.getVotes() != null;
}

@Override
public int hashCode() {
	int result = getEpoch() != null ? getEpoch().hashCode() : 0;
	result = 31 * result + (hash != null ? hash.hashCode() : 0);
	result = 31 * result + (number != null ? number.hashCode() : 0);
	result = 31 * result + (policy != null ? policy.hashCode() : 0);
	result = 31 * result + (tally != null ? tally.hashCode() : 0);
	result = 31 * result + (validators != null ? validators.hashCode() : 0);
	result = 31 * result + (votes != null ? votes.hashCode() : 0);
	
	return result;
}

@Override
public String toString() {
	return "Snapshot{"
			+ "epoch='"
			+ epoch
			+'\''
			+ ", hash='"
			+ hash
			+'\''
			+ ", number='"
			+ number
			+'\''
			+ ", policy='"
			+ policy
			+'\''
			+ ", tally="
			+ tally
			+ ", validators="
			+ validators
			+ ", votes="
			+ votes
			+ '}';
}
}
