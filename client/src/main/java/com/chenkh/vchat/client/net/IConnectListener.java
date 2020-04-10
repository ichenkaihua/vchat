package com.chenkh.vchat.client.net;

@FunctionalInterface
public interface IConnectListener {

    void onConnect(boolean success, Throwable exc);

}
