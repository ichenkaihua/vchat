package com.chenkh.vchat.server.net

import com.chenkh.vchat.base.bean.MsgType
import com.chenkh.vchat.base.msg.ChatMsg
import com.chenkh.vchat.base.msg.OfflineMsg
import com.chenkh.vchat.base.msg.ServerMsg
import com.chenkh.vchat.base.msg.both.DeleteFriendMsg
import com.chenkh.vchat.base.msg.both.UserStateChangeMsg
import com.chenkh.vchat.base.msg.server.*
import java.io.IOException
import java.util.concurrent.Future

interface IChannel {
    fun sendMsg(serverMsg: ServerMsg<*>?): Future<Int?>?
    fun sendLoginResultMsg(loginResultMsg: LoginResultMsg?): Future<Int?>? {
        return sendMsg(ServerMsg<Any?>(MsgType.Server2Client.LOGIN, loginResultMsg))
    }

    fun sendOfflineMsg(offlineMsg: OfflineMsg?): Future<Int?>? {
        return sendMsg(ServerMsg<Any?>(MsgType.Server2Client.OFFLINE_MSG, offlineMsg))
    }

    fun sendFriendStateChanged(userStateChangeMsg: UserStateChangeMsg?): Future<Int?>? {
        return sendMsg(ServerMsg<Any?>(MsgType.Server2Client.FRIEND_STATE_CHANGED, userStateChangeMsg))
    }

    fun sendQueryResultMsg(queryResultMsg: QueryResultMsg?): Future<Int?>? {
        return sendMsg(ServerMsg<Any?>(MsgType.Server2Client.QUERY_RESULT, queryResultMsg))
    }

    fun sendRegisterSuccessMsg(registerSucessMsg: RegisterSucessMsg?): Future<Int?>? {
        return sendMsg(ServerMsg<Any?>(MsgType.Server2Client.REGISTER_RESULT, registerSucessMsg))
    }

    fun sendAddFriendResultMsg(addFriendSucess: AddFriendSucess?): Future<Int?>? {
        return sendMsg(ServerMsg<Any?>(MsgType.Server2Client.ADD_FRIEND_RESULT, addFriendSucess))
    }

    fun sendDeleteFriendResultMsg(deleteFriendMsg: DeleteFriendMsg?): Future<Int?>? {
        return sendMsg(ServerMsg<Any?>(MsgType.Server2Client.DELETE_FRIEND_RESULT, deleteFriendMsg))
    }

    fun sendChatMsg(chatMsg: ChatMsg?): Future<Int?>? {
        return sendMsg(ServerMsg<Any?>(MsgType.Server2Client.CHAT, chatMsg))
    }

    fun sendStrangerMsg(stranger: StrangerMsg?): Future<Int?>? {
        return sendMsg(ServerMsg<Any?>(MsgType.Server2Client.STRAGER_MSG, stranger))
    }

    val isOpen: Boolean
    @Throws(IOException::class)
    fun close()
}