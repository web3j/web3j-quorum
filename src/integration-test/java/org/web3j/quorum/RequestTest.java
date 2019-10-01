package org.web3j.quorum;


import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;

import org.web3j.protocol.RequestTester;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.methods.request.PrivateTransaction;


public class RequestTest extends RequestTester {

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
                        Arrays.asList("privateFor1", "privateFor2")
                )
        ).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"eth_sendTransaction\",\"params\":[{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFrom\":\"privateFrom\",\"privateFor\":[\"privateFor1\",\"privateFor2\"]}],\"id\":1}");
    }

    @Test
    public void testGetPrivateTransaction() throws Exception {
        web3j.quorumGetPrivatePayload("0x").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"eth_getQuorumPayload\",\"params\":[\"0x\"],\"id\":1}");
    }
}
