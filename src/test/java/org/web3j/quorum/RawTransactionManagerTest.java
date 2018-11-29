package org.web3j.quorum;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.enclave.Constellation;
import org.web3j.quorum.enclave.Enclave;
import org.web3j.quorum.enclave.Tessera;
import org.web3j.quorum.enclave.protocol.EnclaveService;
import org.web3j.quorum.enclave.protocol.http.EnclaveHttpService;
import org.web3j.quorum.enclave.protocol.ipc.EnclaveIpcService;
import org.web3j.quorum.enclave.protocol.ipc.UnixEnclaveIpcService;
import org.web3j.quorum.tx.QuorumTransactionManager;
import org.web3j.quorum.generated.Greeter;
import org.web3j.quorum.generated.HumanStandardToken;
import org.web3j.tx.gas.DefaultGasProvider;

import javax.validation.groups.Default;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.web3j.quorum.tx.util.Base64Kt.decode;
import static org.web3j.quorum.tx.util.Base64Kt.encode;
import static org.web3j.tx.Contract.GAS_LIMIT;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;

/**
 * Useful integration tests for verifying Quorum deployments and transaction privacy.
 *
 * <p>To use, simply run:
 *
 * <code>git clone https://github.com/blk-io/crux.git</code>
 * <code>docker-compose -f docker/quorum-crux/docker-compose.yaml up</code>
 *
 * <p>Then you're good to go!
 */

@Ignore
public class RawTransactionManagerTest {

    // Node details are those of blk.io's Sample Quorum with Crux network, available at:
    // https://github.com/blk-io/crux/tree/master/docker/quorum-crux/
    private static final Node quorum1 = new Node(
            "0xed9d02e382b34818e88b88a309c7fe71e65f419d",
            Arrays.asList(
                    "/+UuD63zItL1EbjxkKUljMgG8Z1w0AJ8pNOR4iq2yQc="),
            "http://localhost:22001");

    private static final Node quorum2 = new Node(
            "0xca843569e3427144cead5e4d5999a3d0ccf92b8e",
            Arrays.asList(
                    "yGcjkFyZklTTXrn8+WIkYwicA2EGBn9wZFkctAad4X0="),
            "http://localhost:22002");

    private static final Node quorum3 = new Node(
            "0x0fbdc686b912d7722dc86510934589e0aaf3b55a",
            Arrays.asList(
                    "jP4f+k/IbJvGyh0LklWoea2jQfmLwV53m9XoHVS4NSU="),
            "http://localhost:22003");

    private static final Node quorum4 = new Node(
            "0x9186eb3d20cbd1f5f992a950d808c4495153abd5",
            Arrays.asList(
                    "giizjhZQM6peq52O7icVFxdTmTYinQSUsvyhXzgZqkE="),
            "http://localhost:22004");

    private static final List<Node> nodes = Arrays.asList(
            quorum1, quorum2, quorum3, quorum4);


    @Test
    public void testNodes() throws Exception {

        // Send transactions between all sets of nodes
        for (int count = 0; count < 1; count++) {
            for (int i = 0; i <= 0; i++) {
                Node sourceNode = nodes.get(i);
                Node destNode = nodes.get((i + 1) % nodes.size());

                String keyFile = "keyfiles/key" + String.valueOf(i + 1);
                String url = "http://localhost:8090";
                EnclaveService service = new EnclaveHttpService(url, 8090);
                Quorum quorum = Quorum.build(new HttpService(sourceNode.getUrl()));
                Tessera tessera = new Tessera(service, quorum);
                testRawTransactionsWithGreeterContract(sourceNode, destNode, keyFile, tessera);
                runPrivateHumanStandardTokenTest(sourceNode, destNode, keyFile, tessera);

            }
        }
    }

