package com.chenkh.vchat.server.manager

import com.chenkh.vchat.base.bean.Friend
import com.chenkh.vchat.base.bean.Stranger
import com.chenkh.vchat.base.bean.User
import com.chenkh.vchat.server.dao.Mysql_UserDAO
import com.chenkh.vchat.server.dao.UserDAO
import com.chenkh.vchat.server.exception.PasswordNotCorrect
import com.chenkh.vchat.server.exception.RegiserUserFailedException
import com.chenkh.vchat.server.exception.UserNotFoundException

class UserMgr : UserAccess {
    override var dao: UserDAO? = null

    @Throws(UserNotFoundException::class, PasswordNotCorrect::class)
    override fun getUser(id: Int, password: String?): User? {
        return dao!!.getUser(id, password)
    }

    override fun addFriend(myId: Int, friendId: Int, groupId: Int,
                           noteName: String?): Friend? {
        return dao!!.addFriend(myId, friendId, groupId, noteName)
    }

    override fun deleteFriend(myId: Int, friendId: Int, groupId: Int): Boolean {
        return dao!!.deleteFriend(myId, friendId, groupId)
    }

    override fun modifyFriendNoteName(myId: Int, friendId: Int,
                                      noteName: String?, groupId: Int): Boolean {
        return dao!!.modifyFriendNoteName(myId, friendId, noteName, groupId)
    }

    override fun modifyUser(u: User?): Boolean {
        return dao!!.modifyUser(u)
    }

    @Throws(RegiserUserFailedException::class)
    override fun addUser(u: User?): Int {
        return dao!!.addUser(u)
    }

    override fun queryUser(toId: Int, keyword: String?): List<Stranger?>? {
        return dao!!.queryUser(toId, keyword)
    }

    override fun getStrangerById(id: Int): Stranger? {
        return dao!!.getStranger(id)
    }

    init {
        dao = Mysql_UserDAO()
    }
}