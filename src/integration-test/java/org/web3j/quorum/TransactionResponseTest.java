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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TransactionResponseTest extends ResponseTester {

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
    public void testGetContractPrivacyMetadata() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":{\n"
                        + "  creationTxHash: [55, 80],\n"
                        + "  privacyFlag: 3\n"
                        + "}\n}");

        ContractPrivacyMetadataInfo info = deserialiseResponse(ContractPrivacyMetadataInfo.class);
        assertThat(
                info.getContractPrivacyMetadata().getPrivacyFlag(),
                is(PrivacyFlag.PRIVATE_STATE_VALIDATION));
        assertThat(info.getContractPrivacyMetadata().getCreationTxHash(), is(new byte[] {55, 80}));
    }

    @Test
    public void testPrivatePayload() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0x\"}");

        PrivatePayload privatePayload = deserialiseResponse(PrivatePayload.class);
        assertThat(privatePayload.getPrivatePayload(), is("0x"));
    }

    @Test
    public void testGetQuorumTransactionReceipt() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":{\"transactionHash\":\"0x0d9e7e34fd4db216a3f66981a467d9d990954e6ed3128aff4ec51a50fa175663\",\"transactionIndex\":\"0x0\",\"blockHash\":\"0xee5b9e9030d308c77a2d4f975b7090a026ac2cdfe9669e2452cedb4c82e8285e\",\"blockNumber\":\"0xc9e\",\"cumulativeGasUsed\":\"0x0\",\"gasUsed\":\"0x21c687\",\"contractAddress\":\"0x1932c48b2bf8102ba33b4a6b545c32236e342f34\",\"status\":\"0x1\",\"from\":\"0x0718197b9ac69127381ed0c4b5d0f724f857c4d1\",\"to\":\"0x8a5E2a6343108bABEd07899510fb42297938D41F\",\"logs\":[],\"logsBloom\":\"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\",\"isPrivacyMarkerTransaction\":true}}");
        EthGetQuorumTransactionReceipt ethQuorumReceipt =
                (EthGetQuorumTransactionReceipt)
                        this.deserialiseResponse(EthGetQuorumTransactionReceipt.class);

        QuorumTransactionReceipt quorumReceipt = ethQuorumReceipt.getResult();
        assertThat(
                quorumReceipt.getTransactionHash(),
                is("0x0d9e7e34fd4db216a3f66981a467d9d990954e6ed3128aff4ec51a50fa175663"));
        assertThat(
                quorumReceipt.getBlockHash(),
                is("0xee5b9e9030d308c77a2d4f975b7090a026ac2cdfe9669e2452cedb4c82e8285e"));
        assertThat(quorumReceipt.getBlockNumber(), is(BigInteger.valueOf(0xc9e)));
        assertThat(quorumReceipt.getCumulativeGasUsed(), is(BigInteger.valueOf(0x0)));
        assertThat(quorumReceipt.getGasUsed(), is(BigInteger.valueOf(0x21c687)));
        assertThat(
                quorumReceipt.getContractAddress(),
                is("0x1932c48b2bf8102ba33b4a6b545c32236e342f34"));
        assertThat(quorumReceipt.getStatus(), is("0x1"));
        assertThat(quorumReceipt.getFrom(), is("0x0718197b9ac69127381ed0c4b5d0f724f857c4d1"));
        assertThat(quorumReceipt.getTo(), is("0x8a5E2a6343108bABEd07899510fb42297938D41F"));
        assertThat(
                quorumReceipt.getLogsBloom(),
                is(
                        "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        assertThat(quorumReceipt.getIsPrivacyMarkerTransaction(), is(true));
    }
}
