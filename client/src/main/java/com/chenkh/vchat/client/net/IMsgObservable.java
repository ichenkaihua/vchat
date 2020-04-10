package com.chenkh.vchat.client.net;

public interface IMsgObservable {

    void addMsgListener(IMsgListener msgListener);

    void removeMsgListener(IMsgListener msgListener);
}
