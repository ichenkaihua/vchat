package com.chenkaihua.vchat.mesage;

/**
 * Created by chenkaihua on 15-9-25.
 */
public class LoginMessage  extends  Message{
    private String username;
    private String password;



    public LoginMessage(String username, String password) {
        super(1);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
