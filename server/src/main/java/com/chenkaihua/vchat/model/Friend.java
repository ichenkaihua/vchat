package com.chenkaihua.vchat.model;

import com.avaje.ebeaninternal.server.lib.util.Str;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by chenkaihua on 15-9-30.
 */
@Entity
@Table(name = "friend")
public class Friend {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "main_id")
    private int mainId;
    @Column(name = "friend_id")
    private int firendId;

    @Column(name = "add_time")
    private Date addTime;
    @Column(name = "nick_name")
    private String nickName;



    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMainId() {
        return mainId;
    }

    public void setMainId(int mainId) {
        this.mainId = mainId;
    }

    public int getFirendId() {
        return firendId;
    }

    public void setFirendId(int firendId) {
        this.firendId = firendId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }



}
