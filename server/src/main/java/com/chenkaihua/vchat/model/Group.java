package com.chenkaihua.vchat.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by chenkaihua on 15-9-30.
 */
@Entity
@Table(name = "group")
public class Group {

    @Id
    @GeneratedValue()
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "create_id")
    private int createId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

}
