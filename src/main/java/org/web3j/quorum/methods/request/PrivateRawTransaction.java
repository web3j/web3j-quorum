package org.web3j.quorum.methods.request;

import java.util.List;

/**
 * Quorum's Transaction object.
 *
 * <p>This is almost identical to the Ethereum
 * {@link org.web3j.protocol.core.methods.request.Transaction} with the exception that it provides
 * the privateFor field and does not contain a gas price.
 */
public class PrivateRawTransaction {

    private List<String> privateFor;

    public PrivateRawTransaction(List<String> privateFor) {
        this.privateFor = privateFor;
    }

    public List<String> getPrivateFor() {
        return privateFor;
    }

    public void setPrivateFor(List<String> privateFor) {
        this.privateFor = privateFor;
    }
}
