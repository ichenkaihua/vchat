package com.chenkh.vchat.client;

import com.chenkh.vchat.base.bean.User;
import com.chenkh.vchat.client.chat.IFriendChatSession;
import com.chenkh.vchat.client.net.IClient;

/**
 * 应用程序上下文，提供网络操作接口，tray操作接口等组件
 */
public interface IContext {

    /**
     * 获取网络通讯接口，可用来发送接收消息
     * @return
     */
    IClient getNetClient();

    /**
     * 启动ui
     */
    void launchUi();

    /**
     * 获取当前登录的账号
     * @return 如果没有登录，则返回null
     */
    User getCurrentLoggedInUser();

    /**
     * 是否已经登录了账号
     * @return
     */
    boolean isLoggedIn();


    /**
     * 获取托盘图标接口，可用来操控托盘，设置按钮
     * @return
     */
    ITray getTray();


    /**
     * 创建或者获取chatsession，如果当前存在，则取出。否则，创建并放入容器，然后返回
     * @param friendId
     * @return
     */
    IFriendChatSession createOrGetFriendChatSession(int friendId);

}
