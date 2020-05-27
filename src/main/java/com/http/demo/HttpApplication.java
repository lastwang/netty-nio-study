package com.http.demo;

import com.http.demo.zookeeper.ZookeeperClient;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class HttpApplication {

    private static final Logger log = LoggerFactory.getLogger(HttpApplication.class);

    @Autowired
    private ZookeeperClient zookeeperClient;

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(HttpApplication.class, args);
        ZookeeperClient bean = run.getBean(ZookeeperClient.class);


        List<String> children = bean.getChildren("/");

        log.info("目录:{}",children);
    }

    @Bean(destroyMethod = "close")
    public ZookeeperClient zookeeperClient() throws IOException, InterruptedException {

        return new ZookeeperClient("192.168.107.128:2181",2000);
    }
}
