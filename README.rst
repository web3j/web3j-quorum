.. To build this file locally ensure docutils Python package is installed and run:
   $ rst2html.py README.rst README.html

web3j-quorum: Java integration library for Quorum
=================================================

.. image:: https://travis-ci.org/web3j/web3j-quorum.svg?branch=master
       :target: https://travis-ci.org/web3j/web3j-quorum

web3j-quorum is an extension to `web3j <https://github.com/web3j/web3j>`_ providing support for
`JP Morgan's Quorum <https://github.com/jpmorganchase/quorum>`_ API.

web3j is a lightweight, reactive, type safe Java library for integrating with clients
(nodes) on distributed ledger or blockchain networks.

For further information on web3j, please refer to the
`main project page <https://github.com/web3j/web3j>`_ and the documentation at
`Read the Docs <http://docs.web3j.io>`_.


Features
--------

- Support for Quorum's private transactions through private transaction manager
- Ability to send **signed** private transactions
- Works out the box with web3j's
  `smart contract wrappers <https://docs.web3j.io/smart_contracts/#solidity-smart-contract-wrappers>`_

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
     <version>4.8.3</version>
   </dependency>

Gradle
------

Java 8:

.. code-block:: groovy

   compile ('org.web3j:quorum:4.8.3')


Run Quorum
----------

See instructions as per the `Quorum project page <https://github.com/jpmorganchase/quorum>`_


Start sending requests
----------------------

To send synchronous requests:

.. code-block:: java

   Quorum quorum = Quorum.build(new HttpService("http://localhost:22001"));
   Web3ClientVersion web3ClientVersion = quorum.web3ClientVersion().send();
   String clientVersion = web3ClientVersion.getWeb3ClientVersion();

To send asynchronous requests:

.. code-block:: java

   Quorum quorum = Quorum.build(new HttpService("http://localhost:22001"));
   Web3ClientVersion web3ClientVersion = quorum.web3ClientVersion().sendAsync().get();
   String clientVersion = web3ClientVersion.getWeb3ClientVersion();

To use an RxJava Observable:

.. code-block:: java

   Quorum quorum = Quorum.build(new HttpService("http://localhost:22001"));
   quorum.web3ClientVersion().observable().subscribe(x -> {
       String clientVersion = x.getWeb3ClientVersion();
       ...
   });



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

`Smart contract wrappers <https://docs.web3j.io/smart_contracts/#solidity-smart-contract-wrappers>`_
generated using web3j 2.0+ work out the box with with web3j-quorum.

The only difference is that you'll need to use the
`Quorum ClientTransactionManager <https://github.com/web3j/quorum/tree/master/src/main/java/org/web3j/quorum/tx/ClientTransactionManager.java>`_:

