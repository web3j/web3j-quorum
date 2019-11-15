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
    public void testPrivatePayload() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0x\"}");

        PrivatePayload privatePayload = deserialiseResponse(PrivatePayload.class);
        assertThat(privatePayload.getPrivatePayload(), is("0x"));
    }
}
