package com.chenkaihua.vchat.client;

import com.chenkaihua.vchat.client.initializer.SimpleChatClientInitializer;
import com.chenkaihua.vchat.client.view.FrameController;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SimpleChatClient {

    private final String host;
    private final int port;

    public SimpleChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        FrameController frameController = new FrameController();
        frameController.launchDialog();
        
      /*  EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer());
            Channel channel = bootstrap.connect(host, port).sync().channel();
            System.out.println("客户端已经启动，输入文字发送给server:");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }*/

    }
}

