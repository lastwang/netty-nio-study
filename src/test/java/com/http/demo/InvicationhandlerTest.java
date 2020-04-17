package com.http.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InvicationhandlerTest implements InvocationHandler {

    private Object object;

    public InvicationhandlerTest(Object object) {
        this.object = object;
    }

    public InvicationhandlerTest() {

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("hhh");
        Object invoke = method.invoke(object, args);

        System.out.println("word");
        return invoke;
    }



    public static void main(String[] args) {
        Say test = new Test();

        InvicationhandlerTest handler = new InvicationhandlerTest(test);

        Say instance =(Say)Proxy.newProxyInstance(handler.getClass().getClassLoader(), test.getClass().getInterfaces(), handler);

        instance.say();
    }
}
interface Say {
    void  say();
}

class Test implements Say {

    @Override
    public void say() {
        System.out.println("hello");
    }
}