.. code-block:: java

   QuorumTransactionManager transactionManager = new QuorumTransactionManager(
           web3j, "0x<from-address>", Arrays.asList("<privateFor-public-key>", ...);
   YourSmartContract contract = YourSmartContract.deploy(
       <web3j>, <transactionManager>, GAS_PRICE, GAS_LIMIT,
       <param1>, ..., <paramN>).send();


These wrappers are similar to the web3j smart contract wrappers with the exception that the
transactions are signed by the Quorum nodes rather then by web3j. They also support the privateFor
field on transactions.

See the `web3j documentation <https://docs.web3j.io/smart_contracts/>`_ for a detailed overview
of smart contracts and web3j.


Sending Raw Private Transactions
--------------------------------
web3j supports sending raw private transactions through a connection to Quorum Transaction Managers. Code examples

Connection to Tessera via HTTP
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

   Credentials credentials = ...
   //connect to quorum node via http or ipc as described above
   Quorum quorum = ... 

   EnclaveService enclaveService = new EnclaveService("http://TESSERA_THIRD_PARTY_URL", TESSERA_THIRD_PARTY_PORT, httpClient);
   Enclave enclave = new Tessera(enclaveService, quorum);

   QuorumTransactionManager qrtxm = new QuorumTransactionManager(
       quorum, credentials, TM_FROM_KEY, Arrays.asList(TM_TO_KEY_ARRAY),
       enclave,
       30,     // Retry times
       2000);  // Sleep

Connection to Tessera via IPC
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

   Credentials credentials = ...
   //connect to quorum node via http or ipc as described above
   Quorum quorum = ... 
   
   //build http client that supports ipc connection
   UnixDomainSocketFactory socketFactory = new UnixDomainSocketFactory(new File("TESSERA_IPC_PATH"));
        OkHttpClient client = new OkHttpClient.Builder()
                .socketFactory(socketFactory)
                .build();

   EnclaveService enclaveService = new EnclaveService("http://localhost", TESSERA_THIRD_PARTY_PORT, client);
   Enclave enclave = new Tessera(enclaveService, quorum);

   QuorumTransactionManager qrtxm = new QuorumTransactionManager(
       quorum, credentials, TM_FROM_KEY, Arrays.asList(TM_TO_KEY_ARRAY),
       enclave,
       30,     // Retry times
       2000);  // Sleep
       
Connection to Constellation via IPC
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

   Credentials credentials = ...
   //connect to quorum node via http or ipc as described above
   Quorum quorum = ... 
   
   //build http client that supports ipc connection
   UnixDomainSocketFactory socketFactory = new UnixDomainSocketFactory(new File("CONSTELLATION_IPC_PATH"));
        OkHttpClient client = new OkHttpClient.Builder()
                .socketFactory(socketFactory)
                .build();

   EnclaveService enclaveService = new EnclaveService("http://localhost", CONSTELLATION_THIRD_PARTY_PORT, client);
   Enclave enclave = new Constellation(enclaveService, quorum);

   QuorumTransactionManager qrtxm = new QuorumTransactionManager(
       quorum, credentials, TM_FROM_KEY, Arrays.asList(TM_TO_KEY_ARRAY),
       enclave,
       30,     // Retry times
       2000);  // Sleep
       
Using the QuorumTransactionManager with Smart Contract Wrappers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

   YourSmartContract.deploy(quorum,
       qrtxm,
       GAS_PRICE, GAS_LIMIT,
       <param1>, ..., <paramN>).send();
       
       
       
Using the QuorumTransactionManager alone
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Using a single QuorumTransactionManager method ``signAndSend``
	
.. code-block:: java
	
	RawTransaction rawTransaction = ...
	EthSendTransaction ethSendTransaction = qrtxm.signAndSend(rawTransaction);
	
Using multiple exposed QuorumTransactionManager methods (``storeRawRequest, sign, sendRaw``)

.. code-block:: java

	//send raw bytecode to QuorumTranasctionManager
	SendResponse storeRawResponse = qrtxm.storeRawRequest(HEX_ENCODED_SMARTCONTRACT_BYTECODE, TM_FROM_KEY, Arrays.asList(TM_TO_KEY_ARRAY));
	String tesseraTxHash = Numeric.toHexString(Base64.getDecoder().decode(storeRawResponse.getKey()));
	
	//create raw transaction with hash returned from QuorumTransactionManager
	RawTransaction rawTransaction = ...
	
	//sign raw transaction
	String signedTx = qrtxm.sign(rawTransaction);
	
	//send signed raw transaction to quorum node
	EthSendTransaction ethSendTransaction = qrtxm.sendRaw(signedTx, Arrays.asList(TM_TO_KEY_ARRAY));
	
Retrieving a private transaction payload with Enclave ``receive`` method

.. code-block:: java

	String payload = enclave.receiveRequest(tesseraTxHash, TM_TO_KEY);
	
Full sample code
~~~~~~~~~~~~~~~~

`Sample code <https://github.com/jpmorganchase/quorum-examples/tree/master/examples/7nodes/samples/send-private-txn-java>`_ for sending raw private transactions via smart contract, QuorumTransactionManager and Enclave


Using web3j RawTransactionManager
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

   // Raw txn
   RawTransactionManager qrtxm = new RawTransactionManager(
         quorum,
         credentials,
         30,     // Retry times
         2000);  // Sleep

   YourSmartContract.deploy(quorum,
         qrtxm,
         GAS_PRICE, GAS_LIMIT,
         <param1>, ..., <paramN>).send();
