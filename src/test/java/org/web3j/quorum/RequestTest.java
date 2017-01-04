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
                        Arrays.asList("private1", "private2")
                )
        ).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"eth_sendTransaction\",\"params\":[{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0xa\",\"value\":\"0xa\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"privateFor\":[\"private1\",\"private2\"]}],\"id\":1}");
    }

    @Test
    public void testNodeInfo() throws Exception {
        web3j.quorumNodeInfo().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"quorum_nodeInfo\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testCanonicalHash() throws Exception {
        web3j.quorumCanonicalHash(BigInteger.ONE).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"quorum_canonicalHash\",\"params\":[\"0x1\"],\"id\":1}");
    }

    @Test
    public void testVote() throws Exception {
        web3j.quorumVote("0xd0b10f3352b5a3dcbddac2c282a18b01a3b5fe52cc3c7022b2fff7cbf0fce3a6")
                .send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"quorum_vote\",\"params\":[\"0xd0b10f3352b5a3dcbddac2c282a18b01a3b5fe52cc3c7022b2fff7cbf0fce3a6\"],\"id\":1}");
    }

    @Test
    public void testMakeBlock() throws Exception {
        web3j.quorumMakeBlock().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"quorum_makeBlock\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testPauseBlockMaker() throws Exception {
        web3j.quorumPauseBlockMaker().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"quorum_pauseBlockMaker\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testResumeBlockMaker() throws Exception {
        web3j.quorumResumeBlockMaker().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"quorum_resumeBlockMaker\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testIsBlockMaker() throws Exception {
        web3j.quorumIsBlockMaker("0xca843569e3427144cead5e4d5999a3d0ccf92b8e").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"quorum_isBlockMaker\",\"params\":[\"0xca843569e3427144cead5e4d5999a3d0ccf92b8e\"],\"id\":1}");
    }

    @Test
    public void testIsVoter() throws Exception {
        web3j.quorumIsVoter("0x0fbdc686b912d7722dc86510934589e0aaf3b55a").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"quorum_isVoter\",\"params\":[\"0x0fbdc686b912d7722dc86510934589e0aaf3b55a\"],\"id\":1}");
    }
}
