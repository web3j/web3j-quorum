/*
 * Copyright 2019 Web3 Labs LTD.
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
package org.web3j.quorum.core

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.http.HttpService
import org.web3j.quorum.enclave.Enclave
import org.web3j.quorum.generated.Greeter
import org.web3j.quorum.generated.HumanStandardToken
import org.web3j.quorum.tx.QuorumTransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import java.io.File
import java.math.BigInteger

/**
* Helper class that implements methods of the tests
* */
open class Helper {

    @Throws(Exception::class)
    fun testRawTransactionsWithGreeterContract(
        sourceNode: Node,
        destNode: Node,
        keyFile: String,
        enclave: Enclave
    ) {

        val quorum = Quorum.build(HttpService(sourceNode.url))

        val classLoader = javaClass.classLoader
        val credentials = WalletUtils.loadCredentials("", File(classLoader.getResource(keyFile)!!.file))

        val transactionManager = QuorumTransactionManager(
                quorum,
                credentials,
                sourceNode.publicKeys[0],
                destNode.publicKeys,
                enclave)

        val greeting = "Hello Quorum world!"

        val contract = Greeter.deploy(
                quorum,
                transactionManager,
                BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT,
                greeting).send()

        Assert.assertThat<String>(contract.greet().send(), `is`<String>(greeting))
    }

    @Throws(Exception::class)
    fun runPrivateHumanStandardTokenTest(
        sourceNode: Node,
        destNode: Node,
        keyFile: String,
        enclave: Enclave
    ) {

        val quorum = Quorum.build(HttpService(sourceNode.url))

        val classLoader = javaClass.classLoader
        val credentials = WalletUtils.loadCredentials("", File(classLoader.getResource(keyFile)!!.file))

        val transactionManager = QuorumTransactionManager(
                quorum,
                credentials,
                sourceNode.publicKeys[0],
                destNode.publicKeys,
                enclave)

        var aliceQty = BigInteger.valueOf(1000000)
        val aliceAddress = sourceNode.address
        val bobAddress = destNode.address

        val contract = HumanStandardToken.deploy(quorum, transactionManager,
                BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT,
                aliceQty, "web3j tokens",
                BigInteger.valueOf(18), "w3j$").send()

        Assert.assertTrue(contract.isValid)

        Assert.assertThat(contract.totalSupply().send(), equalTo<BigInteger>(aliceQty))

        Assert.assertThat(contract.balanceOf(sourceNode.address).send(),
                equalTo<BigInteger>(aliceQty))

        var transferQuantity = BigInteger.valueOf(100000)

        val aliceTransferReceipt = contract.transfer(
                destNode.address, transferQuantity).send()

        val aliceTransferEventValues = contract.getTransferEvents(aliceTransferReceipt)[0]

        Assert.assertThat(aliceTransferEventValues._from,
                equalTo<String>(aliceAddress))
        Assert.assertThat(aliceTransferEventValues._to,
                equalTo<String>(bobAddress))
        Assert.assertThat(aliceTransferEventValues._value,
                equalTo<BigInteger>(transferQuantity))

        aliceQty = aliceQty.subtract(transferQuantity)

        var bobQty = BigInteger.ZERO
        bobQty = bobQty.add(transferQuantity)

        Assert.assertThat(contract.balanceOf(sourceNode.address).send(),
                equalTo<BigInteger>(aliceQty))
        Assert.assertThat(contract.balanceOf(destNode.address).send(),
                equalTo<BigInteger>(bobQty))

        Assert.assertThat(contract.allowance(
                aliceAddress, bobAddress).send(),
                equalTo<BigInteger>(BigInteger.ZERO))

        transferQuantity = BigInteger.valueOf(50)
        val approveReceipt = contract.approve(
                destNode.address, transferQuantity).send()

        val approvalEventValues = contract.getApprovalEvents(approveReceipt)[0]

        Assert.assertThat(approvalEventValues._owner,
                equalTo<String>(aliceAddress))
        Assert.assertThat(approvalEventValues._spender,
                equalTo<String>(bobAddress))
        Assert.assertThat(approvalEventValues._value,
                equalTo<BigInteger>(transferQuantity))

        Assert.assertThat(contract.allowance(
                aliceAddress, bobAddress).send(),
                equalTo<BigInteger>(transferQuantity))
    }
}
