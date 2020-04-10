package com.chenkh.vchat.base.msg;


import com.chenkh.vchat.base.bean.MsgType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Msg<T> {


    private String command;

    private MsgBody<T> msgBody;



    public static <V> Msg<V> buildClientMsg(MsgType.Client2Server msgType,V msgBody){
        return buildClientMsg(msgType, true, null, msgBody);
    }

    public static <V> Msg<V> buildClientMsg(MsgType.Client2Server msgType,boolean success,String reason,V msgBody){
        Msg<V> msg = new Msg<V>();
        msg.setCommand(msgType.toString());
        MsgBody<V> vMsgBody = new MsgBody<V>();
        vMsgBody.setSuccess(success);
        vMsgBody.setReason(reason);
        vMsgBody.setBody(msgBody);
        msg.setMsgBody(vMsgBody);
        return msg;
    }


    public static <V> Msg<V> buildServerMsg(MsgType.Server2Client msgType,V msgBody){
        return buildServerMsg(msgType, true, null, msgBody);
    }

    public static <V> Msg<V> buildServerMsg(MsgType.Server2Client msgType,boolean success,String reason,V msgBody){
        Msg<V> msg = new Msg<V>();
        msg.setCommand(msgType.toString());
        MsgBody<V> vMsgBody = new MsgBody<V>();
        vMsgBody.setSuccess(success);
        vMsgBody.setReason(reason);
        vMsgBody.setBody(msgBody);
        msg.setMsgBody(vMsgBody);
        return msg;
    }





}
