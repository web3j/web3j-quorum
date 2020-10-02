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
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.web3j.protocol.RequestTester;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.methods.request.PrivateTransaction;

public class PermissionRequestTest extends RequestTester {

    private Quorum web3j;

    @Override
    protected void initWeb3Client(HttpService httpService) {
        web3j = Quorum.build(httpService);
    }

    @Test
    public void testPermissionGetOrgList() throws Exception {
        web3j.quorumPermissionGetOrgList().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_orgList\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testPermissionGetNodeList() throws Exception {
        web3j.quorumPermissionGetNodeList().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_nodeList\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testPermissionGetRoleList() throws Exception {
        web3j.quorumPermissionGetRoleList().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_roleList\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testPermissionGetAccountList() throws Exception {
        web3j.quorumPermissionGetAccountList().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_acctList\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testPermissionAddOrg() throws Exception {
        web3j.quorumPermissionAddOrg(
                        "orgId",
                        "url",
                        "address",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_addOrg\",\"params\":[\"orgId\",\"url\",\"address\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionApproveOrg() throws Exception {
        web3j.quorumPermissionApproveOrg(
                        "orgId",
                        "url",
                        "address",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_approveOrg\",\"params\":[\"orgId\",\"url\",\"address\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionAddSubOrg() throws Exception {
        web3j.quorumPermissionAddSubOrg(
                        "pOrgId",
                        "orgId",
                        "url",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_addSubOrg\",\"params\":[\"pOrgId\",\"orgId\",\"url\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionUpdateOrgStatus() throws Exception {
        web3j.quorumPermissionUpdateOrgStatus(
                        "orgId",
                        1,
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_updateOrgStatus\",\"params\":[\"orgId\",1,{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionApproveOrgStatus() throws Exception {
        web3j.quorumPermissionApproveOrgStatus(
                        "orgId",
                        1,
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_approveOrgStatus\",\"params\":[\"orgId\",1,{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionAddNode() throws Exception {
        web3j.quorumPermissionAddNode(
                        "orgId",
                        "url",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_addNode\",\"params\":[\"orgId\",\"url\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionUpdateNodeStatus() throws Exception {
        web3j.quorumPermissionUpdateNodeStatus(
                        "orgId",
                        "url",
                        1,
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_updateNodeStatus\",\"params\":[\"orgId\",\"url\",1,{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionAssignAdminRole() throws Exception {
        web3j.quorumPermissionAssignAdminRole(
                        "orgId",
                        "address",
                        "roleid",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_assignAdminRole\",\"params\":[\"orgId\",\"address\",\"roleid\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionApproveAdminRole() throws Exception {
        web3j.quorumPermissionApproveAdminRole(
                        "orgId",
                        "address",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_approveAdminRole\",\"params\":[\"orgId\",\"address\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionAddNewRole() throws Exception {
        web3j.quorumPermissionAddNewRole(
                        "orgId",
                        "roleId",
                        1,
                        true,
                        true,
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_addNewRole\",\"params\":[\"orgId\",\"roleId\",1,true,true,{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionRemoveRole() throws Exception {
        web3j.quorumPermissionRemoveRole(
                        "orgId",
                        "roleId",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_removeRole\",\"params\":[\"orgId\",\"roleId\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionAddAccountToOrg() throws Exception {
        web3j.quorumPermissionAddAccountToOrg(
                        "address",
                        "orgId",
                        "roleId",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_addAccountToOrg\",\"params\":[\"address\",\"orgId\",\"roleId\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionChangeAccountRole() throws Exception {
        web3j.quorumPermissionChangeAccountRole(
                        "address",
                        "orgId",
                        "roleId",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_changeAccountRole\",\"params\":[\"address\",\"orgId\",\"roleId\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionUpdateAccountStatus() throws Exception {
        web3j.quorumPermissionUpdateAccountStatus(
                        "orgId",
                        "address",
                        1,
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_updateAccountStatus\",\"params\":[\"orgId\",\"address\",1,{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionGetOrgDetails() throws Exception {
        web3j.quorumPermissionGetOrgDetails("orgId").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_getOrgDetails\",\"params\":[\"orgId\"],\"id\":1}");
    }

    @Test
    public void testPermissionRecoverBlackListedNode() throws Exception {
        web3j.quorumPermissionRecoverBlackListedNode(
                        "orgId",
                        "enodeId",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_recoverBlackListedNode\",\"params\":[\"orgId\",\"enodeId\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionApproveBlackListedNodeRecover() throws Exception {
        web3j.quorumPermissionApproveBlackListedNodeRecovery(
                        "orgId",
                        "enodeId",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_approveBlackListedNodeRecovery\",\"params\":[\"orgId\",\"enodeId\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":1}");
    }

    @Test
    public void testPermissionRecoverBlackListedAccount() throws Exception {
        web3j.quorumPermissionRecoverBlackListedAccount(
                        "orgId",
                        "address",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_recoverBlackListedAccount\",\"params\":[\"orgId\",\"address\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"]}],\"id\":1}");
    }

    @Test
    public void testPermissionApproveBlackListedAccountRecover() throws Exception {
        web3j.quorumPermissionApproveBlackListedAccountRecovery(
                        "orgId",
                        "address",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumPermission_approveBlackListedAccountRecovery\",\"params\":[\"orgId\",\"address\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"]}],\"id\":1}");
    }
}
