package com.http.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TestController {

    static AtomicInteger atomicInteger = new AtomicInteger(0);
    @RequestMapping("test")
    public String hello(HttpServletRequest request) throws InterruptedException {
        atomicInteger.addAndGet(1);
        System.out.println(atomicInteger.get());
        request.getSession().setAttribute("name","user");
//        Thread.sleep(50);
        return "hello undertow";
    }

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

//    public static void main(String[] args) {
//        log.info("wada,{}", (Object) args);
//    }

    @RequestMapping("logout")
    public String logout() {
        return "logout";
    }
}
