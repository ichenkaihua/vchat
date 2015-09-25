package com.chenkaihua.vchat.mesage;

/**
 * Created by chenkaihua on 15-9-25.
 */
public abstract class Message {
    private int type;

    public Message(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
