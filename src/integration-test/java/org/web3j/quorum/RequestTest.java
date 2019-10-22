/*
 * Copyright 2019 Web3 Labs LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j.quorum;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;

import org.web3j.protocol.RequestTester;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.methods.request.PrivateTransaction;

public class RequestTest extends RequestTester {

    private Quorum web3j;

    @Override
    protected void initWeb3Client(HttpService httpService) {
        web3j = Quorum.build(httpService);
    }

    @Test
    public void testSendTransaction() throws Exception {
        web3j.ethSendTransaction(
                        new PrivateTransaction(
                                "FROM",
                                BigInteger.ONE,
                                BigInteger.TEN,
                                "TO",
                                BigInteger.TEN,
                                "DATA",
                                "privateFrom",
                                Arrays.asList("privateFor1", "privateFor2")))
                .send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"eth_sendTransaction\",\"params\":[{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"]}],\"id\":1}");
    }
    
    @Test
    public void testSendRawTransaction() throws Exception {
    	String signedTransactionData = "SignedTxData";
    	web3j.ethSendRawTransaction(signedTransactionData).send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"eth_sendRawTransaction\",\"params\":[\"SignedTxData\"],\"id\":1}");
    }
    
    @Test
    public void testSendRawPrivateTransaction() throws Exception {
    	String signedTransactionData = "SignedTxData";
    	web3j.ethSendRawPrivateTransaction(signedTransactionData, Arrays.asList("privateFor1", "privateFor2")).send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"eth_sendRawPrivateTransaction\",\"params\":[\"SignedTxData\",{\"privateFor\":[\"privateFor1\",\"privateFor2\"]}],\"id\":1}");
    }

    @Test
    public void testGetPrivateTransaction() throws Exception {
        web3j.quorumGetPrivatePayload("0x").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"eth_getQuorumPayload\",\"params\":[\"0x\"],\"id\":1}");
    }
    
    @Test
    public void testRaftRole() throws Exception {
    	web3j.raftGetRole().send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"raft_role\",\"params\":[],\"id\":1}");
    }
    
    @Test
    public void testRaftLeader() throws Exception {
    	web3j.raftGetLeader().send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"raft_leader\",\"params\":[],\"id\":1}");
    }
    
    @Test
    public void testRaftCluster() throws Exception {
    	web3j.raftGetCluster().send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"raft_cluster\",\"params\":[],\"id\":1}");
    }
    
    @Test
    public void testRaftRemovePeer() throws Exception {
    	web3j.raftRemovePeer(1).send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"raft_removePeer\",\"params\":[1],\"id\":1}");
    }
    
    @Test
    public void testRaftAddPeer() throws Exception {
    	web3j.raftAddPeer("enode").send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"raft_addPeer\",\"params\":[\"enode\"],\"id\":1}");
    }
    
    @Test
    public void testIstanbulGetSnapshot() throws Exception {
    	web3j.istanbulGetSnapshot("latest").send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_getSnapshot\",\"params\":[\"latest\"],\"id\":1}");
    }
    
    @Test
    public void testIstanbulGetSnapshotAtHash() throws Exception {
    	web3j.istanbulGetSnapshotAtHash("blockHash").send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_getSnapshotAtHash\",\"params\":[\"blockHash\"],\"id\":1}");
    }
    
    @Test
    public void testIstanbulGetValidators() throws Exception {
    	web3j.istanbulGetValidators("latest").send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_getValidators\",\"params\":[\"latest\"],\"id\":1}");
    }
    
    @Test
    public void testIstanbulGetValidatorsAtHash() throws Exception {
    	web3j.istanbulGetValidatorsAtHash("blockHash").send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_getValidatorsAtHash\",\"params\":[\"blockHash\"],\"id\":1}");
    }

    @Test
    public void testIstanbulPropose() throws Exception {
    	web3j.istanbulPropose("address", true).send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_propose\",\"params\":[\"address\",true],\"id\":1}");
    }
    
    @Test
    public void testIstanbulDiscard() throws Exception {
    	web3j.istanbulDiscard("address").send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_discard\",\"params\":[\"address\"],\"id\":1}");
    }
    
    @Test
    public void testIstanbulCandidates() throws Exception {
    	web3j.istanbulCandidates().send();
    	
    	verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_candidates\",\"params\":[],\"id\":1}");
    }
}
