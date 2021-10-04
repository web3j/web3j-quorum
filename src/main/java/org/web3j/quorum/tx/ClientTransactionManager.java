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
package org.web3j.quorum.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.quorum.PrivacyFlag;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.methods.request.PrivateTransaction;
import org.web3j.quorum.methods.response.QuorumTransactionReceipt;
import org.web3j.quorum.tx.response.QuorumPollingTransactionReceiptProcessor;
import org.web3j.tx.ContractErrorUtil;
import org.web3j.tx.TransactionManager;

/** TransactionManager implementation for using a Quorum node to transact. */
public class ClientTransactionManager extends TransactionManager {

    private static final int SLEEP_DURATION = 1000;
    private static final int ATTEMPTS = 20;

    private final Quorum quorum;
    private final String fromAddress;
    private final String privateFrom;
    private List<String> privateFor;
    private PrivacyFlag privacyFlag;
    private List<String> mandatoryFor;

    public ClientTransactionManager(
            Quorum quorum,
            String fromAddress,
            String privateFrom,
            List<String> privateFor,
            PrivacyFlag privacyFlag,
            List<String> mandatoryFor,
            int attempts,
            int sleepDuration) {
        super(
                new QuorumPollingTransactionReceiptProcessor(quorum, sleepDuration, attempts),
                fromAddress);
        this.quorum = quorum;
        this.fromAddress = fromAddress;
        this.privateFrom = privateFrom;
        this.privateFor = privateFor;
        this.privacyFlag = privacyFlag;
        this.mandatoryFor = mandatoryFor;
    }

    public ClientTransactionManager(
            Quorum quorum,
            String fromAddress,
            String privateFrom,
            List<String> privateFor,
            PrivacyFlag privacyFlag,
            int attempts,
            int sleepDuration) {
        this(
                quorum,
                fromAddress,
                privateFrom,
                privateFor,
                privacyFlag,
                null,
                attempts,
                sleepDuration);
    }

    // For backward compatibility
    public ClientTransactionManager(
            Quorum quorum,
            String fromAddress,
            String privateFrom,
            List<String> privateFor,
            int attempts,
            int sleepDuration) {
        this(quorum, fromAddress, privateFrom, privateFor, null, null, attempts, sleepDuration);
    }

    public ClientTransactionManager(
            Quorum quorum, String fromAddress, String privateFrom, List<String> privateFor) {
        this(quorum, fromAddress, privateFrom, privateFor, ATTEMPTS, SLEEP_DURATION);
    }

    @Deprecated
    public ClientTransactionManager(
            Quorum quorum,
            String fromAddress,
            List<String> privateFor,
            int attempts,
            int sleepDuration) {
        this(quorum, fromAddress, null, privateFor, attempts, sleepDuration);
    }

    @Deprecated
    public ClientTransactionManager(Quorum quorum, String fromAddress, List<String> privateFor) {
        this(quorum, fromAddress, null, privateFor);
    }

    public String getPrivateFrom() {
        return privateFrom;
    }

    public List<String> getPrivateFor() {
        return privateFor;
    }

    public void setPrivateFor(List<String> privateFor) {
        this.privateFor = privateFor;
    }

    @Override
    public EthSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value)
            throws IOException {

        PrivateTransaction transaction =
                new PrivateTransaction(
                        fromAddress,
                        null,
                        gasLimit,
                        to,
                        value,
                        data,
                        privateFrom,
                        privateFor,
                        privacyFlag,
                        mandatoryFor);

        return quorum.ethSendTransaction(transaction).send();
    }

    @Override
    public EthSendTransaction sendTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value,
            boolean constructor)
            throws IOException {
        return sendTransaction(gasPrice, gasLimit, to, data, value);
    }

    @Override
    public EthSendTransaction sendEIP1559Transaction(
            long chainId,
            BigInteger maxPriorityFeePerGas,
            BigInteger maxFeePerGas,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value,
            boolean constructor)
            throws IOException {
        throw new UnsupportedOperationException("sendTransactionEIP1559 is not available");
    }

    @Override
    public String sendCall(String to, String data, DefaultBlockParameter defaultBlockParameter)
            throws IOException {
        EthCall ethCall =
                quorum.ethCall(
                                Transaction.createEthCallTransaction(getFromAddress(), to, data),
                                defaultBlockParameter)
                        .send();
        ContractErrorUtil.assertCallNotReverted(ethCall);
        return ethCall.getValue();
    }

    @Override
    public EthGetCode getCode(String contractAddress, DefaultBlockParameter defaultBlockParameter)
            throws IOException {
        return quorum.ethGetCode(contractAddress, defaultBlockParameter).send();
    }

    /*
     * Override, so that we can retrieve QuorumTransactionReceipt which has extra fields.
     * Also provides support for Privacy Marker Transactions.
     */
    @Override
    protected TransactionReceipt processResponse(EthSendTransaction transactionResponse)
            throws IOException, TransactionException {
        TransactionReceipt transactionReceipt = super.processResponse(transactionResponse);
        if (transactionReceipt instanceof QuorumTransactionReceipt) {
            QuorumTransactionReceipt quorumTransactionReceipt =
                    (QuorumTransactionReceipt) transactionReceipt;

            // If this was a Privacy Marker Transaction, then need to retrieve the receipt for the
            // internal private transaction
            if (quorumTransactionReceipt.isPrivacyMarkerTransaction()) {
                String transactionHash = transactionResponse.getTransactionHash();
                EthGetTransactionReceipt privateTransactionReceipt =
                        quorum.ethGetPrivateTransactionReceipt(transactionHash).send();
                return privateTransactionReceipt.getTransactionReceipt().get();
            }
        }

        return transactionReceipt;
    }
}
