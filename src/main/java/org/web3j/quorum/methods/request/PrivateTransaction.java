package org.web3j.quorum.methods.request;

import java.math.BigInteger;
import java.util.List;

import org.web3j.protocol.core.methods.request.Transaction;

/**
 * Quorum's Transaction object.
 *
 * <p>This is almost identical to the Ethereum
 * {@link org.web3j.protocol.core.methods.request.Transaction} with the exception that it provides
 * the privateFor field and does not contain a gas price.
 */
public class PrivateTransaction extends Transaction {

    private String privateFrom;
    private List<String> privateFor;

    public PrivateTransaction(
            String from, BigInteger nonce, BigInteger gasLimit, String to, BigInteger value,
            String data, String privateFrom, List<String> privateFor) {
        super(from, nonce, null, gasLimit, to, value, data);
        this.privateFrom = privateFrom;
        this.privateFor = privateFor;
    }

    public String getPrivateFrom() {
        return privateFrom;
    }

    public void setPrivateFrom(final String privateFrom) {
        this.privateFrom = privateFrom;
    }

    public List<String> getPrivateFor() {
        return privateFor;
    }

    public void setPrivateFor(List<String> privateFor) {
        this.privateFor = privateFor;
    }
}
