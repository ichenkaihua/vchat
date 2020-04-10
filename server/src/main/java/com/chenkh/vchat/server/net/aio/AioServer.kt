package com.chenkh.vchat.server.net.aio

import com.chenkh.vchat.base.IDecoder
import com.chenkh.vchat.base.IEncoder
import com.chenkh.vchat.base.msg.ClientMsg
import com.chenkh.vchat.server.net.AbstractServer
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousChannelGroup
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler
import java.util.concurrent.Executors

/**
 * 服务器的总管理者，
 *
 * @author Administrator
 */
class AioServer(decoder: IDecoder?, encoder: IEncoder?) : AbstractServer(decoder!!, encoder!!) {
    private var group: AsynchronousChannelGroup? = null
    private var server: AsynchronousServerSocketChannel? = null
    private var acceptHandler: AcceptHandler? = null

    @Throws(IOException::class)
    override  fun start(port: Int) {
        if (group == null) {
            group = AsynchronousChannelGroup.withCachedThreadPool(
                    Executors.newCachedThreadPool(), 50)
        }


        try {


            //server = AsynchronousServerSocketChannel.open(group)

            server = AsynchronousServerSocketChannel.open().bind(InetSocketAddress("127.0.0.1", port))
            println("server开启成功!")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        server!!.accept<Void?>(null, acceptHandler)
    }

    override val isStarted: Boolean
        get() = server!!.isOpen

    @Throws(IOException::class)
    override fun stop() {
        server!!.close()
    }

    private inner class AcceptHandler : CompletionHandler<AsynchronousSocketChannel, Void?> {
        override fun completed(result: AsynchronousSocketChannel, attachment: Void?) {
            println("一个客户端连接成功,重新注册新连接")
            server!!.accept<Void?>(null, this)
            val bf = ByteBuffer.allocate(1024 * 5)
            val channel = AioChannel(result, encoder)
            result.read(bf, bf, ReadHandler(channel))
            publishConnectEvent(channel, true, null)
        }

        override fun failed(exc: Throwable, attachment: Void?) {
            println("一个客户端尝试连接失败,重新注册新连接")
            server!!.accept<Void?>(null, this)
        }
    }

    private inner class ReadHandler(private val channel: AioChannel) : CompletionHandler<Int, ByteBuffer> {
        override fun completed(result: Int, attachment: ByteBuffer) {
            println("读取到来自客户端的消息--大小:$result")
            if (result <= -1) {
                println("客户端已经关闭")
                try {
                    channel.socketChannel.close()
                    publishConnectEvent(channel, false, null)
                } catch (e: IOException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
            } else {
                val bf = ByteBuffer.allocate(1024 * 5)
                channel.socketChannel.read(bf, bf, this)
                val bytes = attachment.array()
                val decode = decoder.decode(bytes)
                publishMsgEvent(channel, decode as ClientMsg<*>)
            }
        }

        override fun failed(exc: Throwable, attachment: ByteBuffer) {
            println("一个客户端读消息读取失败,读取下一次消息")
            try {
                channel.socketChannel.close()
                publishConnectEvent(channel, false, exc)
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

            /*
			 * ByteBuffer bf = ByteBuffer.allocate(1024); try {
			 * System.out.println(socket.getRemoteAddress()); } catch
			 * (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } socket.read(bf, bf, this);
			 */
        }

    }

    //private Thread writeThread = new Thread(new WriteThread(), "writeThread");
    init {
        acceptHandler = AcceptHandler()
    }
}