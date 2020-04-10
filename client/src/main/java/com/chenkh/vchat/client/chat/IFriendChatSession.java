package com.chenkh.vchat.client.chat;

import com.chenkh.vchat.base.bean.Friend;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

/**
 * 好友session
 */
public interface IFriendChatSession  extends IChatSession{


    Friend getFriend();

   default void addFriendTextMsg(String content,Timestamp timestamp){
       getFriendMsgs().add(new FriendMsg(false,content,timestamp));

   };


    List<FriendMsg> getFriendMsgs();

    @Getter
    @AllArgsConstructor
     class FriendMsg{
        boolean isSelfMsg;
        String content;
        Timestamp timestamp;
    }


}
