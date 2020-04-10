package com.chenkh.vchat.server.net

import com.chenkh.vchat.base.IDecoder
import com.chenkh.vchat.base.IEncoder
import com.chenkh.vchat.server.coder.JsonEncoder
import com.chenkh.vchat.server.net.aio.AioServer
import com.chenkh.vchat.server.net.bio.BioServer
import java.io.IOException
import java.util.function.Supplier

interface IServer : IConnectObservable, IMsgObservable {
    @Throws(IOException::class)
    fun start(port: Int)

    val isStarted: Boolean
    @Throws(IOException::class)
    fun stop()

    companion object {
        fun createAioServer(encoder:IEncoder,decoder:IDecoder): IServer {
            return AioServer(decoder,encoder);
        }

        fun createBioServer(encoder: IEncoder,decoder: IDecoder): IServer {
            return BioServer(decoder,encoder)
        }
    }
}