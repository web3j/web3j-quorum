/*
 * Copyright 2019 Web3 Labs Ltd.
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

import org.junit.jupiter.api.Test;

import org.web3j.protocol.ResponseTester;
import org.web3j.quorum.methods.response.ConsensusNoResponse;
import org.web3j.quorum.methods.response.raft.RaftCluster;
import org.web3j.quorum.methods.response.raft.RaftLeader;
import org.web3j.quorum.methods.response.raft.RaftPeerId;
import org.web3j.quorum.methods.response.raft.RaftPromote;
import org.web3j.quorum.methods.response.raft.RaftRole;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RaftResponseTest extends ResponseTester {

    @Test
    public void testRaftRole() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"verifier\"}");

        RaftRole raftRole = deserialiseResponse(RaftRole.class);
        assertThat(raftRole.getRole(), is("verifier"));
    }

    @Test
    public void testRaftLeader() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0ba6b9f606a43a95edc6247cdb1c1e105145817be7bcafd6b2c0ba15d58145f0dc1a194f70ba73cd6f4cdd6864edc7687f311254c7555cc32e4d45aeb1b80416\"}");

        RaftLeader raftLeader = deserialiseResponse(RaftLeader.class);
        assertThat(
                raftLeader.getLeader(),
                is(
                        "0ba6b9f606a43a95edc6247cdb1c1e105145817be7bcafd6b2c0ba15d58145f0dc1a194f70ba73cd6f4cdd6864edc7687f311254c7555cc32e4d45aeb1b80416"));
    }

    @Test
    public void testRaftCluster() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":[{\"hostname\":\"127.0.0.1\", \"nodeActive\":\"true\", \"nodeId\":\"3d9ca5956b38557aba991e31cf510d4df641dce9cc26bfeb7de082f0c07abb6ede3a58410c8f249dabeecee4ad3979929ac4c7c496ad20b8cfdd061b7401b4f5\",\"p2pPort\":\"21003\", \"raftId\":\"4\", \"raftPort\":\"50404\", \"role\":\"verifier\"}]}");

        RaftCluster raftCluster = deserialiseResponse(RaftCluster.class);
        assertThat(
                raftCluster.getCluster().get().toString(),
                is(
                        "[RaftPeer(hostname=127.0.0.1, nodeId=3d9ca5956b38557aba991e31cf510d4df641dce9cc26bfeb7de082f0c07abb6ede3a58410c8f249dabeecee4ad3979929ac4c7c496ad20b8cfdd061b7401b4f5, nodeActive=true, p2pPort=21003, raftId=4, raftPort=50404, role=verifier)]"));
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
    public void testRaftPromoteToPeer() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"true\"}");

        RaftPromote raftPromote = deserialiseResponse(RaftPromote.class);
        assertThat(raftPromote.getPromotionStatus(), is(true));
    }
}
