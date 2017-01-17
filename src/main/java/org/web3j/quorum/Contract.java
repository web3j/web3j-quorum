package org.web3j.quorum;

import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionTimeoutException;
import org.web3j.quorum.methods.request.PrivateTransaction;
import org.web3j.utils.Async;

/**
 * Contract implementation for Quorum.
 */
public class Contract extends org.web3j.abi.Contract {

    private List<String> privateFor;
    private String fromAddress;

    public Contract(String contractAddress, Web3j web3j, String fromAddress, BigInteger gasPrice, BigInteger gasLimit, List<String> privateFor) {
        super(contractAddress, web3j, null, gasPrice, gasLimit);

        this.fromAddress = fromAddress;
        this.privateFor = privateFor;
    }

    private void setPrivateFor(String ... addresses) {
        privateFor = Arrays.asList(addresses);
    }

    private void setFromAddress(String address) {
        fromAddress = address;
    }

    @Override
    protected TransactionReceipt executeTransaction(
            String data, BigInteger value) throws ExecutionException, InterruptedException,
            TransactionTimeoutException {

        PrivateTransaction transaction = new PrivateTransaction(
                fromAddress, null, gasLimit, getContractAddress(), value,
                data, privateFor);

        EthSendTransaction transactionResponse = web3j.ethSendTransaction(transaction)
                .sendAsync().get();

        return processResponse(transactionResponse);
    }

    protected static <T extends org.web3j.quorum.Contract> T deploy(
            Class<T> type,
            Web3j web3j, String toAddress,
            BigInteger gasPrice, BigInteger gasLimit,
            String binary, String encodedConstructor, BigInteger value,
            List<String> privateFor) throws Exception {

        Constructor<T> constructor = type.getDeclaredConstructor(
                String.class, Web3j.class, String.class, BigInteger.class, BigInteger.class, List.class);
        constructor.setAccessible(true);

        T contract = constructor.newInstance(null, web3j, toAddress, gasPrice, gasLimit, privateFor);
        TransactionReceipt transactionReceipt =
                contract.executeTransaction(binary + encodedConstructor, value);

        String contractAddress = transactionReceipt.getContractAddress();
        if (contractAddress == null) {
            throw new RuntimeException("Empty contract address returned");
        }
        contract.setContractAddress(contractAddress);

        return contract;
    }

    protected static <T extends org.web3j.quorum.Contract> CompletableFuture<T> deployAsync(
            Class<T> type, Web3j web3j, String toAddress,
            BigInteger gasPrice, BigInteger gasLimit,
            String binary, String encodedConstructor, BigInteger value,
            List<String> privateFor) {

        return Async.run(() -> deploy(type, web3j, toAddress, gasPrice, gasLimit,
                binary, encodedConstructor, value, privateFor));
    }
}
