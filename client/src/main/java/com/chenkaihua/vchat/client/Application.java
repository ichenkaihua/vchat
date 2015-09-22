package com.chenkaihua.vchat.client;

/**
 * Created by chenkaihua on 15-9-22.
 */
public class Application {



    public static void main(String[] args){
        try {
            new SimpleChatClient("127.0.0.1",8000).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
