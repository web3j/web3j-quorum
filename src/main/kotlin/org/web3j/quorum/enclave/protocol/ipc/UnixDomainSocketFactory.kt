package org.web3j.quorum.enclave.protocol.ipc


import java.io.File
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress
import javax.net.SocketFactory
import jnr.unixsocket.UnixSocket
import jnr.unixsocket.UnixSocketAddress
import jnr.unixsocket.UnixSocketChannel

/** Impersonate TCP-style SocketFactory over UNIX domain sockets. */
class UnixDomainSocketFactory: SocketFactory() {
    lateinit var path: File

    fun UnixDomainSocketFactory(path: File) {
        this.path = path
    }

    @Throws(IOException::class)
    private fun createUnixDomainSocket(): Socket {
        val channel = UnixSocketChannel.open()

        return object : UnixSocket(channel) {
            private var inetSocketAddress: InetSocketAddress? = null

            @Throws(IOException::class)
            override fun connect(endpoint: SocketAddress) {
                connect(endpoint, Integer.valueOf(0))
            }

            @Throws(IOException::class)
            override fun connect(endpoint: SocketAddress, timeout: Int?) {
                this.inetSocketAddress = endpoint as InetSocketAddress
                super.connect(UnixSocketAddress(path), timeout)
            }

            override fun getInetAddress(): InetAddress {
                return inetSocketAddress!!.address // TODO(jwilson): fake the remote address?
            }
        }
    }

    @Throws(IOException::class)
    override fun createSocket(): Socket {
        return createUnixDomainSocket()
    }

    @Throws(IOException::class)
    override fun createSocket(host: String, port: Int): Socket {
        return createUnixDomainSocket()
    }

    @Throws(IOException::class)
    override fun createSocket(
            host: String, port: Int, localHost: InetAddress, localPort: Int): Socket {
        return createUnixDomainSocket()
    }

    @Throws(IOException::class)
    override fun createSocket(host: InetAddress, port: Int): Socket {
        return createUnixDomainSocket()
    }

    @Throws(IOException::class)
    override fun createSocket(
            address: InetAddress, port: Int, localAddress: InetAddress, localPort: Int): Socket {
        return createUnixDomainSocket()
    }
}
