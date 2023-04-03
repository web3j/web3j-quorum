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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.quorum.methods.request.PrivateRawTransaction;
import org.web3j.quorum.methods.request.PrivateTransaction;
import org.web3j.quorum.methods.response.*;
import org.web3j.quorum.methods.response.extension.*;
import org.web3j.quorum.methods.response.istanbul.IstanbulBlockSigners;
import org.web3j.quorum.methods.response.istanbul.IstanbulCandidates;
import org.web3j.quorum.methods.response.istanbul.IstanbulNodeAddress;
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
import org.web3j.quorum.methods.response.raft.RaftPromote;
import org.web3j.quorum.methods.response.raft.RaftRole;

/** Quorum JSON-RPC API implementation. */
public class JsonRpc2_0Quorum extends JsonRpc2_0Web3j implements Quorum {

    public JsonRpc2_0Quorum(Web3jService web3jService) {
        super(web3jService);
    }

    public JsonRpc2_0Quorum(
            Web3jService web3jService,
            long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        super(web3jService, pollingInterval, scheduledExecutorService);
    }

    @Override
    public Request<?, EthSendTransaction> ethSendRawPrivateTransaction(
            String signedTransactionData,
            List<String> privateFor,
            PrivacyFlag privacyFlag,
            List<String> mandatoryFor) {
        PrivateRawTransaction transaction =
                new PrivateRawTransaction(privateFor, privacyFlag, mandatoryFor);
        return new Request<>(
                "eth_sendRawPrivateTransaction",
                Arrays.asList(signedTransactionData, transaction),
                web3jService,
                EthSendTransaction.class);
    }

    @Override
    public Request<?, EthSendTransaction> ethSendRawPrivateTransaction(
            String signedTransactionData, List<String> privateFor, PrivacyFlag privacyFlag) {
        PrivateRawTransaction transaction = new PrivateRawTransaction(privateFor, privacyFlag);
        return new Request<>(
                "eth_sendRawPrivateTransaction",
                Arrays.asList(signedTransactionData, transaction),
                web3jService,
                EthSendTransaction.class);
    }

    @Override
    public Request<?, EthSendTransaction> ethSendRawTransaction(String signedTransactionData) {
        return new Request<>(
                "eth_sendRawTransaction",
                Collections.singletonList(signedTransactionData),
                web3jService,
                EthSendTransaction.class);
    }

    @Override
    public Request<?, EthSendTransaction> ethSendTransaction(PrivateTransaction transaction) {
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

    @Override
    public Request<?, ContractPrivacyMetadataInfo> quorumGetContractPrivacyMetadata(
            String hexDigest) {
        return new Request<>(
                "eth_getContractPrivacyMetadata",
                Collections.singletonList(hexDigest),
                web3jService,
                ContractPrivacyMetadataInfo.class);
    }

    @Override
    public Request<?, EthSendTransaction> ethSendTransactionAsync(PrivateTransaction transaction) {
        return new Request<>(
                "eth_sendTransactionAsync",
                Collections.singletonList(transaction),
                web3jService,
                EthSendTransaction.class);
    }

    @Override
    public Request<?, EthGetQuorumTransactionReceipt> ethGetQuorumTransactionReceipt(
            String transactionHash) {

        return new Request<>(
                "eth_getTransactionReceipt",
                Arrays.asList(transactionHash),
                this.web3jService,
                EthGetQuorumTransactionReceipt.class);
    }

    // privacy marker transactions

    @Override
    public Request<?, EthSendTransaction> ethDistributePrivateTransaction(
            String signedTransactionData,
            List<String> privateFor,
            PrivacyFlag privacyFlag,
            List<String> mandatoryFor) {
        PrivateRawTransaction transaction =
                new PrivateRawTransaction(privateFor, privacyFlag, mandatoryFor);
        return new Request<>(
                "eth_distributePrivateTransaction",
                Arrays.asList(signedTransactionData, transaction),
                web3jService,
                EthSendTransaction.class);
    }

    @Override
    public Request<?, EthSendTransaction> ethDistributePrivateTransaction(
            String signedTransactionData, List<String> privateFor, PrivacyFlag privacyFlag) {
        return ethDistributePrivateTransaction(
                signedTransactionData, privateFor, privacyFlag, null);
    }

    @Override
    public Request<?, EthSendTransaction> ethDistributePrivateTransaction(
            String signedTransactionData, List<String> privateFor) {
        return ethDistributePrivateTransaction(signedTransactionData, privateFor, null, null);
    }

    @Override
    public Request<?, EthAddress> ethGetPrivacyPrecompileAddress() {
        return new Request<>(
                "eth_getPrivacyPrecompileAddress", Arrays.asList(), web3jService, EthAddress.class);
    }

    @Override
    public Request<?, EthTransaction> ethGetPrivateTransactionByHash(String hexDigest) {
        return new Request<>(
                "eth_getPrivateTransactionByHash",
                Collections.singletonList(hexDigest),
                web3jService,
                EthTransaction.class);
    }

    @Override
    public Request<?, EthGetTransactionReceipt> ethGetPrivateTransactionReceipt(String hexDigest) {
        return new Request<>(
                "eth_getPrivateTransactionReceipt",
                Collections.singletonList(hexDigest),
                web3jService,
                EthGetTransactionReceipt.class);
    }

    // raft consensus

    @Override
    public Request<?, RaftLeader> raftGetLeader() {
        return new Request<>(
                "raft_leader", Collections.emptyList(), web3jService, RaftLeader.class);
    }

    @Override
    public Request<?, RaftRole> raftGetRole() {
        return new Request<>("raft_role", Collections.emptyList(), web3jService, RaftRole.class);
    }

    @Override
    public Request<?, RaftPeerId> raftAddPeer(String enode) {
        return new Request<>(
                "raft_addPeer", Collections.singletonList(enode), web3jService, RaftPeerId.class);
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
                "raft_cluster", Collections.emptyList(), web3jService, RaftCluster.class);
    }

