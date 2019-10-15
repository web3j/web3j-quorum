package org.web3j.quorum;


import org.junit.Test;

import org.web3j.protocol.ResponseTester;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.quorum.methods.response.*;
import org.web3j.quorum.methods.response.istanbul.IstanbulCandidates;
import org.web3j.quorum.methods.response.istanbul.IstanbulSnapshot;
import org.web3j.quorum.methods.response.istanbul.IstanbulValidators;
import org.web3j.quorum.methods.response.raft.RaftCluster;
import org.web3j.quorum.methods.response.raft.RaftLeader;
import org.web3j.quorum.methods.response.raft.RaftPeerId;
import org.web3j.quorum.methods.response.raft.RaftRole;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;

public class ResponseTest extends ResponseTester {

    @Test
    public void testEthSendTransaction() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0x0d9e7e34fd4db216a3f66981a467d9d990954e6ed3128aff4ec51a50fa175663\"}");

        EthSendTransaction transaction = deserialiseResponse(EthSendTransaction.class);
        assertThat(transaction.getTransactionHash(), is("0x0d9e7e34fd4db216a3f66981a467d9d990954e6ed3128aff4ec51a50fa175663"));
    }

    @Test
    public void testPrivatePayload() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0x\"}");

        PrivatePayload privatePayload = deserialiseResponse(PrivatePayload.class);
        assertThat(privatePayload.getPrivatePayload(), is("0x"));
    }
    
    @Test
    public void testRaftRole() {
    	buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"verifier\"}");
    	
    	RaftRole raftRole = deserialiseResponse(RaftRole.class);
    	assertThat(raftRole.getRole(), is("verifier"));
    }
    
    @Test
    public void testRaftLeader() {
    	buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0ba6b9f606a43a95edc6247cdb1c1e105145817be7bcafd6b2c0ba15d58145f0dc1a194f70ba73cd6f4cdd6864edc7687f311254c7555cc32e4d45aeb1b80416\"}");
    	
    	RaftLeader raftLeader = deserialiseResponse(RaftLeader.class);
    	assertThat(raftLeader.getLeader(), is("0ba6b9f606a43a95edc6247cdb1c1e105145817be7bcafd6b2c0ba15d58145f0dc1a194f70ba73cd6f4cdd6864edc7687f311254c7555cc32e4d45aeb1b80416"));
    }
    
    @Test
    public void testRaftCluster() { 
    	buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":[{\"ip\":\"127.0.0.1\", \"nodeId\":\"3d9ca5956b38557aba991e31cf510d4df641dce9cc26bfeb7de082f0c07abb6ede3a58410c8f249dabeecee4ad3979929ac4c7c496ad20b8cfdd061b7401b4f5\",\"p2pPort\":\"21003\", \"raftId\":\"4\", \"raftPort\":\"50404\"}]}");
    	
    	RaftCluster raftCluster = deserialiseResponse(RaftCluster.class);
    	assertThat(raftCluster.getCluster().get().toString(), is("[RaftPeer{ip='127.0.0.1', nodeId='3d9ca5956b38557aba991e31cf510d4df641dce9cc26bfeb7de082f0c07abb6ede3a58410c8f249dabeecee4ad3979929ac4c7c496ad20b8cfdd061b7401b4f5', p2pPort='21003', raftId='4', raftPort='50404'}]"));
    }
    
    @Test
    public void testRaftRemovePeer() {
    	buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"null\"}");
    	
    	ConsensusNoResponse raftPeerId = deserialiseResponse(ConsensusNoResponse.class);
    	assertThat(raftPeerId.getNoResponse(), is("success"));
    }
    
    @Test
    public void testRaftAddPeer() {
    	buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"1\"}");
    	
    	RaftPeerId raftPeerId = deserialiseResponse(RaftPeerId.class);
    	assertThat(raftPeerId.getAddedPeer(), is(BigInteger.ONE));
    }
    
    @Test
    public void testIstanbulGetSnapshot() {
    	buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":{\"epoch\": \"30000\",\"hash\": \"0x0cea2fb02ca1e6e9f75d6d551766b3b4776ce5e644b0c78ed164cc63d6635dca\",\"number\": \"2\",\"policy\": \"0\",\"tally\": {},\"validators\": [\"0x6571d97f340c8495b661a823f2c2145ca47d63c2\", \"0x8157d4437104e3b8df4451a85f7b2438ef6699ff\"],\"votes\": []}}");

    	IstanbulSnapshot snapshot = deserialiseResponse(IstanbulSnapshot.class);
    	assertThat(snapshot.getSnapshot().get().toString(), is("Snapshot{epoch='30000', hash='0x0cea2fb02ca1e6e9f75d6d551766b3b4776ce5e644b0c78ed164cc63d6635dca', number='2', policy='0', tally=Tally{authorize='false', votes='0'}, validators=[0x6571d97f340c8495b661a823f2c2145ca47d63c2, 0x8157d4437104e3b8df4451a85f7b2438ef6699ff], votes=[]}"));
    }
    
    @Test
    public void testIstanbulGetValidators() {
    	buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":[\"0x6571d97f340c8495b661a823f2c2145ca47d63c2\", \"0x8157d4437104e3b8df4451a85f7b2438ef6699ff\"]}");   	
    	
    	IstanbulValidators validators = deserialiseResponse(IstanbulValidators.class);
    	assertThat(validators.getValidators().toString(), is("[0x6571d97f340c8495b661a823f2c2145ca47d63c2, 0x8157d4437104e3b8df4451a85f7b2438ef6699ff]")); 
    }
    
    @Test
    public void testIstanbulGetCandidates() {
    	buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":{0x6571d97f340c8495b661a823f2c2145ca47d63c2: true, 0x8157d4437104e3b8df4451a85f7b2438ef6699ff: false}}");   	
    	
    	IstanbulCandidates validators = deserialiseResponse(IstanbulCandidates.class);
    	assertThat(validators.getCandidates().toString(), is("{0x6571d97f340c8495b661a823f2c2145ca47d63c2=true, 0x8157d4437104e3b8df4451a85f7b2438ef6699ff=false}")); 
    }
}
