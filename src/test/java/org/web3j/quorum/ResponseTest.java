package org.web3j.quorum;


import org.junit.Test;

import org.web3j.protocol.ResponseTester;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.VoidResponse;
import org.web3j.quorum.methods.response.*;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ResponseTest extends ResponseTester {

    @Test
    public void testEthSendTransaction() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0x0d9e7e34fd4db216a3f66981a467d9d990954e6ed3128aff4ec51a50fa175663\"}");

        EthSendTransaction transaction = deserialiseResponse(EthSendTransaction.class);
        assertThat(transaction.getTransactionHash(), is("0x0d9e7e34fd4db216a3f66981a467d9d990954e6ed3128aff4ec51a50fa175663"));
    }

    @Test
    public void testNodeInfo() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":{\"blockMakerAccount\":\"0xca843569e3427144cead5e4d5999a3d0ccf92b8e\",\"blockmakestrategy\":{\"maxblocktime\":5,\"minblocktime\":2,\"status\":\"active\",\"type\":\"deadline\"},\"canCreateBlocks\":true,\"canVote\":false,\"voteAccount\":\"0x0fbdc686b912d7722dc86510934589e0aaf3b55a\"}}");

        QuorumNodeInfo quorumNodeInfo = deserialiseResponse(QuorumNodeInfo.class);
        assertThat(quorumNodeInfo.getNodeInfo(), equalTo(
                new QuorumNodeInfo.NodeInfo("0xca843569e3427144cead5e4d5999a3d0ccf92b8e",
                        "0x0fbdc686b912d7722dc86510934589e0aaf3b55a",
                        true,
                        false,
                        new QuorumNodeInfo.BlockMakeStrategy(5, 2, "active", "deadline"))));
    }

    @Test
    public void testCanonicalHash() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0x8bb911238205c6d5e9841335c9c5aff3dfae4c0f6b0df28100737c2660a15f8d\"}");

        CanonicalHash canonicalHash = deserialiseResponse(CanonicalHash.class);
        assertThat(canonicalHash.getCanonicalHash(),
                is("0x8bb911238205c6d5e9841335c9c5aff3dfae4c0f6b0df28100737c2660a15f8d"));
    }

    @Test
    public void testVote() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0x582f40b7915fb069809dc9f0b2c6f03b53d386344d43b4727f83183831822913\"}");

        Vote vote = deserialiseResponse(Vote.class);
        assertThat(vote.getTransactionHash(),
                is("0x582f40b7915fb069809dc9f0b2c6f03b53d386344d43b4727f83183831822913"));
    }

    @Test
    public void testMakeBlock() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0x290ae9295e3a6e35e2d43a8bf9804dfe701c7cd4009619f771735bdebb66372f\"}");

        MakeBlock makeBlock = deserialiseResponse(MakeBlock.class);
        assertThat(makeBlock.getBlockHash(), is("0x290ae9295e3a6e35e2d43a8bf9804dfe701c7cd4009619f771735bdebb66372f"));
    }

    @Test
    public void testVoidResponse() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":null}");

        VoidResponse voidResponse = deserialiseResponse(VoidResponse.class);
        assertTrue(voidResponse.isValid());
    }

    @Test
    public void testBlockMaker() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":true}");

        BlockMaker blockMaker = deserialiseResponse(BlockMaker.class);
        assertTrue(blockMaker.isBlockMaker());
    }

    @Test
    public void testVoter() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":true}");

        Voter voter = deserialiseResponse(Voter.class);
        assertTrue(voter.isVoter());
    }

    @Test
    public void testPrivatePayload() {
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":\"0x\"}");

        PrivatePayload privatePayload = deserialiseResponse(PrivatePayload.class);
        assertThat(privatePayload.getPrivatePayload(), is("0x"));
    }

}
