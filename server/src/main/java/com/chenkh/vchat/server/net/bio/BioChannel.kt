package com.chenkh.vchat.server.net.bio

import com.chenkh.vchat.base.IEncoder
import com.chenkh.vchat.base.msg.ServerMsg
import com.chenkh.vchat.server.net.IChannel
import java.io.BufferedOutputStream
import java.io.IOException
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class BioChannel(private val socket: Socket, private val encoder: IEncoder) : IChannel {
    private var executorService: ExecutorService = Executors.newCachedThreadPool()
    override fun sendMsg(serverMsg: ServerMsg<*>?): Future<Int?>? {
        return executorService.submit<Int?> {
            val bytes = encoder.encode(serverMsg)
            try {
                val bufferedOutputStream = BufferedOutputStream(socket.getOutputStream())
                bufferedOutputStream.write(bytes)
                bufferedOutputStream.flush()
                return@submit bytes.size
            } catch (e: IOException) {
                e.printStackTrace()
            }
            0
        }
    }

    override val isOpen: Boolean
        get() = !socket.isClosed

    @Throws(IOException::class)
    override fun close() {
        socket.close()
    }

}