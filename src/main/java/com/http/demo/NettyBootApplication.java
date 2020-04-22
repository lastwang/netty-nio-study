package com.http.demo;

import com.http.demo.netty.EchoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

//@Component
public class NettyBootApplication implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(NettyBootApplication.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            try {
                EchoServer.getInstance().start();
            } catch (InterruptedException e) {
                log.error("Netty Starting error:", e);
            }
        }
    }
}
