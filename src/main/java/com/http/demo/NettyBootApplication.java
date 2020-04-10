package com.http.demo;

import com.http.demo.netty.EchoServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

//@Component
public class NettyBootApplication implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            try {
                EchoServer.getInstance().start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