    public void testRawTransactionsWithGreeterContract (Node sourceNode,
                                                        Node destNode,
                                                        String keyFile,
                                                        Enclave enclave) throws Exception {

        Quorum quorum = Quorum.build(new HttpService(sourceNode.getUrl()));

        ClassLoader classLoader = getClass().getClassLoader();
        Credentials credentials = WalletUtils.loadCredentials("", new File(classLoader.getResource(keyFile).getFile()));

        QuorumTransactionManager transactionManager = new QuorumTransactionManager(
                quorum,
                credentials,
                sourceNode.getPublicKeys().get(0),
                destNode.getPublicKeys(),
                enclave,
                5000,
                5);

        String greeting = "Hello Quorum world!";

        Greeter contract = Greeter.deploy(
                quorum,
                transactionManager,
                BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT,
                greeting).send();

        assertThat(contract.greet().send() , is(greeting));
    }

    private void runPrivateHumanStandardTokenTest(
            Node sourceNode, Node destNode, String keyFile,
            Enclave enclave) throws Exception {

        Quorum quorum = Quorum.build(new HttpService(sourceNode.getUrl()));

        ClassLoader classLoader = getClass().getClassLoader();
        Credentials credentials = WalletUtils.loadCredentials("", new File(classLoader.getResource(keyFile).getFile()));


        QuorumTransactionManager transactionManager = new QuorumTransactionManager(
                quorum,
                credentials,
                sourceNode.getPublicKeys().get(0),
                destNode.getPublicKeys(),
                enclave,
                5000,
                5);

        BigInteger aliceQty = BigInteger.valueOf(1_000_000);
        final String aliceAddress = sourceNode.getAddress();
        final String bobAddress = destNode.getAddress();

        HumanStandardToken contract = HumanStandardToken.deploy(quorum, transactionManager,
                BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT,
                aliceQty, "web3j tokens",
                BigInteger.valueOf(18), "w3j$").send();

        Assert.assertTrue(contract.isValid());

        Assert.assertThat(contract.totalSupply().send(), equalTo(aliceQty));

        Assert.assertThat(contract.balanceOf(sourceNode.getAddress()).send(),
                equalTo(aliceQty));

        // transfer tokens
        BigInteger transferQuantity = BigInteger.valueOf(100_000);

        TransactionReceipt aliceTransferReceipt = contract.transfer(
                destNode.getAddress(), transferQuantity).send();

        HumanStandardToken.TransferEventResponse aliceTransferEventValues =
                contract.getTransferEvents(aliceTransferReceipt).get(0);

        Assert.assertThat(aliceTransferEventValues._from,
                equalTo(aliceAddress));
        Assert.assertThat(aliceTransferEventValues._to,
                equalTo(bobAddress));
        Assert.assertThat(aliceTransferEventValues._value,
                equalTo(transferQuantity));

        aliceQty = aliceQty.subtract(transferQuantity);

        BigInteger bobQty = BigInteger.ZERO;
        bobQty = bobQty.add(transferQuantity);

        Assert.assertThat(contract.balanceOf(sourceNode.getAddress()).send(),
                equalTo(aliceQty));
        Assert.assertThat(contract.balanceOf(destNode.getAddress()).send(),
                equalTo(bobQty));

        // set an allowance
        Assert.assertThat(contract.allowance(
                aliceAddress, bobAddress).send(),
                equalTo(BigInteger.ZERO));

        transferQuantity = BigInteger.valueOf(50);
        TransactionReceipt approveReceipt = contract.approve(
                destNode.getAddress(), transferQuantity).send();

        HumanStandardToken.ApprovalEventResponse approvalEventValues =
                contract.getApprovalEvents(approveReceipt).get(0);

        Assert.assertThat(approvalEventValues._owner,
                equalTo(aliceAddress));
        Assert.assertThat(approvalEventValues._spender,
                equalTo(bobAddress));
        Assert.assertThat(approvalEventValues._value,
                equalTo(transferQuantity));

        Assert.assertThat(contract.allowance(
                aliceAddress, bobAddress).send(),
                equalTo(transferQuantity));
    }
}
