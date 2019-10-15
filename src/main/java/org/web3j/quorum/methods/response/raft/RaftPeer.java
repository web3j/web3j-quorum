package org.web3j.quorum.methods.response.raft;

public class RaftPeer {
	
	private String ip;
	private String nodeId;
	private String p2pPort;
	private String raftId;
	private String raftPort;
	
	public RaftPeer() {}
	
	public RaftPeer(String ip, String nodeId, String p2pPort, String raftId, 
			String raftPort) {
		this.setIp(ip);
		this.setNodeId(nodeId);
		this.setP2pPort(p2pPort);
		this.setRaftId(raftId);
		this.setRaftPort(raftPort);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getP2pPort() {
		return p2pPort;
	}

	public void setP2pPort(String p2pPort) {
		this.p2pPort = p2pPort;
	}

	public String getRaftId() {
		return raftId;
	}

	public void setRaftId(String raftId) {
		this.raftId = raftId;
	}

	public String getRaftPort() {
		return raftPort;
	}

	public void setRaftPort(String raftPort) {
		this.raftPort = raftPort;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof RaftPeer)) {
			return false;
		}
		
		RaftPeer that = (RaftPeer) o;
		
		if(getIp() != null ? !getIp().equals(that.getIp()) : that.getIp() != null) {
			return false;
		}
		
		if(getNodeId() != null ? !getNodeId().equals(that.getNodeId()) : that.getNodeId() != null) {
			return false;
		}
		
		if(getP2pPort() != null ? !getP2pPort().equals(that.getP2pPort()) : that.getP2pPort() != null) {
			return false;
		}
		
		if(getRaftId() != null ? !getRaftId().equals(that.getRaftId()) : that.getRaftId() != null) {
			return false;
		}
		
		return getRaftPort() != null ? !getRaftPort().equals(that.getRaftPort()) : that.getRaftPort() != null;
	}
	
	@Override
	public int hashCode() {
		int result = getIp() != null ? getIp().hashCode() : 0;
		result = 31 * result + (nodeId != null ? nodeId.hashCode() : 0);
		result = 31 * result + (p2pPort != null ? p2pPort.hashCode() : 0);
		result = 31 * result + (raftId != null ? raftId.hashCode() : 0);
		result = 31 * result + (raftPort != null ? raftPort.hashCode() : 0);
		
		return result;
	}
	
	@Override
	public String toString() {
		return "RaftPeer{"
				+ "ip='"
				+ ip
				+'\''
				+ ", nodeId='"
				+ nodeId
				+'\''
				+ ", p2pPort='"
				+ p2pPort
				+'\''
				+ ", raftId='"
				+ raftId
				+'\''
				+ ", raftPort='"
				+ raftPort
				+'\''
				+ '}';
	}

}
