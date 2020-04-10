package com.chenkh.vchat.base.msg;

import com.chenkh.vchat.base.bean.MsgType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServerMsg<T> {

    private MsgType.Server2Client msgType;

    private T body;


    public static <V> ServerMsg<V> builde(MsgType.Server2Client msgType,V body){
        return new ServerMsg(msgType, body);
    }



}
