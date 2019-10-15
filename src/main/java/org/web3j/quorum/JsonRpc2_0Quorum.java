package org.web3j.quorum;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.quorum.methods.request.PrivateRawTransaction;
import org.web3j.quorum.methods.request.PrivateTransaction;
import org.web3j.quorum.methods.response.ConsensusNoResponse;
import org.web3j.quorum.methods.response.PrivatePayload;
import org.web3j.quorum.methods.response.istanbul.IstanbulCandidates;
import org.web3j.quorum.methods.response.istanbul.IstanbulSnapshot;
import org.web3j.quorum.methods.response.istanbul.IstanbulValidators;
import org.web3j.quorum.methods.response.raft.RaftCluster;
import org.web3j.quorum.methods.response.raft.RaftLeader;
import org.web3j.quorum.methods.response.raft.RaftPeerId;
import org.web3j.quorum.methods.response.raft.RaftRole;

/**
 * Quorum JSON-RPC API implementation.
 */
public class JsonRpc2_0Quorum extends JsonRpc2_0Web3j implements Quorum {

    public JsonRpc2_0Quorum(Web3jService web3jService) {
        super(web3jService);
    }

    public JsonRpc2_0Quorum(
            Web3jService web3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        super(web3jService, pollingInterval, scheduledExecutorService);
    }

    @Override
    public Request<?, EthSendTransaction> ethSendRawPrivateTransaction(
            String signedTransactionData, List<String> privateFor) {
        PrivateRawTransaction transaction = new PrivateRawTransaction(privateFor);
        return new Request<>(
                "eth_sendRawPrivateTransaction",
                Arrays.asList(signedTransactionData, transaction),
                web3jService,
                EthSendTransaction.class);
    }

    @Override
    public Request<?, EthSendTransaction> ethSendRawTransaction(
            String signedTransactionData) {
        return new Request<>(
                "eth_sendRawTransaction",
                Collections.singletonList(signedTransactionData),
                web3jService,
                EthSendTransaction.class);
    }

    @Override
    public Request<?, EthSendTransaction> ethSendTransaction(
            PrivateTransaction transaction) {
        return new Request<>(
                "eth_sendTransaction",
                Collections.singletonList(transaction),
                web3jService,
                EthSendTransaction.class);
    }

    @Override
    public Request<?, PrivatePayload> quorumGetPrivatePayload(String hexDigest) {
        return new Request<>(
                "eth_getQuorumPayload",
                Collections.singletonList(hexDigest),
                web3jService,
                PrivatePayload.class);
    }
    
    //raft consensus
    
	@Override
	public Request<?, RaftLeader> raftGetLeader() {
		return new Request<>(
				"raft_leader",
				Collections.emptyList(),
				web3jService,
				RaftLeader.class);
	}

	@Override
	public Request<?, RaftRole> raftGetRole() {
		return new Request<>(
				"raft_role",
				Collections.emptyList(),
				web3jService,
				RaftRole.class);
	}

	@Override
	public Request<?, RaftPeerId> raftAddPeer(String enode) {
		return new Request<>(
				"raft_addPeer",
				Collections.singletonList(enode),
				web3jService,
				RaftPeerId.class);
	}

	@Override
	public Request<?, ConsensusNoResponse> raftRemovePeer(int peerId) {
		return new Request<>(
				"raft_removePeer",
				Collections.singletonList(peerId),
				web3jService,
				ConsensusNoResponse.class);
	}

	@Override
	public Request<?, RaftCluster> raftGetCluster() {
		return new Request<>(
				"raft_cluster",
				Collections.emptyList(),
				web3jService,
				RaftCluster.class);
	}

	//istanbul consensus
	
	@Override
	public Request<?, IstanbulSnapshot> istanbulGetSnapshot(String blockNum) {
		return new Request<>(
				"istanbul_getSnapshot",
				Collections.singletonList(blockNum),
				web3jService,
				IstanbulSnapshot.class);
	}

	@Override
	public Request<?, IstanbulSnapshot> istanbulGetSnapshotAtHash(String blockHash) {
		return new Request<>(
				"istanbul_getSnapshotAtHash",
				Collections.singletonList(blockHash),
				web3jService,
				IstanbulSnapshot.class);
	}

	@Override
	public Request<?, IstanbulValidators> istanbulGetValidators(String blockNum) {
		return new Request<>(
				"istanbul_getValidators",
				Collections.singletonList(blockNum),
				web3jService,
				IstanbulValidators.class);
	}

	@Override
	public Request<?, IstanbulValidators> istanbulGetValidatorsAtHash(String blockHash) {
		return new Request<>(
				"istanbul_getValidatorsAtHash",
				Collections.singletonList(blockHash),
				web3jService,
				IstanbulValidators.class);
	}

	@Override
	public Request<?, ConsensusNoResponse> istanbulPropose(String address, boolean auth) {
		return new Request<>(
				"istanbul_propose",
				Arrays.asList(address, auth),
				web3jService,
				ConsensusNoResponse.class);
	}

	@Override
	public Request<?, ConsensusNoResponse> istanbulDiscard(String address) {
		return new Request<>(
				"istanbul_discard",
				Collections.singletonList(address),
				web3jService,
				ConsensusNoResponse.class);
	}

	@Override
	public Request<?, IstanbulCandidates> istanbulCandidates() {
		return new Request<>(
				"istanbul_candidates",
				Collections.emptyList(),
				web3jService,
				IstanbulCandidates.class);
	}
}
