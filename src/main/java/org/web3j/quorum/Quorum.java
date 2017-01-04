package org.web3j.quorum;

import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.parity.methods.response.VoidResponse;
import org.web3j.quorum.methods.request.*;
import org.web3j.quorum.methods.response.*;

/**
 * JSON-RPC Request object building factory for Quorum.
 */
public interface Quorum extends Web3j {
    static Quorum build(Web3jService web3jService) {
        return new JsonRpc2_0Quorum(web3jService);
    }

    Request<?, EthSendTransaction> ethSendTransaction(PrivateTransaction transaction);

    Request<?, QuorumNodeInfo> quorumNodeInfo();

    Request<?, CanonicalHash> quorumCanonicalHash(BigInteger blockHeight);

    Request<?, Vote> quorumVote(String blockHash);

    Request<?, MakeBlock> quorumMakeBlock();

    Request<?, VoidResponse> quorumPauseBlockMaker();

    Request<?, VoidResponse> quorumResumeBlockMaker();

    Request<?, BlockMaker> quorumIsBlockMaker(String address);

    Request<?, Voter> quorumIsVoter(String address);
}
