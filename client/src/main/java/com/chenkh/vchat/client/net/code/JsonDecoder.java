package com.chenkh.vchat.client.net.code;

import com.chenkh.vchat.base.bean.MsgType;
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
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class JsonDecoder implements IClientDecoder {


    @Override
    public ServerMsg<?> decode2ServerMsg(byte[] bytes) {
        String msgSource = new String(bytes, Charset.forName("utf-8")).trim();

        log.debug("正在解析消息服务器消息:{}",msgSource);

        String cmd = null;

        String body = null;

        if (msgSource.contains(" ")) {
            String[] split = msgSource.split(" ", 2);
            cmd = split[0];
            body = split[1].trim();
        } else {
            cmd = msgSource;
        }


        MsgType.Server2Client msgType = MsgType.Server2Client.valueOf(cmd);
        Class<?> clzz = null;

        switch (msgType) {
            case LOGIN:
                clzz = LoginResultMsg.class;
                break;
            case CHAT:
                clzz = ChatMsg.class;
                break;
            case OFFLINE_MSG:
                clzz = OfflineMsg.class;
                break;
            case STRAGER_MSG:
                clzz = Stranger.class;
                break;
            case QUERY_RESULT:
                clzz = QueryResultMsg.class;
                break;
            case REGISTER_RESULT:
                clzz = RegisterSucessMsg.class;
                break;
            case ADD_FRIEND_RESULT:

                clzz = AddFriendSucess.class;

                break;
            case DELETE_FRIEND_RESULT:
                clzz = DeleteFriendMsg.class;
                break;
            case FRIEND_STATE_CHANGED:
                clzz = UserStateChangeMsg.class;
                break;
        }

        Object o = new Gson().fromJson(body, clzz);
        log.debug("消息解析成功:{}->{}",msgType,o);
        return new ServerMsg(msgType, o);
    }

    @Override
    public boolean support(byte[] bytes) {
        return true;
    }
}
