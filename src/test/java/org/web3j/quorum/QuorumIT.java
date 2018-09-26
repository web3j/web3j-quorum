package org.web3j.quorum;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.generated.Greeter;
import org.web3j.quorum.generated.HumanStandardToken;
import org.web3j.quorum.tx.ClientTransactionManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;
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
public class QuorumIT {

    // Node details are those of blk.io's Sample Quorum with Crux network, available at:
    // https://github.com/blk-io/crux/tree/master/docker/quorum-crux/
    private static final Node quorum1 = new Node(
            "0xed9d02e382b34818e88b88a309c7fe71e65f419d",
            Arrays.asList(
                    "BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo="),
            "http://localhost:22001");

    private static final Node quorum2 = new Node(
            "0xca843569e3427144cead5e4d5999a3d0ccf92b8e",
            Arrays.asList(
                    "QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc="),
            "http://localhost:22002");

    private static final Node quorum3 = new Node(
            "0x0fbdc686b912d7722dc86510934589e0aaf3b55a",
            Arrays.asList(
                    "1iTZde/ndBHvzhcl7V68x44Vx7pl8nwx9LqnM/AfJUg="),
            "http://localhost:22003");

    private static final Node quorum4 = new Node(
            "0x9186eb3d20cbd1f5f992a950d808c4495153abd5",
            Arrays.asList(
                    "oNspPPgszVUFw0qmGFfWwh1uxVUXgvBxleXORHj07g8="),
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

                runPrivateGreeterTest(sourceNode, destNode, Integer.toString(i));
                runPrivateHumanStandardTokenTest(sourceNode, destNode);
            }
        }
    }

    private void runPrivateGreeterTest(
            Node sourceNode, Node destNode, String requestId) throws Exception {
        Quorum quorum = Quorum.build(new HttpService(sourceNode.getUrl()));

        ClientTransactionManager transactionManager =
                new ClientTransactionManager(
                        quorum,
                        sourceNode.getAddress(),
                        sourceNode.getPublicKeys().get(0),
                        destNode.getPublicKeys());

        String greeting = "Hello Quorum world! [" + requestId + "]";
        Greeter contract = Greeter.deploy(
                quorum, transactionManager,
                GAS_PRICE, GAS_LIMIT,
                greeting).send();

        assertThat(contract.greet().send(), is(greeting));
    }

    private void runPrivateHumanStandardTokenTest(
            Node sourceNode, Node destNode) throws Exception {

        Quorum quorum = Quorum.build(new HttpService(sourceNode.getUrl()));

        ClientTransactionManager transactionManager =
                new ClientTransactionManager(
                        quorum,
                        sourceNode.getAddress(),
                        sourceNode.getPublicKeys().get(0),
                        destNode.getPublicKeys());

        BigInteger aliceQty = BigInteger.valueOf(1_000_000);
        final String aliceAddress = sourceNode.getAddress();
        final String bobAddress = destNode.getAddress();

        HumanStandardToken contract = HumanStandardToken.deploy(quorum, transactionManager,
                GAS_PRICE, GAS_LIMIT,
                aliceQty, "web3j tokens",
                BigInteger.valueOf(18), "w3j$").send();

        assertTrue(contract.isValid());

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
