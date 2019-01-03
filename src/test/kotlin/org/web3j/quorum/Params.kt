package org.web3j.quorum

import okhttp3.OkHttpClient
import org.web3j.protocol.http.HttpService
import org.web3j.quorum.enclave.Constellation
import org.web3j.quorum.enclave.Tessera
import org.web3j.quorum.enclave.protocol.http.EnclaveHttpService
import org.web3j.quorum.enclave.protocol.ipc.EnclaveIpcService
import java.io.File
//import org.web3j.quorum.enclave.protocol.ipc.UnixEnclaveIpcService
import java.util.*

/**
 * Common parameters for unit tests.
 */

// ASCII base 64 encoded payload
val PAYLOAD: String = Base64.getEncoder().encodeToString("message payload1".toByteArray())
val localhost = "http://localhost"


// Tessera Node configuration
val quorum1T = Node(
        "0xed9d02e382b34818e88b88a309c7fe71e65f419d",
        Arrays.asList(
                "/+UuD63zItL1EbjxkKUljMgG8Z1w0AJ8pNOR4iq2yQc="),
        "http://localhost:22001")

val quorum2T = Node(
        "0xca843569e3427144cead5e4d5999a3d0ccf92b8e",
        Arrays.asList(
                "yGcjkFyZklTTXrn8+WIkYwicA2EGBn9wZFkctAad4X0="),
        "http://localhost:22002")
val quorum3T = Node(
        "0x0fbdc686b912d7722dc86510934589e0aaf3b55a",
        Arrays.asList(
                "jP4f+k/IbJvGyh0LklWoea2jQfmLwV53m9XoHVS4NSU="),
        "http://localhost:22003")

val quorum4T = Node(
        "0x9186eb3d20cbd1f5f992a950d808c4495153abd5",
        Arrays.asList(
                "giizjhZQM6peq52O7icVFxdTmTYinQSUsvyhXzgZqkE="),
        "http://localhost:22004")

val nodesT = Arrays.asList(
        quorum1T, quorum2T, quorum3T, quorum4T)
val storeRawPort = "8090"
val quorumTessera = Quorum.build(HttpService(quorum1T.url))
val tessera = Arrays.asList(Tessera(EnclaveHttpService(localhost, 8090), quorumTessera),
        Tessera(EnclaveHttpService(localhost, 8091),  Quorum.build(HttpService(quorum2T.url))),
        Tessera(EnclaveHttpService(localhost, 8092),  Quorum.build(HttpService(quorum3T.url))),
        Tessera(EnclaveHttpService(localhost, 8093),  Quorum.build(HttpService(quorum4T.url))))
val upCheckTessera = Tessera(EnclaveHttpService(localhost, 8080), quorumTessera)


// Constellation configuration parameters
private val quorum1C = Node(
        "0xed9d02e382b34818e88b88a309c7fe71e65f419d",
        Arrays.asList(
                "BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo="),
        "http://localhost:22001")

private val quorum2C = Node(
        "0xca843569e3427144cead5e4d5999a3d0ccf92b8e",
        Arrays.asList(
                "QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc="),
        "http://localhost:22002")

private val quorum3C = Node(
        "0x0fbdc686b912d7722dc86510934589e0aaf3b55a",
        Arrays.asList(
                "1iTZde/ndBHvzhcl7V68x44Vx7pl8nwx9LqnM/AfJUg="),
        "http://localhost:22003")

private val quorum4C = Node(
        "0x9186eb3d20cbd1f5f992a950d808c4495153abd5",
        Arrays.asList(
                "oNspPPgszVUFw0qmGFfWwh1uxVUXgvBxleXORHj07g8="),
        "http://localhost:22004")

val nodesC = Arrays.asList(
        quorum1C, quorum2C, quorum3C, quorum4C)

val constellationIpcPath1 = "/Users/sebastianraba/Desktop/work/web3js-quorum/constellation/data/constellation.ipc"
val constellationIpcPath2 = "/Users/sebastianraba/Desktop/work/web3js-quorum/constellation/data1/constellation.ipc"
val constellationIpcPath3 = "/Users/sebastianraba/Desktop/work/web3js-quorum/constellation/data2/constellation.ipc"
val constellationIpcPath4 = "/Users/sebastianraba/Desktop/work/web3js-quorum/constellation/data3/constellation.ipc"

val client = OkHttpClient.Builder()
        .socketFactory(UnixDomainSocketFactory(File(constellationIpcPath1)))
        .build()
val client1 = OkHttpClient.Builder()
        .socketFactory(UnixDomainSocketFactory(File(constellationIpcPath2)))
        .build()

val client2 = OkHttpClient.Builder()
        .socketFactory(UnixDomainSocketFactory(File(constellationIpcPath3)))
        .build()
val client3 = OkHttpClient.Builder()
        .socketFactory(UnixDomainSocketFactory(File(constellationIpcPath4)))
        .build()
val quorumConstellation = Quorum.build(HttpService(quorum1C.url))
val constellation = Arrays.asList(Constellation(EnclaveIpcService("http://localhost", 9020, client), Quorum.build(HttpService(quorum1C.url))),
        Constellation(EnclaveIpcService("http://localhost", 9020, client), Quorum.build(HttpService(quorum1C.url))))

//val constellation = Arrays.asList(Constellation(UnixEnclaveIpcService(constellationIpcPath1), Quorum.build(HttpService(quorum1C.url))),
//        Constellation(UnixEnclaveIpcService(constellationIpcPath2), Quorum.build(HttpService(quorum2C.url))),
//        Constellation(UnixEnclaveIpcService(constellationIpcPath3), Quorum.build(HttpService(quorum3C.url))),
//        Constellation(UnixEnclaveIpcService(constellationIpcPath4), Quorum.build(HttpService(quorum4C.url))))
// ASCII base 64 encoded public keys for our transaction managers
const val TM1_PUBLIC_KEY = "BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo="
const val TM2_PUBLIC_KEY = "QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc="
