.. To build this file locally ensure docutils Python package is installed and run:
   $ rst2html.py README.rst README.html

web3j-quorum: Java integration library for Quorum
=================================================

web3j-quorum is an extension to `web3j <https://github.com/web3j/web3j>`_ providing support for
`JP Morgan's Quorum <https://github.com/jpmorganchase/quorum>`_ API.

web3j is a lightweight, reactive, type safe Java library for integrating with clients
(nodes) on distributed ledger or blockchain networks.

For further information on web3j, please refer to the
`main project page <https://github.com/web3j/web3j>`_ and the documentation at
`Read the Docs <http://docs.web3j.io>`_.


Features
--------

- Support for Quorum's private transactions
- `QuorumChain API <https://github.com/jpmorganchase/quorum/blob/master/docs/api.md#quorumchain-apis>`_
  implementation
- Works out the box with web3j's
  `smart contract wrappers <http://docs.web3j.io/smart_contracts.html#solidity-smart-contract-wrappers>`_

Getting started
---------------

Add the relevant dependency to your project:

Maven
-----

Java 8:

.. code-block:: xml

   <dependency>
     <groupId>org.web3j</groupId>
     <artifactId>quorum</artifactId>
     <version>0.7.0</version>
   </dependency>

Gradle
------

Java 8:

.. code-block:: groovy

   compile ('org.web3j:quorum:0.7.0')


Run Quorum
----------

See instructions as per the `Quorum project page <https://github.com/jpmorganchase/quorum>`_


Start sending requests
----------------------

To send asynchronous requests using a Future:

.. code-block:: java

   Quorum quorum = Quorum.build(new HttpService("http://localhost:22001"));
   QuorumNodeInfo quorumNodeInfo = quorum.quorumNodeInfo().sendAsync().get();
   String voteAccount = quorumNodeInfo.getNodeInfo().getVoteAccount();


To use an RxJava Observable:

.. code-block:: java

   Quorum quorum = Quorum.build(new HttpService("http://localhost:22001"));
   quorum.quorumNodeInfo().observable().subscribe(x -> {
       String voteAccount = x.getNodeInfo().getVoteAccount();
       ...
   });

To send synchronous requests:

.. code-block:: java

   Quorum quorum = Quorum.build(new HttpService("http://localhost:22001"));
   QuorumNodeInfo quorumNodeInfo = quorum.quorumNodeInfo().send();
   String voteAccount = quorumNodeInfo.getNodeInfo().getVoteAccount();


IPC
---

web3j also supports fast inter-process communication (IPC) via file sockets to clients running on
the same host as web3j. To connect simply use *UnixIpcService* or *WindowsIpcService* instead of
*HttpService* when you create your service:

.. code-block:: java

   // OS X/Linux/Unix:
   Quorum quorum = Quorum.build(new UnixIpcService("/path/to/socketfile"));
   ...

   // Windows
   Quorum quorum = Quorum.build(new WindowsIpcService("/path/to/namedpipefile"));
   ...


Smart Contract Wrappers
-----------------------

`Smart contract wrappers <http://docs.web3j.io/smart_contracts.html#solidity-smart-contract-wrappers>`_
generated using web3j 2.0+ work out the box with with web3j-quorum.

The only difference is that you'll need to use the
`Quorum ClientTransactionManager <https://github.com/web3j/quorum/tree/master/src/main/java/org/web3j/quorum/tx/ClientTransactionManager.java>`_:

.. code-block:: java

   ClientTransactionManager transactionManager = new ClientTransactionManager(
           web3j, "0x<from-address>", Arrays.asList("<privateFor-public-key>", ...);
   YourSmartContract contract = YourSmartContract.deploy(
       <web3j>, <transactionManager>, GAS_PRICE, GAS_LIMIT,
       <param1>, ..., <paramN>).send();


These wrappers are similar to the web3j smart contract wrappers with the exception that the
transactions are signed by the Quorum nodes rather then by web3j. They also support the privateFor
field on transactions.

See the `web3j documentation <http://docs.web3j.io/smart_contracts.html>`_ for a detailed overview
of smart contracts and web3j.

