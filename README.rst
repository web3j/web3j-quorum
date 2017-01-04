.. To build this file locally ensure docutils Python package is installed and run:
   $ rst2html.py README.rst README.html

web3j-quorum: Java integration with Quorum
==========================================

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
     <version>0.1</version>
   </dependency>

Gradle
------

Java 8:

.. code-block:: groovy

   compile ('org.web3j:quorum:0.1')


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
