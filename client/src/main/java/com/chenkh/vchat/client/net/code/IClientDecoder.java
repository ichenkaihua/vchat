package com.chenkh.vchat.client.net.code;

import com.chenkh.vchat.base.IDecoder;
import com.chenkh.vchat.base.msg.ServerMsg;

public interface IClientDecoder extends IDecoder {

    ServerMsg<?> decode2ServerMsg(byte[] bytes);

    @Override
    default Object decode(byte[] bytes) {
        return decode2ServerMsg(bytes);
    }
}
