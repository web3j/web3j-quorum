package org.web3j.quorum;

import java.math.BigInteger;
import java.util.Collections;
import java.util.concurrent.ScheduledExecutorService;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.VoidResponse;
import org.web3j.quorum.methods.request.PrivateTransaction;
import org.web3j.quorum.methods.response.BlockMaker;
import org.web3j.quorum.methods.response.CanonicalHash;
import org.web3j.quorum.methods.response.MakeBlock;
import org.web3j.quorum.methods.response.PrivatePayload;
import org.web3j.quorum.methods.response.QuorumNodeInfo;
import org.web3j.quorum.methods.response.Vote;
import org.web3j.quorum.methods.response.Voter;
import org.web3j.utils.Numeric;

/**
 * Quorum JSON-RPC API implementation.
 */
public class JsonRpc2_0Quorum extends JsonRpc2_0Web3j implements Quorum {

    public JsonRpc2_0Quorum(Web3jService web3jService) {
        super(web3jService);
    }

    public JsonRpc2_0Quorum(
            Web3jService web3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        super(web3jService, pollingInterval, scheduledExecutorService);
    }

    @Override
    public Request<?, EthSendTransaction> ethSendRawTransaction(
            String signedTransactionData) {
        throw new UnsupportedOperationException("Quorum requires PrivateTransaction types");
    }

    @Override
    public Request<?, EthSendTransaction> ethSendTransaction(
            PrivateTransaction transaction) {
        return new Request<>(
                "eth_sendTransaction",
                Collections.singletonList(transaction),
                ID,
                web3jService,
                EthSendTransaction.class);
    }

    @Override
    public Request<?, QuorumNodeInfo> quorumNodeInfo() {
        return new Request<>(
                "quorum_nodeInfo",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                QuorumNodeInfo.class);
    }

    @Override
    public Request<?, CanonicalHash> quorumCanonicalHash(BigInteger blockHeight) {
        return new Request<>(
                "quorum_canonicalHash",
                Collections.singletonList(Numeric.encodeQuantity(blockHeight)),
                ID,
                web3jService,
                CanonicalHash.class);
    }

    @Override
    public Request<?, Vote> quorumVote(String blockHash) {
        return new Request<>(
                "quorum_vote",
                Collections.singletonList(blockHash),
                ID,
                web3jService,
                Vote.class);
    }

    @Override
    public Request<?, MakeBlock> quorumMakeBlock() {
        return new Request<>(
                "quorum_makeBlock",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                MakeBlock.class);
    }

    @Override
    public Request<?, VoidResponse> quorumPauseBlockMaker() {
        return new Request<>(
                "quorum_pauseBlockMaker",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                VoidResponse.class);
    }

    @Override
    public Request<?, VoidResponse> quorumResumeBlockMaker() {
        return new Request<>(
                "quorum_resumeBlockMaker",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                VoidResponse.class);
    }

    @Override
    public Request<?, BlockMaker> quorumIsBlockMaker(String address) {
        return new Request<>(
                "quorum_isBlockMaker",
                Collections.singletonList(address),
                ID,
                web3jService,
                BlockMaker.class);
    }

    @Override
    public Request<?, Voter> quorumIsVoter(String address) {
        return new Request<>(
                "quorum_isVoter",
                Collections.singletonList(address),
                ID,
                web3jService,
                Voter.class);
    }

    @Override
    public Request<?, PrivatePayload> quorumGetPrivatePayload(String hexDigest) {
        return new Request<>(
                "quorum_getPrivatePayload",
                Collections.singletonList(hexDigest),
                ID,
                web3jService,
                PrivatePayload.class);
    }
}
