package com.chenkh.vchat.client.net;

public interface IConnectObservable {
    void addConnectListener(IConnectListener listener);
    void removeConnectListener(IConnectListener listener);
}
