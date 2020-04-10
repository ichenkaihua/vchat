package com.chenkh.vchat.server.net

import com.chenkh.vchat.base.IDecoder
import com.chenkh.vchat.base.IEncoder
import com.chenkh.vchat.base.msg.ClientMsg
import com.chenkh.vchat.server.coder.JsonDecoder
import com.chenkh.vchat.server.coder.JsonEncoder
import java.util.concurrent.CopyOnWriteArrayList

abstract class AbstractServer(decoder: IDecoder, encoder: IEncoder) : IServer {
    private var connectListeners: MutableList<IChannelConnectListener> = CopyOnWriteArrayList()
    private var msgListeners: MutableList<IMsgListener> = CopyOnWriteArrayList()
    @JvmField
    protected var decoder: IDecoder = JsonDecoder()
    @JvmField
    protected var encoder: IEncoder = JsonEncoder()
    override fun addConnectListener(listener: IChannelConnectListener) {
        connectListeners.add(listener)
    }

    override fun removeConnectListener(listener: IChannelConnectListener) {
        connectListeners.remove(listener)
    }

    override fun addMsgListener(msgListener: IMsgListener) {
        msgListeners.add(msgListener)
    }

    override fun removeMsgListener(msgListener: IMsgListener) {
        msgListeners.remove(msgListener)
    }

    protected fun publishConnectEvent(channel: IChannel, success: Boolean, exc: Throwable?) {
        for (connectListener in connectListeners) {
            if (success) {
                connectListener!!.channelConnected(channel)
            } else {
                connectListener!!.channelDisconnect(channel, exc)
            }
        }
    }

    protected fun publishMsgEvent(channel: IChannel, clientMsg: ClientMsg<*>) {
        for (msgListener in msgListeners) {
            msgListener.onReceivedMsg(channel, clientMsg)
        }
    }

    init {
        this.decoder = decoder
        this.encoder = encoder
    }
}