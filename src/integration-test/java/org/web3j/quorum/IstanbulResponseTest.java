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
import org.web3j.quorum.methods.response.istanbul.IstanbulBlockSigners;
import org.web3j.quorum.methods.response.istanbul.IstanbulCandidates;
import org.web3j.quorum.methods.response.istanbul.IstanbulNodeAddress;
import org.web3j.quorum.methods.response.istanbul.IstanbulSnapshot;
import org.web3j.quorum.methods.response.istanbul.IstanbulValidators;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IstanbulResponseTest extends ResponseTester {

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
    public void testIstanbulNodeAddress() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0xd8dba507e85f116b1f7e231ca8525fc9008a6966\"}");

        IstanbulNodeAddress nodeAddress = deserialiseResponse(IstanbulNodeAddress.class);
        assertThat(nodeAddress.getNodeAddress(), is("0xd8dba507e85f116b1f7e231ca8525fc9008a6966"));
    }

    @Test
    public void testIstanbulGetSignersFromBlock() {
        buildResponse(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":{\"Author\":\"0xe36cbeb565b061217930767886474e3cde903ac5\", \"Committers\": [\"0xd8dba507e85f116b1f7e231ca8525fc9008a6966\", \"0xb131288f355bc27090e542ae0be213c20350b767\"], \"Hash\": \"0xfa9e324051c60946b938c13869e92c1e6884343bca7b262f479bc0c8ef3e06f5\", \"Number\": \"20\"}}");

        IstanbulBlockSigners blockSigners = deserialiseResponse(IstanbulBlockSigners.class);
        assertThat(
                blockSigners.getBlockSigners().toString(),
                is(
                        "Optional[BlockSigners(Number=20, Hash=0xfa9e324051c60946b938c13869e92c1e6884343bca7b262f479bc0c8ef3e06f5, Author=0xe36cbeb565b061217930767886474e3cde903ac5, Committers=[0xd8dba507e85f116b1f7e231ca8525fc9008a6966, 0xb131288f355bc27090e542ae0be213c20350b767])]"));
    }
}
