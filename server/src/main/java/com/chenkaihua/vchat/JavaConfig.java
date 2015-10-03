package com.chenkaihua.vchat;

import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.UnderscoreNamingConvention;
import com.avaje.ebean.springsupport.factory.EbeanServerFactoryBean;
import com.avaje.ebean.springsupport.txn.SpringAwareJdbcTransactionManager;
import org.avaje.agentloader.AgentLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenkaihua on 15-9-25.
 */
@Configuration
@ComponentScan("com.chenkaihua.vchat")
@EnableTransactionManagement(proxyTargetClass = true)
public class JavaConfig implements TransactionManagementConfigurer {


    private static Logger logger = LoggerFactory.getLogger(JavaConfig.class);

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/vchat");
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUsername("github");
        driverManagerDataSource.setPassword("github-pass");
        return new LazyConnectionDataSourceProxy(driverManagerDataSource);
    }


    @Bean(name = "txManager")

    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }


    @Bean
    public EbeanServerFactoryBean ebeanServerFactoryBean() {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setDataSource(dataSource());
        List<String> packges = new ArrayList<String>();
        packges.add("com.chenkaihua.vchat.model");
        serverConfig.setPackages(packges);
        serverConfig.setName("ebeanServer");
        serverConfig.setDefaultServer(true);
        serverConfig.setExternalTransactionManager(new SpringAwareJdbcTransactionManager());
        serverConfig.setNamingConvention(new UnderscoreNamingConvention());
        EbeanServerFactoryBean factory = new EbeanServerFactoryBean();
        factory.setServerConfig(serverConfig);
        return factory;
    }


    public static void main(String[] args) {
        if (!AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1;packages=com.chenkaihua.vchat.model.**")) {
            logger.info("avaje-ebeanorm-agent not found in classpath - not dynamically loaded");
        }

        AnnotationConfigApplicationContext configApplicationContext
                = new AnnotationConfigApplicationContext(JavaConfig.class);


        Server server = configApplicationContext.getBean(Server.class);
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
