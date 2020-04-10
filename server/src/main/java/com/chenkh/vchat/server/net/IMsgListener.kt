package com.chenkh.vchat.server.net

import com.chenkh.vchat.base.msg.ClientMsg

interface IMsgListener {
    fun onReceivedMsg(channel: IChannel, serverMsg: ClientMsg<*>) /* default void onReceivedLoginResultMsg(LoginResultMsg loginResultMsg) {

    }

    default void onReceivedChatMsg(ChatMsg chatMsgBody) {

    }

    default void onReceivedOfflineMessageMsg(OfflineMsg offlineMsgMsgBody) {

    }


    default void onReceivedStrangerMsg(Stranger strangerMsgBody) {

    }

    default void onReceivedQueryResultMsg(QueryResultMsg queryResultMsgMsgBody) {

    }

    default void onReceivedRegisterResultMsg(RegisterSucessMsg registerSucessMsgMsgBody) {

    }


    default void onReceivedAddFriendResultMsg(AddFriendSucess addFriendSucessMsgBody) {

    }

    default void onReceivedDeleteFriendResultMsg(DeleteFriendMsg deleteFriendMsgMsgBody) {

    }

    default void onReceivedFriendStateChangedResultMsg(UserStateChangeMsg friendStateChangedMsgBody) {

    }

    default void onReceivedUnKnowMsg(String command, String body) {

    }
*/
}