package org.web3j.quorum;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.quorum.methods.request.*;
import org.web3j.quorum.methods.response.*;
import org.web3j.quorum.methods.response.istanbul.IstanbulCandidates;
import org.web3j.quorum.methods.response.istanbul.IstanbulSnapshot;
import org.web3j.quorum.methods.response.istanbul.IstanbulValidators;
import org.web3j.quorum.methods.response.raft.RaftCluster;
import org.web3j.quorum.methods.response.raft.RaftLeader;
import org.web3j.quorum.methods.response.raft.RaftPeerId;
import org.web3j.quorum.methods.response.raft.RaftRole;

import java.util.List;

/**
 * JSON-RPC Request object building factory for Quorum.
 */
public interface Quorum extends Web3j {
    static Quorum build(Web3jService web3jService) {
        return new JsonRpc2_0Quorum(web3jService);
    }

    Request<?, EthSendTransaction> ethSendTransaction(PrivateTransaction transaction);

    Request<?, PrivatePayload> quorumGetPrivatePayload(String hexDigest);

    Request<?, EthSendTransaction> ethSendRawTransaction(String signedTransactionData);

    Request<?, EthSendTransaction> ethSendRawPrivateTransaction(String signedTransactionData, List<String> privateFor);


    //raft consensus
    
    Request<?, RaftLeader> raftGetLeader();
    
    Request<?, RaftRole> raftGetRole();
    
    Request<?, RaftPeerId> raftAddPeer(String enode);
    
    Request<?, ConsensusNoResponse> raftRemovePeer(int peerId);
    
    Request<?, RaftCluster> raftGetCluster();
    
    //istanbul consensus
    
    Request<?, IstanbulSnapshot> istanbulGetSnapshot(String blockNum);
    
    Request<?, IstanbulSnapshot> istanbulGetSnapshotAtHash(String blockHash);
    
    Request<?, IstanbulValidators> istanbulGetValidators(String blockNum);
    
    Request<?, IstanbulValidators> istanbulGetValidatorsAtHash(String blockHash);
    
    Request<?, ConsensusNoResponse> istanbulPropose(String address, boolean auth);
    
    Request<?, ConsensusNoResponse> istanbulDiscard(String address);
    
    Request<?, IstanbulCandidates> istanbulCandidates();
    
}
