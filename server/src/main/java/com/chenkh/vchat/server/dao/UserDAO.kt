package com.chenkh.vchat.server.dao

import com.chenkh.vchat.base.bean.Friend
import com.chenkh.vchat.base.bean.Stranger
import com.chenkh.vchat.base.bean.User
import com.chenkh.vchat.server.exception.PasswordNotCorrect
import com.chenkh.vchat.server.exception.RegiserUserFailedException
import com.chenkh.vchat.server.exception.UserNotFoundException

interface UserDAO {
    /**
     * 此方法用于得到指定ID
     * 的用户，如果用户名没找到，跑出UserNotFoundException异常
     * 如果密码不正确，抛出PasswrodNotCorrect异常
     * 如果都正确，返回一个User
     * @param id 要得到用户的ID
     * @param password 用户密码
     * @return 如果出现异常，则User 为空，
     * @throws UserNotFoundException 用户名没找到时抛出
     * @throws PasswordNotCorrect 密码不正确时抛出
     */
    @Throws(UserNotFoundException::class, PasswordNotCorrect::class)
    fun getUser(id: Int, password: String?): User?

    /**
     * 在指定id的用户下添加指定组的指定好友
     * @param myId 主ID
     * @param friendId 好友ID
     * @param groupid 分组id
     * @return 是否添加成功
     */
    fun addFriend(myId: Int, friendId: Int, groupid: Int, noteName: String?): Friend?

    /**
     * 删除指定id用户的下的指定好友
     * @param myId 主ID
     * @param friendId 好友ID
     * @return 是否是否删除成功
     */
    fun deleteFriend(myId: Int, friendId: Int, groupid: Int): Boolean

    /**
     * 修改好友的昵称（备注名称）
     * @param myId 主Id
     * @param friendId 好友Id
     * @param noteName 要修改的昵称
     * @return 是否修改成功
     */
    fun modifyFriendNoteName(myId: Int, friendId: Int, noteName: String?, groupId: Int): Boolean

    /**
     * 通过传入的User对象，修改用户资料,只会修改用户名，性别，地址和电话
     * @param u 已经修改过的User
     * @return 是否修改成功
     */
    fun modifyUser(u: User?): Boolean

    /**
     * 注册用户，如果失败，抛出注册失败异常
     * @param u 要注册的用户对象
     * @return 注册成功后的Id号
     * @throws RegiserUserFailedException 注册失败后抛出的异常
     */
    @Throws(RegiserUserFailedException::class)
    fun addUser(u: User?): Int
    fun queryUser(toId: Int, keyword: String?): List<Stranger?>?
    fun getStranger(id: Int): Stranger?
}