package com.chenkh.vchat.client.net;

import com.chenkh.vchat.base.IDecoder;
import com.chenkh.vchat.base.IEncoder;
import com.chenkh.vchat.base.msg.ServerMsg;
import com.chenkh.vchat.client.net.code.JsonDecoder;
import com.chenkh.vchat.client.net.code.JsonEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public abstract class AbstractClient implements IClient {

    protected List<IConnectListener> connectListeners = new CopyOnWriteArrayList<>();
    protected List<IMsgListener> msgListeners = new CopyOnWriteArrayList<>();

    protected IEncoder encoder;

    protected IDecoder decoder;



    public AbstractClient(IEncoder encoder, IDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public void addConnectListener(IConnectListener listener) {
        synchronized (connectListeners) {
            connectListeners.add(listener);
        }
    }

    @Override
    public void removeConnectListener(IConnectListener listener) {
        synchronized (connectListeners) {
            connectListeners.remove(listener);
        }
    }

    @Override
    public void addMsgListener(IMsgListener msgListener) {
        log.debug("正在添加{}监听",msgListener);
            msgListeners.add(msgListener);
    }

    @Override
    public void removeMsgListener(IMsgListener msgListener) {
        log.debug("正在移除{}监听",msgListener);
            msgListeners.remove(msgListener);
    }


    protected   void publishConnectEvent(boolean success,Throwable exc){
        for (IConnectListener connectListener : connectListeners) {
            connectListener.onConnect(success,exc);
        }
    }

    protected   void publishMsgEvent(ServerMsg serverMsg){
        log.debug("正在分发消息");


            for (IMsgListener msgListener : msgListeners) {
                log.debug("正在分发到{}",msgListener);
                msgListener.onReceivedMsg(serverMsg);

        }

            log.debug("分发完毕");

        log.debug("当前总共多少监听{}",msgListeners.size());

        msgListeners.forEach(item->log.debug("{}",item));
    }
}
