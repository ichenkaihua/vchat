package com.chenkh.vchat.server.pojo

import com.chenkh.vchat.base.bean.VState
import com.chenkh.vchat.server.net.IChannel
import java.io.IOException

/**
 * 客户端类，为了适应操作，已经重写hashCode()和equals(Object)方法
 *
 * @author Administrator
 */
class Client
/**
 * 此构造方法会初始化id和socket，并且不可变， ip为socket的远程ip地址和端口
 *
 * @param id
 * 用户id号
 * @param socket
 * 用户地址的异步套接字
 */(val id: Int, val socket: IChannel?, var state: VState,
    private val friendIds: MutableSet<Int>) {
    fun addFriend(friendId: Int) {
        friendIds.add(friendId)
    }

    fun isFriend(id: Int): Boolean {
        return friendIds.contains(id)
    }

    val isOpen: Boolean
        get() = socket?.isOpen ?: true

    fun close() {
        if (!socket!!.isOpen) return
        try {
            socket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}