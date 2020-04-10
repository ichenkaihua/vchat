package com.chenkh.vchat.server.net.bio

import com.chenkh.vchat.base.IDecoder
import com.chenkh.vchat.base.IEncoder
import com.chenkh.vchat.base.msg.ClientMsg
import com.chenkh.vchat.server.net.AbstractServer
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedInputStream
import java.io.IOException
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.SocketException
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class BioServer(decoder: IDecoder?, encoder: IEncoder?) : AbstractServer(decoder!!, encoder!!) {
    private var serverSocket: ServerSocket? = null
    private var running = false
    private val executorService: ExecutorService = Executors.newCachedThreadPool()
    companion object{
        val log: Logger =LoggerFactory.getLogger(BioServer::class.java)
    }
    @Throws(IOException::class)
    override fun start(port: Int) {
        if (serverSocket == null) {
            serverSocket = ServerSocket()

        }
        serverSocket!!.bind(InetSocketAddress(port))
        running = true
        log.debug("服务器开启成功，循环接收连接")
        while (running) {
            val socket = serverSocket!!.accept()
            log.debug("接收到一个新连接")
            val channel = BioChannel(socket, encoder)
            publishConnectEvent(channel, true, null)
            executorService.submit {
                var bufferedInputStream: BufferedInputStream? = null
                try {
                    bufferedInputStream = BufferedInputStream(socket.getInputStream())
                    while (running) {
                        val bytes = ByteArray(1024)
                        try {
                            val read = bufferedInputStream.read(bytes)
                            if (read == -1) {
                                log.debug("读取到的字节为-1")
                                publishConnectEvent(channel, false, null)
                                break
                            } else {
                                log.debug("成功从客户端读取到{}个字节", read)
                                executorService.submit {
                                    val clientMsg = decoder.decode(Arrays.copyOf(bytes, read)) as ClientMsg<*>
                                    publishMsgEvent(channel, clientMsg)
                                }
                            }
                        } catch (e: SocketException) {
                            publishConnectEvent(channel, false, e)
                            bufferedInputStream.close()
                            return@submit
                        }
                    }
                } catch (e: IOException) {
                    BioServer.log.error("读取发生exception", e)
                    e.printStackTrace()
                }
            }
        }
    }

    override val isStarted: Boolean
        get() = serverSocket != null && !serverSocket!!.isClosed

    @Throws(IOException::class)
    override fun stop() {
        serverSocket!!.close()
        running = false
    }

}