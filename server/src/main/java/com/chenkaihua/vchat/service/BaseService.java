package com.chenkaihua.vchat.service;

import com.avaje.ebean.EbeanServer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by chenkaihua on 15-9-22.
 */
public class BaseService<M,PK> {

    Class<M> mClass;

    public BaseService(){

    }


    @Autowired
    EbeanServer ebeanServer;

    public void save(M model){
        ebeanServer.save(model);
    }


}
