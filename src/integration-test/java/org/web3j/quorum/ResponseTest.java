package org.web3j.quorum;


import org.junit.Test;

import org.web3j.protocol.ResponseTester;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.quorum.methods.response.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ResponseTest extends ResponseTester {

    @Test
    public void testEthSendTransaction() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0x0d9e7e34fd4db216a3f66981a467d9d990954e6ed3128aff4ec51a50fa175663\"}");

        EthSendTransaction transaction = deserialiseResponse(EthSendTransaction.class);
        assertThat(transaction.getTransactionHash(), is("0x0d9e7e34fd4db216a3f66981a467d9d990954e6ed3128aff4ec51a50fa175663"));
    }

    @Test
    public void testPrivatePayload() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0x\"}");

        PrivatePayload privatePayload = deserialiseResponse(PrivatePayload.class);
        assertThat(privatePayload.getPrivatePayload(), is("0x"));
    }
}
