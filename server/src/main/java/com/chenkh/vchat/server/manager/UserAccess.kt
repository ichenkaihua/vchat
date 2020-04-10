package com.chenkh.vchat.server.manager

import com.chenkh.vchat.base.bean.Friend
import com.chenkh.vchat.base.bean.Stranger
import com.chenkh.vchat.base.bean.User
import com.chenkh.vchat.server.dao.UserDAO
import com.chenkh.vchat.server.exception.PasswordNotCorrect
import com.chenkh.vchat.server.exception.RegiserUserFailedException
import com.chenkh.vchat.server.exception.UserNotFoundException

interface UserAccess {
    /**
     * 得到用户信息，包括组和朋友,如果用户不存在或者密码不正确，将返回null
     *
     * @param id
     * 用户id
     * @param password
     * 用户密码
     * @return
     * @throws PasswordNotCorrect 当密码不正确时抛出
     * @throws UserNotFoundException  当用户名不存在时抛出
     */
    @Throws(UserNotFoundException::class, PasswordNotCorrect::class)
    fun getUser(id: Int, password: String?): User?

    /**
     * 将指定的朋友添加到指定的用户中
     *
     * @param myId
     * 主ID，即
     * @param friendId
     * 好友id
     * @return 如果添加成功，返回Friend，否则返回null
     */
    fun addFriend(myId: Int, friendId: Int, groupId: Int, noteName: String?): Friend?

    /**
     * 从指定的朋友中删除指定的好友
     *
     * @param myId
     * 主ID，即
     * @param friendId
     * 好友id
     * @return 如果删除成功，返回true，否则返回false
     */
    fun deleteFriend(myId: Int, friendId: Int, groupId: Int): Boolean

    /**
     * 指定用户修改指定朋友的备注名称
     *
     * @param myId
     * 主Id
     * @param friendId
     * 朋友ID
     * @param noteName
     * 要修改的名字
     * @return 修改成功则返回true,否则返回false
     */
    fun modifyFriendNoteName(myId: Int, friendId: Int, noteName: String?, groupId: Int): Boolean

    /**
     * 用户修改资料
     *
     * @param u
     * 用户资料
     * @return 修改成功，返回true，否则返回false
     */
    fun modifyUser(u: User?): Boolean
    var dao: UserDAO?

    @Throws(RegiserUserFailedException::class)
    fun addUser(u: User?): Int
    fun queryUser(toId: Int, keyword: String?): List<Stranger?>?
    fun getStrangerById(id: Int): Stranger?
}