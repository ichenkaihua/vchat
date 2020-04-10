package com.chenkh.vchat.server.manager

import com.chenkh.vchat.base.bean.VState
import com.chenkh.vchat.server.net.IChannel
import com.chenkh.vchat.server.pojo.Client

/**
 * 客户端管理类要实现的借口
 * @author Administrator
 */
interface IClientManager {
    /**
     * 通过指定的id得到用户
     * @param id 指定用户id
     * @return 如果不存在，则返回null
     */
    fun getClient(id: Int): Client

    /**
     * 增加客户端
     * @param client 客户端
     */
    fun addClient(id: Int, client: Client)

    /**
     * 通过id和套接字添加客户端
     * @param id 用户id
     * @param channel 套接字
     */
    fun addClient(id: Int, channel: IChannel?, state: VState?, friendIds: MutableSet<Int>)
    fun isContain(id: Int): Boolean
    fun getState(id: Int): VState
    fun isFriend(mainId: Int, checkId: Int): Boolean
    fun addFriend(mainId: Int, friendId: Int)
    fun getFriends(id: Int): Set<Int>
    fun removeCient(socket: IChannel): Int
}