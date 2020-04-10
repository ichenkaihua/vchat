package com.chenkh.vchat.server.coder;

import com.chenkh.vchat.base.IEncoder;
import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.ServerMsg;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class JsonEncoder implements IEncoder {


    @Override
    public boolean support(Object content) {
        return content instanceof ClientMsg;
    }

    @Override
    public byte[] encode(Object content) {
        ServerMsg serverMsg = (ServerMsg)content;
        String bodjJson = new Gson().toJson(serverMsg.getBody());
        String command = serverMsg.getMsgType().toString();
        String msgSource = new StringBuilder(command).append(" ").append(bodjJson).append("\r\t\r\t").toString();
        log.debug("即将发送消息:{}->{}", command, bodjJson);
        byte[] bytes = msgSource.getBytes(Charset.forName("utf-8"));
        return bytes;
    }
}
