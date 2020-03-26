package com.http.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TestController {

    static AtomicInteger atomicInteger = new AtomicInteger(0);
    @RequestMapping("test")
    public String hello() throws InterruptedException {
        atomicInteger.addAndGet(1);
        System.out.println(atomicInteger.get());
        Thread.sleep(50);
        return "hello undertow";
    }
}
