package org.web3j.quorum.methods.request;

import java.math.BigInteger;
import java.util.List;

import org.web3j.protocol.core.methods.request.Transaction;

/**
 * Quorum's Transaction object.
 */
public class PrivateTransaction extends Transaction {

    private List<String> privateFor;

    public PrivateTransaction(
            String from, BigInteger nonce, BigInteger gasLimit, String to, BigInteger value,
            String data, List<String> privateFor) {
        super(from, nonce, null, gasLimit, to, value, data);
        this.privateFor = privateFor;
    }

    public List<String> getPrivateFor() {
        return privateFor;
    }

    public void setPrivateFor(List<String> privateFor) {
        this.privateFor = privateFor;
    }
}
