package com.chenkh.vchat.server.net.aio

import com.chenkh.vchat.base.IEncoder
import com.chenkh.vchat.base.msg.ServerMsg
import com.chenkh.vchat.server.net.IChannel
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.util.concurrent.Future

class AioChannel(val socketChannel: AsynchronousSocketChannel, private val encoder: IEncoder) : IChannel {

    override fun sendMsg(serverMsg: ServerMsg<*>?): Future<Int?>? {
        val bytes = encoder.encode(serverMsg)
        val byteBuffer = ByteBuffer.wrap(bytes)
        return socketChannel.write(byteBuffer)
    }

    @Throws(IOException::class)
    override fun close() {
        socketChannel.close()
    }

    override val isOpen: Boolean
        get() = socketChannel.isOpen

}