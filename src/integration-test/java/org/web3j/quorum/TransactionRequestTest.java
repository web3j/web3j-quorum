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

public class TransactionRequestTest extends RequestTester {

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

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"eth_sendRawTransaction\",\"params\":[\"SignedTxData\"],\"id\":1}");
    }

    @Test
    public void testSendRawPrivateTransaction() throws Exception {
        String signedTransactionData = "SignedTxData";
        web3j.ethSendRawPrivateTransaction(
                        signedTransactionData, Arrays.asList("privateFor1", "privateFor2"))
                .send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"eth_sendRawPrivateTransaction\",\"params\":[\"SignedTxData\",{\"privateFor\":[\"privateFor1\",\"privateFor2\"]}],\"id\":1}");
    }

    @Test
    public void testSendRawPrivateTransactionPrivacyFlag() throws Exception {
        String signedTransactionData = "SignedTxData";
        web3j.ethSendRawPrivateTransaction(
                signedTransactionData, Arrays.asList("privateFor1", "privateFor2"), PrivacyFlag.PARTY_PROTECTION)
                .send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"eth_sendRawPrivateTransaction\",\"params\":[\"SignedTxData\",{\"privateFor\":[\"privateFor1\",\"privateFor2\"],\"privacyFlag\":1}],\"id\":1}");
    }

    @Test
    public void testGetPrivateTransaction() throws Exception {
        web3j.quorumGetPrivatePayload("0x").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"eth_getQuorumPayload\",\"params\":[\"0x\"],\"id\":1}");
    }

    @Test
    public void testSendTransactionAsync() throws Exception {
        web3j.ethSendTransactionAsync(
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
                "{\"jsonrpc\":\"2.0\",\"method\":\"eth_sendTransactionAsync\",\"params\":[{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"]}],\"id\":1}");
    }
}
