package com.chenkh.vchat.client.net.bio;

import com.chenkh.vchat.base.IDecoder;
import com.chenkh.vchat.base.IEncoder;
import com.chenkh.vchat.base.bean.MsgType;
import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.ServerMsg;
import com.chenkh.vchat.client.net.AbstractClient;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class BioClient extends AbstractClient {

    private Socket socket;

    public BioClient(IEncoder encoder, IDecoder decoder) {

        super(encoder, decoder);
        executorService = Executors.newCachedThreadPool();
    }

    private ExecutorService executorService;


    @Override
    public <T> Future<Integer> sendMsg(MsgType.Client2Server msgType, T body) {
        return executorService.submit(()->{
            try {
                byte[] bytes = encoder.encode(new ClientMsg(msgType, body));
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
                 bufferedOutputStream.write(bytes);
                 bufferedOutputStream.flush();
                return bytes.length;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        });
    }

    @Override
    public boolean isOpen() {
        return !socket.isClosed();
    }

    @Override
    public void connectServer(String ip, int port) throws IOException {
        if(socket==null){
            socket=new Socket();
        }
        socket.connect(new InetSocketAddress(ip,port));
        executorService.submit(()->{
            while (true){
                BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
                byte[] bytes = new byte[1024*5];
                int readBytes=bufferedInputStream.read(bytes);
                executorService.submit(()->{
                    ServerMsg serverMsg = (ServerMsg) decoder.decode(Arrays.copyOf(bytes, readBytes));
                    publishMsgEvent(serverMsg);
                });


            }
        });




    }

    @Override
    public void close() throws IOException {

    }
}
