package com.chenkh.vchat.client.net.aio;

import com.chenkh.vchat.base.IDecoder;
import com.chenkh.vchat.base.IEncoder;
import com.chenkh.vchat.base.bean.MsgType;
import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.ServerMsg;
import com.chenkh.vchat.client.net.AbstractClient;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class AioClient extends AbstractClient {


    private AsynchronousChannelGroup group;
    private AsynchronousSocketChannel socket;

    public AioClient(IEncoder encoder, IDecoder decoder) {
        super(encoder, decoder);
    }

    @Override
    public <T> Future<Integer> sendMsg(MsgType.Client2Server msgType, T body) {
        byte[] bytes = encoder.encode(new ClientMsg(msgType, body));
        ByteBuffer bf = ByteBuffer.wrap(bytes);
        Future<Integer> write = socket.write(bf);

        return write;
    }

    @Override
    public boolean isOpen() {
        return socket != null && socket.isOpen();
    }

    @Override
    public void connectServer(String ip, int port) throws IOException {

        if (isOpen()) throw new IllegalStateException("服务器正在运行,请关闭后再连接");


        if (group == null) {
            group = AsynchronousChannelGroup.withCachedThreadPool(
                    Executors.newCachedThreadPool(), 2);
        }
        socket = AsynchronousSocketChannel.open(group);

        socket.connect(new InetSocketAddress(ip, port), null, new CompletionHandler<Void, Object>() {
            @Override
            public void completed(Void result, Object attachment) {
                publishConnectEvent(true,null);
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 5);
                socket.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        if(result<=-1){
                            //连接失败,已经断开
                            publishConnectEvent(false,new IllegalStateException("服务器已经断开"));
                        }
                        else{
                            ByteBuffer buffer = ByteBuffer.allocate(1024 * 5);
                            socket.read(buffer,buffer,this);
                            ServerMsg serverMsg = (ServerMsg) decoder.decode(attachment.array());
                            publishMsgEvent(serverMsg);
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        log.error("消息接收失败",exc);

                    }
                });


            }

            @Override
            public void failed(Throwable exc, Object attachment) {

                publishConnectEvent(false,exc);
            }
        });

    }









    @Override
    public void close() throws IOException {
        if (!socket.isOpen()) throw new IllegalStateException("当前没有开启连接!");
        socket.close();
    }


}
