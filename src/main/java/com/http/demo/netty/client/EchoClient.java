package com.http.demo.netty.client;

import com.http.demo.netty.EchoNettyConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {

    private String host;

    private int port;

    public EchoClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public void start() throws InterruptedException {

        final EchoClientHandler clientHandler = new EchoClientHandler();

        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        try {
            Bootstrap client = new Bootstrap();
            ChannelFuture f = client.group(clientGroup).channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(clientHandler);
                        }
                    }).connect().sync();


            f.channel().closeFuture().sync();
        } finally {
            clientGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient(EchoNettyConfig.LOCAL_HOST, EchoNettyConfig.PORT).start();
    }
}
