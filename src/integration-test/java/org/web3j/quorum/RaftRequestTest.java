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

import org.junit.jupiter.api.Test;

import org.web3j.protocol.RequestTester;
import org.web3j.protocol.http.HttpService;

public class RaftRequestTest extends RequestTester {

    private Quorum web3j;

    @Override
    protected void initWeb3Client(HttpService httpService) {
        web3j = Quorum.build(httpService);
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

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"raft_removePeer\",\"params\":[1],\"id\":1}");
    }

    @Test
    public void testRaftAddPeer() throws Exception {
        web3j.raftAddPeer("enode").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"raft_addPeer\",\"params\":[\"enode\"],\"id\":1}");
    }
}
