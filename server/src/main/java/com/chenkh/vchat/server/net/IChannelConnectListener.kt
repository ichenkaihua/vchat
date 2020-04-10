package com.chenkh.vchat.server.net

/**
 * 监听channel连接事件
 */
interface IChannelConnectListener {
    //    void onConnect(IChannel channel,boolean success, Throwable exc);
    fun channelConnected(channel: IChannel)

    fun channelDisconnect(channel: IChannel, exc: Throwable?)
}