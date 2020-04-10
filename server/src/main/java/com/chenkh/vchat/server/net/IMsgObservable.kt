package com.chenkh.vchat.server.net

interface IMsgObservable {
    fun addMsgListener(msgListener: IMsgListener)
    fun removeMsgListener(msgListener: IMsgListener)
}