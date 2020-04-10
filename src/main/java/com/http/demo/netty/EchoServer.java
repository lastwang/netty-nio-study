package com.http.demo.netty;

import com.http.demo.nio.EchoNettyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class EchoServer {

    private static final Logger log = LoggerFactory.getLogger(EchoServer.class);

    private int port;

    private EchoServer(int port) {
        this.port = port;
    }

    private static class INSTANCE {
        private static final EchoServer echoServer = new EchoServer(EchoNettyConfig.PORT);
    }

    public static EchoServer getInstance() {
        return INSTANCE.echoServer;
    }

    public void start() throws InterruptedException {
        log.info("Netty Server is starting");

        final EchoServerHandler handler = new EchoServerHandler();

        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(group).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(handler);
                        }
                    })
                    .localAddress(new InetSocketAddress(port));
            ChannelFuture f = bootstrap.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
            log.error("Netty Server is stopping");
        }


    }

    public static void main(String[] args) throws InterruptedException {
        EchoServer server = new EchoServer(EchoNettyConfig.PORT);

        server.start();
    }
}
