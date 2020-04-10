package com.chenkh.vchat.client.net;

import com.chenkh.vchat.base.bean.Stranger;
import com.chenkh.vchat.base.msg.ChatMsg;
import com.chenkh.vchat.base.msg.OfflineMsg;
import com.chenkh.vchat.base.msg.ServerMsg;
import com.chenkh.vchat.base.msg.both.DeleteFriendMsg;
import com.chenkh.vchat.base.msg.both.UserStateChangeMsg;
import com.chenkh.vchat.base.msg.server.AddFriendSucess;
import com.chenkh.vchat.base.msg.server.LoginResultMsg;
import com.chenkh.vchat.base.msg.server.QueryResultMsg;
import com.chenkh.vchat.base.msg.server.RegisterSucessMsg;


public interface IMsgListener {

    default void onReceivedMsg(ServerMsg serverMsg) {

        Object body = serverMsg.getBody();
        switch (serverMsg.getMsgType()) {
            case LOGIN:
                onReceivedLoginResultMsg((LoginResultMsg) body);
                break;
            case CHAT:
                onReceivedChatMsg((ChatMsg) body);

                break;
            case OFFLINE_MSG:
                onReceivedOfflineMessageMsg((OfflineMsg) body);
                break;
            case STRAGER_MSG:
                onReceivedStrangerMsg((Stranger) body);
                break;
            case QUERY_RESULT:
                onReceivedQueryResultMsg((QueryResultMsg) body);
                break;
            case REGISTER_RESULT:
                onReceivedRegisterResultMsg((RegisterSucessMsg) body);
                break;
            case ADD_FRIEND_RESULT:
                onReceivedAddFriendResultMsg((AddFriendSucess) body);
                break;
            case DELETE_FRIEND_RESULT:
                onReceivedDeleteFriendResultMsg((DeleteFriendMsg) body);
                break;
            case FRIEND_STATE_CHANGED:
                onReceivedFriendStateChangedResultMsg((UserStateChangeMsg) body);
                break;
        }


    }


    default void onReceivedLoginResultMsg(LoginResultMsg loginResultMsg) {

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


}
