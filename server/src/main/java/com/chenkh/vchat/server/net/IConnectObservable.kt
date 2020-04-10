package com.chenkh.vchat.server.net

interface IConnectObservable {
    fun addConnectListener(listener: IChannelConnectListener)
    fun removeConnectListener(listener: IChannelConnectListener)
}