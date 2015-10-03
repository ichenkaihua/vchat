package com.chenkaihua.vchat.test;

import com.chenkaihua.vchat.JavaConfig;
import com.chenkaihua.vchat.model.User;
import com.chenkaihua.vchat.service.UserService;
import org.avaje.agentloader.AgentLoader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by chenkaihua on 15-9-30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JavaConfig.class)
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

 /*   @Before
    public void setUp(){
        System.out.println("凯斯了啦啦啦啦啦");
        if (!AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1;packages=com.chenkaihua.vchat.model.**")) {
            System.out.println("avaje-ebeanorm-agent not found in classpath - not dynamically loaded");
        }
    }*/

    @BeforeClass
    public static void beforeClass(){
        if (!AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1;packages=com.chenkaihua.vchat.model.**")) {
            System.out.println("avaje-ebeanorm-agent not found in classpath - not dynamically loaded");
        }
    }

    @Test
    @Rollback
    public void testAdd(){


        User user = new User();
        user.setName("陈ss陈");
        user.setPassword("sss");
        user.setCreate_time(new Date());
        userService.saveUser(user);




    }



}
