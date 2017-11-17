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
 */
@Ignore
public class QuorumIT {

    private static final List<Node> nodes = Arrays.asList(
            // FIXME: add your node configurations here
            );

    @Test
    public void testNodes() throws Exception {

        for (int count = 0; count < 1; count++) {
            for (int i = 0; i < nodes.size(); i++) {
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
                        sourceNode.getAddress(), destNode.getPublicKeys());

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
                        sourceNode.getAddress(), destNode.getPublicKeys());

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
