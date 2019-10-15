package org.web3j.quorum.methods.response.istanbul;

public class Tally {

	private boolean authorize;
	private int votes;
	
	public Tally() {};
	
	public Tally(boolean authorize, int votes) {
		this.setAuthorize(authorize);
		this.setVotes(votes);
	}

	public boolean isAuthorize() {
		return authorize;
	}

	public void setAuthorize(boolean authorize) {
		this.authorize = authorize;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Tally)) {
			return false;
		}
		
		Tally that = (Tally) o;
		
		if(isAuthorize() != that.isAuthorize()) {
			return false;
		}
		
		return getVotes() != that.getVotes() ;
	}
	
	@Override
	public int hashCode() {
		int result = isAuthorize() ? 1 : 0;
		result = 31 * result + votes;
		
		return result;
	}
	
	@Override
	public String toString() {
		return "Tally{"
				+ "authorize='"
				+ authorize
				+'\''
				+ ", votes='"
				+ votes
				+'\''
				+ '}';
	}

}