    @Override
    public Request<?, RaftPeerId> raftAddLearner(String enode) {
        return new Request<>(
                "raft_addLearner",
                Collections.singletonList(enode),
                web3jService,
                RaftPeerId.class);
    }

    @Override
    public Request<?, RaftPromote> raftPromoteToPeer(int raftId) {
        return new Request<>(
                "raft_promoteToPeer",
                Collections.singletonList(raftId),
                web3jService,
                RaftPromote.class);
    }

    // istanbul consensus

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

    @Override
    public Request<?, IstanbulNodeAddress> istanbulNodeAddress() {
        return new Request<>(
                "istanbul_nodeAddress",
                Collections.emptyList(),
                web3jService,
                IstanbulNodeAddress.class);
    }

    @Override
    public Request<?, IstanbulBlockSigners> istanbulGetSignersFromBlock(String blockNum) {
        return new Request<>(
                "istanbul_getSignersFromBlock",
                Collections.singletonList(blockNum),
                web3jService,
                IstanbulBlockSigners.class);
    }

    @Override
    public Request<?, IstanbulBlockSigners> istanbulGetSignersFromBlockByHash(String blockHash) {
        return new Request<>(
                "istanbul_getSignersFromBlockByHash",
                Collections.singletonList(blockHash),
                web3jService,
                IstanbulBlockSigners.class);
    }

    // permissioning

    @Override
    public Request<?, PermissionOrgList> quorumPermissionGetOrgList() {
        return new Request<>(
                "quorumPermission_orgList",
                Collections.emptyList(),
                web3jService,
                PermissionOrgList.class);
    }

    @Override
    public Request<?, PermissionNodeList> quorumPermissionGetNodeList() {
        return new Request<>(
                "quorumPermission_nodeList",
                Collections.emptyList(),
                web3jService,
                PermissionNodeList.class);
    }

    @Override
    public Request<?, PermissionAccountList> quorumPermissionGetAccountList() {
        return new Request<>(
                "quorumPermission_acctList",
                Collections.emptyList(),
                web3jService,
                PermissionAccountList.class);
    }

