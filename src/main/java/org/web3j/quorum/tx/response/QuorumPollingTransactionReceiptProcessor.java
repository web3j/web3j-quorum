/*
 * Copyright 2019 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j.quorum.tx.response;

import java.io.IOException;
import java.util.Optional;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.methods.response.EthGetQuorumTransactionReceipt;
import org.web3j.quorum.methods.response.QuorumTransactionReceipt;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

/**
 * Quorum's Transaction Receipt Processor object.
 *
 * <p>This is almost identical to the Ethereum {@link
 * org.web3j.tx.response.PollingTransactionReceiptProcessor} with the exception that it returns a
 * QuorumTransactionReceipt object. Note that this class currently pretty much replaces the
 * superclass, as PollingTransactionReceiptProcessor doesn't really lend itself to reuse.
 */
public class QuorumPollingTransactionReceiptProcessor extends PollingTransactionReceiptProcessor {

    private final Quorum quorum;

    public QuorumPollingTransactionReceiptProcessor(
            Quorum quorum, long sleepDuration, int attempts) {
        super(quorum, sleepDuration, attempts);
        this.quorum = quorum;
    }

    /*
     * Returns QuorumTransactionReceipt.
     */
    @Override
    public TransactionReceipt waitForTransactionReceipt(String transactionHash)
            throws IOException, TransactionException {

        return getQuorumTransactionReceipt(transactionHash, sleepDuration, attempts);
    }

    private QuorumTransactionReceipt getQuorumTransactionReceipt(
            String transactionHash, long sleepDuration, int attempts)
            throws IOException, TransactionException {

        Optional<? extends QuorumTransactionReceipt> receiptOptional =
                sendTransactionReceiptRequest(transactionHash);
        for (int i = 0; i < attempts; i++) {
            if (!receiptOptional.isPresent()) {
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    throw new TransactionException(e);
                }

                receiptOptional = sendTransactionReceiptRequest(transactionHash);
            } else {
                return receiptOptional.get();
            }
        }

        throw new TransactionException(
                "Transaction receipt was not generated after "
                        + ((sleepDuration * attempts) / 1000
                                + " seconds for transaction: "
                                + transactionHash),
                transactionHash);
    }

    Optional<? extends QuorumTransactionReceipt> sendTransactionReceiptRequest(
            String transactionHash) throws IOException, TransactionException {
        EthGetQuorumTransactionReceipt transactionReceipt =
                quorum.ethGetQuorumTransactionReceipt(transactionHash).send();

        if (transactionReceipt.hasError()) {
            throw new TransactionException(
                    "Error processing request: " + transactionReceipt.getError().getMessage());
        }

        return transactionReceipt.getTransactionReceipt();
    }
}
