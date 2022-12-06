/*
 * Copyright 2022 Web3 Labs Ltd.
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

public class ExtensionRequestTest extends RequestTester {

    private Quorum web3j;

    @Override
    protected void initWeb3Client(HttpService httpService) {
        web3j = Quorum.build(httpService);
    }

    @Test
    public void testActiveExtensionList() throws Exception {
        web3j.quorumExtensionActiveExtensionContracts().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumExtension_activeExtensionContracts\",\"params\":[],\"id\":0}");
    }

    @Test
    public void testApproveExtension() throws Exception {
        web3j.quorumExtensionApproveExtension(
                        "addressToVoteOn",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumExtension_approveExtension\",\"params\":[\"addressToVoteOn\",true,{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":0}");
    }

    @Test
    public void testCancelExtension() throws Exception {
        web3j.quorumExtensionCancelExtension(
                        "extensionContract",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumExtension_cancelExtension\",\"params\":[\"extensionContract\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":0}");
    }

    @Test
    public void testExtendContract() throws Exception {
        web3j.quorumExtensionExtendContract(
                        "toExtend",
                        "newRecipientPtmPublicKey",
                        "recipientAddress",
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumExtension_extendContract\",\"params\":[\"toExtend\",\"newRecipientPtmPublicKey\",\"recipientAddress\",{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":0}],\"id\":0}");
    }

    @Test
    public void testExtensionStatus() throws Exception {
        web3j.quorumExtensionGetExtensionStatus("managementContractAddress").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"quorumExtension_getExtensionStatus\",\"params\":[\"managementContractAddress\"],\"id\":0}");
    }
}