    @Override
    public Request<?, PermissionRoleList> quorumPermissionGetRoleList() {
        return new Request<>(
                "quorumPermission_roleList",
                Collections.emptyList(),
                web3jService,
                PermissionRoleList.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionAddOrg(
            String orgId, String enodeId, String address, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_addOrg",
                Arrays.asList(orgId, enodeId, address, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionApproveOrg(
            String orgId, String enodeId, String address, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_approveOrg",
                Arrays.asList(orgId, enodeId, address, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionAddSubOrg(
            String pOrgId, String orgId, String enodeId, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_addSubOrg",
                Arrays.asList(pOrgId, orgId, enodeId, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionUpdateOrgStatus(
            String orgId, int action, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_updateOrgStatus",
                Arrays.asList(orgId, action, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionApproveOrgStatus(
            String orgId, int action, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_approveOrgStatus",
                Arrays.asList(orgId, action, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionAddNode(
            String orgId, String enodeId, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_addNode",
                Arrays.asList(orgId, enodeId, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionUpdateNodeStatus(
            String orgId, String enodeId, int action, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_updateNodeStatus",
                Arrays.asList(orgId, enodeId, action, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionAssignAdminRole(
            String orgId, String address, String roleId, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_assignAdminRole",
                Arrays.asList(orgId, address, roleId, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionApproveAdminRole(
            String orgId, String address, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_approveAdminRole",
                Arrays.asList(orgId, address, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionAddNewRole(
            String orgId,
            String roleId,
            int access,
            boolean isVoter,
            boolean isAdmin,
            PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_addNewRole",
                Arrays.asList(orgId, roleId, access, isVoter, isAdmin, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionRemoveRole(
            String orgId, String roleId, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_removeRole",
                Arrays.asList(orgId, roleId, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionAddAccountToOrg(
            String address, String orgId, String roleId, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_addAccountToOrg",
                Arrays.asList(address, orgId, roleId, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionChangeAccountRole(
            String address, String orgId, String roleId, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_changeAccountRole",
                Arrays.asList(address, orgId, roleId, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionUpdateAccountStatus(
            String orgId, String address, int status, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_updateAccountStatus",
                Arrays.asList(orgId, address, status, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, OrgDetailsInfo> quorumPermissionGetOrgDetails(String orgId) {
        return new Request<>(
                "quorumPermission_getOrgDetails",
                Collections.singletonList(orgId),
                web3jService,
                OrgDetailsInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionRecoverBlackListedNode(
            String orgId, String enodeId, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_recoverBlackListedNode",
                Arrays.asList(orgId, enodeId, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionApproveBlackListedNodeRecovery(
            String orgId, String enodeId, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_approveBlackListedNodeRecovery",
                Arrays.asList(orgId, enodeId, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionRecoverBlackListedAccount(
            String orgId, String address, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_recoverBlackListedAccount",
                Arrays.asList(orgId, address, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ExecStatusInfo> quorumPermissionApproveBlackListedAccountRecovery(
            String orgId, String address, PrivateTransaction transaction) {
        return new Request<>(
                "quorumPermission_approveBlackListedAccountRecovery",
                Arrays.asList(orgId, address, transaction),
                web3jService,
                ExecStatusInfo.class);
    }

    @Override
    public Request<?, ActiveExtensionList> quorumExtensionActiveExtensionContracts() {
        return new Request<>(
                "quorumExtension_activeExtensionContracts",
                Collections.emptyList(),
                web3jService,
                ActiveExtensionList.class);
    }

    @Override
    public Request<?, ApproveExtensionInfo> quorumExtensionApproveExtension(
            String addressToVoteOn, boolean vote, PrivateTransaction transaction) {
        return new Request<>(
                "quorumExtension_approveExtension",
                Arrays.asList(addressToVoteOn, vote, transaction),
                web3jService,
                ApproveExtensionInfo.class);
    }

    @Override
    public Request<?, CancelExtensionInfo> quorumExtensionCancelExtension(
            String extensionContract, PrivateTransaction transaction) {
        return new Request<>(
                "quorumExtension_cancelExtension",
                Arrays.asList(extensionContract, transaction),
                web3jService,
                CancelExtensionInfo.class);
    }

    @Override
    public Request<?, ExtendContractInfo> quorumExtensionExtendContract(
            String toExtend,
            String newRecipientPtmPublicKey,
            String recipientAddress,
            PrivateTransaction transaction) {
        return new Request<>(
                "quorumExtension_extendContract",
                Arrays.asList(toExtend, newRecipientPtmPublicKey, recipientAddress, transaction),
                web3jService,
                ExtendContractInfo.class);
    }

    @Override
    public Request<?, ExtensionStatusInfo> quorumExtensionGetExtensionStatus(
            String managementContractAddress) {
        return new Request<>(
                "quorumExtension_getExtensionStatus",
                Arrays.asList(managementContractAddress),
                web3jService,
                ExtensionStatusInfo.class);
    }

    @Override
    public Request<?, ExtensionApprovalUuid> quorumExtensionGenerateExtensionApprovalUuid(
            String addressToVoteOn, String externalSignerAddress, PrivateTransaction transaction) {
        return new Request<>(
                "quorumExtension_generateExtensionApprovalUuid",
                Arrays.asList(addressToVoteOn, externalSignerAddress, transaction),
                web3jService,
                ExtensionApprovalUuid.class);
    }
}
