package com.chenkaihua.vchat.service;

import com.avaje.ebean.EbeanServer;
import com.chenkaihua.vchat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenkaihua on 15-9-22.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    EbeanServer ebeanServer;


    public User getUserById(int id){
        return ebeanServer.find(User.class, id);
    }

    @Transactional
    public void saveUser(User user){
        ebeanServer.save(user);



    }









}
