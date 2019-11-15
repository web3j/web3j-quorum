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

import org.web3j.protocol.ResponseTester;
import org.web3j.quorum.methods.response.permissioning.ExecStatusInfo;
import org.web3j.quorum.methods.response.permissioning.OrgDetailsInfo;
import org.web3j.quorum.methods.response.permissioning.PermissionAccountList;
import org.web3j.quorum.methods.response.permissioning.PermissionNodeList;
import org.web3j.quorum.methods.response.permissioning.PermissionOrgList;
import org.web3j.quorum.methods.response.permissioning.PermissionRoleList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PermissionResponseTest extends ResponseTester {

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
