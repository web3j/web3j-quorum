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
- Auto-generation of Quorum smart contract wrappers to create, deploy, transact with and call smart
  contracts from native Java code, with full transaction privacy support

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
     <version>0.2.0</version>
   </dependency>

Gradle
------

Java 8:

.. code-block:: groovy

   compile ('org.web3j:quorum:0.2.0')


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
the same host as web3j. To connect simply use *IpcService* instead of *HttpService* when you
create your service:

.. code-block:: java

   Quorum quorum = Quorum.build(new IpcService("/path/to/socketfile"));
   ...


Smart Contract Wrappers
-----------------------

You can generate Quorum compatible smart contract wrappers using the
`QuorumFunctionWrapperGenerator <https://github.com/web3j/quorum/blob/master/src/main/java/org/web3j/quorum/codegen/QuorumFunctionWrapperGenerator.java>`_:

.. code-block:: bash

   org.web3j.quorum.codegen.QuorumFunctionWrapperGenerator /path/to/<smart-contract>.bin /path/to/<smart-contract>.abi -o /path/to/src/main/java -p com.your.organisation.name

Where the *bin* and *abi* are obtained as per
`compiling-solidity <http://docs.web3j.io/smart_contracts.html#compiling-solidity-source-code>`_
or via `Cakeshop <https://github.com/jpmorganchase/cakeshop/wiki/Contracts>`_.

These wrappers are similar to the web3j smart contract wrappers with the exception that the
transactions are signed by the Quorum nodes rather then by web3j. They also support the privateFor
field on transactions.

See the `web3j documentation <http://docs.web3j.io/smart_contracts.html>`_ for a detailed overview
of smart contracts and web3j.

