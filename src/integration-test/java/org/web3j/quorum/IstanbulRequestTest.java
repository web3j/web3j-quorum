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

public class IstanbulRequestTest extends RequestTester {

    private Quorum web3j;

    @Override
    protected void initWeb3Client(HttpService httpService) {
        web3j = Quorum.build(httpService);
    }

    @Test
    public void testIstanbulGetSnapshot() throws Exception {
        web3j.istanbulGetSnapshot("latest").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_getSnapshot\",\"params\":[\"latest\"],\"id\":1}");
    }

    @Test
    public void testIstanbulGetSnapshotAtHash() throws Exception {
        web3j.istanbulGetSnapshotAtHash("blockHash").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_getSnapshotAtHash\",\"params\":[\"blockHash\"],\"id\":1}");
    }

    @Test
    public void testIstanbulGetValidators() throws Exception {
        web3j.istanbulGetValidators("latest").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_getValidators\",\"params\":[\"latest\"],\"id\":1}");
    }

    @Test
    public void testIstanbulGetValidatorsAtHash() throws Exception {
        web3j.istanbulGetValidatorsAtHash("blockHash").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_getValidatorsAtHash\",\"params\":[\"blockHash\"],\"id\":1}");
    }

    @Test
    public void testIstanbulPropose() throws Exception {
        web3j.istanbulPropose("address", true).send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_propose\",\"params\":[\"address\",true],\"id\":1}");
    }

    @Test
    public void testIstanbulDiscard() throws Exception {
        web3j.istanbulDiscard("address").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_discard\",\"params\":[\"address\"],\"id\":1}");
    }

    @Test
    public void testIstanbulCandidates() throws Exception {
        web3j.istanbulCandidates().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_candidates\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testIstanbulNodeAddress() throws Exception {
        web3j.istanbulNodeAddress().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_nodeAddress\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testIstanbulBlockSigners() throws Exception {
        web3j.istanbulGetSignersFromBlock("blockNum").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_getSignersFromBlock\",\"params\":[\"blockNum\"],\"id\":1}");
    }

    @Test
    public void testIstanbulBlockSignersByHash() throws Exception {
        web3j.istanbulGetSignersFromBlockByHash("blockHash").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"istanbul_getSignersFromBlockByHash\",\"params\":[\"blockHash\"],\"id\":1}");
    }
}
