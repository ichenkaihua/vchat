package com.chenkh.vchat.base.msg;

import com.chenkh.vchat.base.bean.MsgType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.nio.channels.AsynchronousSocketChannel;


@Getter
@AllArgsConstructor
public class ClientMsg<T>   {
	

    private MsgType.Client2Server msgType;


    private T body;


    public static <V> ClientMsg<V> builder(MsgType.Client2Server msgType, V body){
        return new ClientMsg<>(msgType, body);
    }

	

}
