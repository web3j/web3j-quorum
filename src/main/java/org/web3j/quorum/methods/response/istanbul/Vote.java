package org.web3j.quorum.methods.response.istanbul;

public class Vote {
	
	private String validator;
	private int block;
	private String address;
	private boolean authorize;
	
	public Vote() {}
	
	public Vote(String validator, int block, String address, boolean authorize) {
		this.setValidator(validator);
		this.setBlock(block);
		this.setAddress(address);
		this.setAuthorize(authorize);
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isAuthorize() {
		return authorize;
	}

	public void setAuthorize(boolean authorize) {
		this.authorize = authorize;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Vote)) {
			return false;
		}
		
		Vote that = (Vote) o;
		
		if(getValidator() != null ? !getValidator().equals(that.getValidator()) : that.getValidator() != null) {
			return false;
		}
		
		if(getBlock() != that.getBlock()) {
			return false;
		}
		
		if(getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) {
			return false;
		}
		
		return isAuthorize() != that.isAuthorize();
	}
	
	@Override
	public int hashCode() {
		int result = getValidator() != null ? getValidator().hashCode() : 0;
		result = 31 * result + block;
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (authorize ? 1 : 0);
		
		return result;
	}
	
	@Override
	public String toString() {
		return "Vote{"
				+ "validator='"
				+ validator
				+'\''
				+ ", block='"
				+ block
				+'\''
				+ ", address='"
				+ address
				+'\''
				+ ", authorize='"
				+ authorize
				+ '}';
	}


}
