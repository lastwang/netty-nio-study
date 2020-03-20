package com.http.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {

    public static void main(String[] args) {

        try (SocketChannel socketChannel = SocketChannel.open()) {

//            socketChannel.bind(new InetSocketAddress("127.0.0.1", 9800));
            socketChannel.configureBlocking(false);
            ByteBuffer byteBuffer = ByteBuffer.wrap("hello  撒改为过去".getBytes());

            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9800);

            // 非堵塞连接
            if(!socketChannel.connect(inetSocketAddress)) {
                // 没有连接成功
                while (!socketChannel.finishConnect()){
                    //客户端连接需要时间
                    System.out.println("客户端连接需要时间，");
                }
            }
            socketChannel.write(byteBuffer);
            ByteBuffer byteBuffer2 = ByteBuffer.wrap("您号  哇大师父".getBytes());
            ByteBuffer byteBuffer3 = ByteBuffer.wrap("对么  倒萨范德萨个".getBytes());
            socketChannel.write(byteBuffer2);
            Thread.sleep(4000);

            socketChannel.write(byteBuffer3);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    static void client1() {
        try (SocketChannel socketChannel = SocketChannel.open()) {

//            socketChannel.bind(new InetSocketAddress("127.0.0.1", 9800));
            socketChannel.configureBlocking(false);
            ByteBuffer byteBuffer = ByteBuffer.wrap("hello  第三个VS国事访问".getBytes());

            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9800);

            // 非堵塞连接
            if(!socketChannel.connect(inetSocketAddress)) {
                // 没有连接成功
                while (!socketChannel.finishConnect()){
                    //客户端连接需要时间
                    System.out.println("客户端连接需要时间，");
                }
            }
            socketChannel.write(byteBuffer);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
