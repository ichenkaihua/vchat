package com.chenkh.vchat.server.manager

import com.chenkh.vchat.base.bean.VState
import com.chenkh.vchat.server.net.IChannel
import com.chenkh.vchat.server.pojo.Client
import java.util.*

/**
 * 客户端管理类，负责对客户端的增加，删除，管理
 *
 * @author Administrator
 */
class ClientMgr : IClientManager {
    private val clients: MutableMap<Int, Client> = HashMap()
    override fun getClient(id: Int): Client {
        return clients[id]
    }

    override fun addClient(id: Int, client: Client) {
        clients[id] = client
    }

    override fun isContain(id: Int): Boolean {
        return clients.containsKey(id)
    }

    override fun addClient(id: Int, channel: IChannel?,
                           state: VState?, friendIds: MutableSet<Int>) {
        val client = Client(id, channel, state!!, friendIds)
        this.addClient(id, client)
    }

    override fun getState(id: Int): VState {
        var state: VState? = null
        val c = clients[id]
        state = c!!.state
        return state
    }

    override fun isFriend(mainId: Int, checkId: Int): Boolean {
        if (isContain(mainId)) {
            val c = clients[mainId]
            return c!!.isFriend(mainId)
        }
        return false
    }

    override fun getFriends(id: Int): Set<Int> {
        val friends: MutableSet<Int> = HashSet()
        for (iid in clients.keys) {
            val c = clients[iid]
            if (c!!.isFriend(id)) {
                friends.add(iid)
            }
        }
        return friends
    }

    override fun removeCient(socket: IChannel): Int {
        for (id in clients.keys) {
            val c = clients[id]
            if (c!!.socket == socket) {
                clients.remove(id)
                println("移除完成")
                return id
            }
        }
        return -1
    }

    override fun addFriend(mainId: Int, friendId: Int) {
        val c = clients[mainId]
        c!!.addFriend(friendId)
    }
}