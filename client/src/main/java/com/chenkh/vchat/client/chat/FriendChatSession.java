package com.chenkh.vchat.client.chat;

import com.chenkh.vchat.base.bean.Friend;
import com.chenkh.vchat.base.msg.ChatMsg;
import com.chenkh.vchat.client.net.IClient;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FriendChatSession implements IFriendChatSession{
    private IClient client;
    private Friend friend;
    private List<IFriendChatSession.FriendMsg> friendMsgs=new ArrayList<>();

    public FriendChatSession(IClient client,Friend friend) {
        this.client = client;
        this.friend=friend;
    }

    @Override
    public Friend getFriend() {
        return friend;
    }



    @Override
    public List<IFriendChatSession.FriendMsg> getFriendMsgs() {
        return friendMsgs;
    }

    @Override
    public void sendTextMsg(String textContent) {
            client.sendChatMsg(new ChatMsg(-1,friend.getId(),textContent,new Timestamp(System.currentTimeMillis())));
    }


}
