package com.chenkaihua.vchat;

import org.avaje.agentloader.AgentLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by chenkaihua on 15-9-22.
 */
public class Application {
    static Logger logger = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {

        if (!AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1;packages=com.chenkaihua.vchat.model.**")) {
            logger.info("avaje-ebeanorm-agent not found in classpath - not dynamically loaded");
        }
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");

        Server server = applicationContext.getBean(Server.class);
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
