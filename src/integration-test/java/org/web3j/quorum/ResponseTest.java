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
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.quorum.methods.response.*;
import org.web3j.quorum.methods.response.istanbul.IstanbulCandidates;
import org.web3j.quorum.methods.response.istanbul.IstanbulSnapshot;
import org.web3j.quorum.methods.response.istanbul.IstanbulValidators;
import org.web3j.quorum.methods.response.permissioning.ExecStatusInfo;
import org.web3j.quorum.methods.response.permissioning.OrgDetailsInfo;
import org.web3j.quorum.methods.response.permissioning.PermissionAccountList;
import org.web3j.quorum.methods.response.permissioning.PermissionNodeList;
import org.web3j.quorum.methods.response.permissioning.PermissionOrgList;
import org.web3j.quorum.methods.response.permissioning.PermissionRoleList;
import org.web3j.quorum.methods.response.raft.RaftCluster;
import org.web3j.quorum.methods.response.raft.RaftLeader;
import org.web3j.quorum.methods.response.raft.RaftPeerId;
import org.web3j.quorum.methods.response.raft.RaftRole;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ResponseTest extends ResponseTester {

    @Test
    public void testEthSendTransaction() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0x0d9e7e34fd4db216a3f66981a467d9d990954e6ed3128aff4ec51a50fa175663\"}");

        EthSendTransaction transaction = deserialiseResponse(EthSendTransaction.class);
        assertThat(
                transaction.getTransactionHash(),
                is("0x0d9e7e34fd4db216a3f66981a467d9d990954e6ed3128aff4ec51a50fa175663"));
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
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":[{\"ip\":\"127.0.0.1\", \"nodeId\":\"3d9ca5956b38557aba991e31cf510d4df641dce9cc26bfeb7de082f0c07abb6ede3a58410c8f249dabeecee4ad3979929ac4c7c496ad20b8cfdd061b7401b4f5\",\"p2pPort\":\"21003\", \"raftId\":\"4\", \"raftPort\":\"50404\"}]}");

        RaftCluster raftCluster = deserialiseResponse(RaftCluster.class);
        assertThat(
                raftCluster.getCluster().get().toString(),
                is(
                        "[{ip=127.0.0.1, nodeId=3d9ca5956b38557aba991e31cf510d4df641dce9cc26bfeb7de082f0c07abb6ede3a58410c8f249dabeecee4ad3979929ac4c7c496ad20b8cfdd061b7401b4f5, p2pPort=21003, raftId=4, raftPort=50404}]"));
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
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":{\"epoch\": \"30000\",\"hash\": \"0x0cea2fb02ca1e6e9f75d6d551766b3b4776ce5e644b0c78ed164cc63d6635dca\",\"number\": \"2\",\"policy\": \"0\",\"tally\": {},\"validators\": [\"0x6571d97f340c8495b661a823f2c2145ca47d63c2\", \"0x8157d4437104e3b8df4451a85f7b2438ef6699ff\"],\"votes\": []}}");

        IstanbulSnapshot snapshot = deserialiseResponse(IstanbulSnapshot.class);
        assertThat(
                snapshot.getSnapshot().get().toString(),
                is(
                        "Snapshot(epoch=30000, hash=0x0cea2fb02ca1e6e9f75d6d551766b3b4776ce5e644b0c78ed164cc63d6635dca, number=2, policy=0, tally=Tally(authorize=false, votes=0), validators=[0x6571d97f340c8495b661a823f2c2145ca47d63c2, 0x8157d4437104e3b8df4451a85f7b2438ef6699ff], votes=[])"));
    }

    @Test
    public void testIstanbulGetValidators() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":[\"0x6571d97f340c8495b661a823f2c2145ca47d63c2\", \"0x8157d4437104e3b8df4451a85f7b2438ef6699ff\"]}");

        IstanbulValidators validators = deserialiseResponse(IstanbulValidators.class);
        assertThat(
                validators.getValidators().toString(),
                is(
                        "[0x6571d97f340c8495b661a823f2c2145ca47d63c2, 0x8157d4437104e3b8df4451a85f7b2438ef6699ff]"));
    }

    @Test
    public void testIstanbulGetCandidates() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":{0x6571d97f340c8495b661a823f2c2145ca47d63c2: true, 0x8157d4437104e3b8df4451a85f7b2438ef6699ff: false}}");

        IstanbulCandidates validators = deserialiseResponse(IstanbulCandidates.class);
        assertThat(
                validators.getCandidates().toString(),
                is(
                        "{0x6571d97f340c8495b661a823f2c2145ca47d63c2=true, 0x8157d4437104e3b8df4451a85f7b2438ef6699ff=false}"));
    }

    @Test
    public void testPermissionGetOrgList() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":[{\"fullOrgId\": \"INITORG\",\"level\": \"1\",\"orgId\": \"INITORG\",\"parentOrgId\": \"\",\"status\": \"2\",\"subOrgList\": [],\"ultimateParent\": \"INITORG\"}]}");

        PermissionOrgList orgList = deserialiseResponse(PermissionOrgList.class);
        assertThat(
                orgList.getPermissionOrgList().toString(),
                is(
                        "[{fullOrgId=INITORG, level=1, orgId=INITORG, parentOrgId=, status=2, subOrgList=[], ultimateParent=INITORG}]"));
    }

    @Test
    public void testPermissionGetNodeList() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":[{\"orgId\": \"INITORG\",\"status\": \"2\",\"url\":\"enode://72c0572f7a2492cffb5efc3463ef350c68a0446402a123dacec9db5c378789205b525b3f5f623f7548379ab0e5957110bffcf43a6115e450890f97a9f65a681a@127.0.0.1:21000?discport=0\"},{\"orgId\": \"INITORG\",\"status\": \"2\",\"url\":\"enode://7a1e3b5c6ad614086a4e5fb55b6fe0a7cf7a7ac92ac3a60e6033de29df14148e7a6a7b4461eb70639df9aa379bd77487937bea0a8da862142b12d326c7285742@127.0.0.1:21001?discport=0\"}]}");

        PermissionNodeList nodeList = deserialiseResponse(PermissionNodeList.class);
        assertThat(
                nodeList.getPermissionNodeList().toString(),
                is(
                        "[{orgId=INITORG, status=2, url=enode://72c0572f7a2492cffb5efc3463ef350c68a0446402a123dacec9db5c378789205b525b3f5f623f7548379ab0e5957110bffcf43a6115e450890f97a9f65a681a@127.0.0.1:21000?discport=0}, {orgId=INITORG, status=2, url=enode://7a1e3b5c6ad614086a4e5fb55b6fe0a7cf7a7ac92ac3a60e6033de29df14148e7a6a7b4461eb70639df9aa379bd77487937bea0a8da862142b12d326c7285742@127.0.0.1:21001?discport=0}]"));
    }

    @Test
    public void testPermissionGetRoleList() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":[{\"access\":\"3\",\"active\":\"true\",\"isAdmin\":\"true\",\"isVoter\":\"true\",\"orgId\":\"INITORG\",\"roleId\":\"NWADMIN\"}]}");

        PermissionRoleList roleList = deserialiseResponse(PermissionRoleList.class);
        assertThat(
                roleList.getPermissionRoleList().toString(),
                is(
                        "[{access=3, active=true, isAdmin=true, isVoter=true, orgId=INITORG, roleId=NWADMIN}]"));
    }

    @Test
    public void testPermissionGetAccountList() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":[{\"acctId\":\"0xed9d02e382b34818e88b88a309c7fe71e65f419d\",\"isOrgAdmin\":\"true\",\"orgId\":\"INITORG\",\"roleId\":\"NWADMIN\",\"status\":\"2\"},{\"acctId\":\"0xca843569e3427144cead5e4d5999a3d0ccf92b8e\",\"isOrgAdmin\":\"true\",\"orgId\":\"INITORG\",\"roleId\":\"NWADMIN\",\"status\":\"2\"}]}");

        PermissionAccountList accountList = deserialiseResponse(PermissionAccountList.class);
        assertThat(
                accountList.getPermissionAccountList().toString(),
                is(
                        "[{acctId=0xed9d02e382b34818e88b88a309c7fe71e65f419d, isOrgAdmin=true, orgId=INITORG, roleId=NWADMIN, status=2}, {acctId=0xca843569e3427144cead5e4d5999a3d0ccf92b8e, isOrgAdmin=true, orgId=INITORG, roleId=NWADMIN, status=2}]"));
    }

    @Test
    public void testPermissionExecStatusInfoResponse() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"Action completed successfully\"}");

        ExecStatusInfo response = deserialiseResponse(ExecStatusInfo.class);
        assertThat(response.getExecStatus(), is("Action completed successfully"));
    }

    @Test
    public void testPermissionGetOrgDetails() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":{\"acctList\":[{\"acctId\":\"0xed9d02e382b34818e88b88a309c7fe71e65f419d\",\"isOrgAdmin\":\"true\",\"orgId\":\"INITORG\",\"roleId\":\"NWADMIN\",\"status\":\"2\"}],\"nodeList\":[{\"orgId\":\"INITORG\",\"status\":\"2\",\"enodeId\":\"enode://72c0572f7a2492cffb5efc3463ef350c68a0446402a123dacec9db5c378789205b525b3f5f623f7548379ab0e5957110bffcf43a6115e450890f97a9f65a681a@127.0.0.1:21000?discport=0\"},{\"orgId\":\"INITORG\",\"status\":\"2\",\"enodeId\":\"enode://7a1e3b5c6ad614086a4e5fb55b6fe0a7cf7a7ac92ac3a60e6033de29df14148e7a6a7b4461eb70639df9aa379bd77487937bea0a8da862142b12d326c7285742@127.0.0.1:21001?discport=0\"}],\"roleList\":[{\"access\":\"3\",\"active\":\"true\",\"isAdmin\":\"true\",\"isVoter\":\"true\",\"orgId\":\"INITORG\",\"roleId\":\"NWADMIN\"}],\"subOrgList\":[]}}");

        OrgDetailsInfo orgDetails = deserialiseResponse(OrgDetailsInfo.class);
        assertThat(
                orgDetails.getOrgDetails().toString(),
                is(
                        "OrgDetails(roleList=[PermissionRoleInfo(isVoter=true, active=true, orgId=INITORG, roleId=NWADMIN, access=3, isAdmin=true)], acctList=[PermissionAccountInfo(acctId=0xed9d02e382b34818e88b88a309c7fe71e65f419d, isOrgAdmin=true, orgId=INITORG, roleId=NWADMIN, status=2)], nodeList=[PermissionNodeInfo(orgId=INITORG, enodeId=enode://72c0572f7a2492cffb5efc3463ef350c68a0446402a123dacec9db5c378789205b525b3f5f623f7548379ab0e5957110bffcf43a6115e450890f97a9f65a681a@127.0.0.1:21000?discport=0, status=2), PermissionNodeInfo(orgId=INITORG, enodeId=enode://7a1e3b5c6ad614086a4e5fb55b6fe0a7cf7a7ac92ac3a60e6033de29df14148e7a6a7b4461eb70639df9aa379bd77487937bea0a8da862142b12d326c7285742@127.0.0.1:21001?discport=0, status=2)], subOrgList=[])"));
    }
}
