package com.chenkh.vchat.server

import com.chenkh.vchat.server.coder.JsonDecoder
import com.chenkh.vchat.server.coder.JsonEncoder
import com.chenkh.vchat.server.manager.MsgMgr
import com.chenkh.vchat.server.net.IServer
import java.io.IOException
import java.util.function.Supplier



object AppServer {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) { //IServer server = new AioServer();
        //val server = IServer.createAioServer(()=> JsonEncoder }) { JsonDecoder() }
        //IServer.createAioServer(Supplier { ret JsonDecoder }, Supplier { JsonDecoder })
        val server=IServer.createAioServer(JsonEncoder(),JsonDecoder())
        //val server = IServer.createAioServer(()-> JsonDecoder(),()->JsonDecoder())
        MsgMgr(server)
        server.start(2567)
        try {
            Thread.sleep(Int.MAX_VALUE.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}