package org.web3j.quorum;

import java.math.BigInteger;
import java.util.Collections;
import java.util.concurrent.ExecutorService;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.VoidResponse;
import org.web3j.quorum.methods.request.*;
import org.web3j.quorum.methods.response.*;
import org.web3j.utils.Numeric;

/**
 * Quorum JSON-RPC API implementation.
 */
public class JsonRpc2_0Quorum extends JsonRpc2_0Web3j implements Quorum {

    public JsonRpc2_0Quorum(Web3jService web3jService) {
        super(web3jService);
    }

    public JsonRpc2_0Quorum(
            Web3jService web3jService, long pollingInterval, ExecutorService executorService) {
        super(web3jService, pollingInterval, executorService);
    }

    @Override
    public Request<?, EthSendTransaction> ethSendTransaction(Transaction transaction) {
        throw new UnsupportedOperationException("Quorum requires PrivateTransaction types");
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
}
