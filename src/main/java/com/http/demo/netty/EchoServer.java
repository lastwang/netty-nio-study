package com.http.demo.netty;

import java.io.IOException;

public class EchoServer {

    private int port;

    public void start() throws IOException {

        final EchoServerHandler handler = new EchoServerHandler();


    }
}
