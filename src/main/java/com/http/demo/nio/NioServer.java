package com.http.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NioServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(9800));

        Selector selector = Selector.open();

        // 设置位非堵塞

        serverSocketChannel.configureBlocking(Boolean.FALSE);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("没有监听到事件,等待一秒!");
                continue;
            }

            // 返回关注的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {

                SelectionKey next = iterator.next();

                if (next.isAcceptable()) {
                    //有客户端连接
                    //给该客户端生成socket客户端
                    // 方法本生堵塞，但是next.isAcceptable()已经得知有连接了
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    socketChannel.configureBlocking(false);
                    //  将socketChannel注册到selector,同事关注事件为OP_READ，绑定buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if (next.isReadable()) { //OP_READ事件
                    //socketChannel的读事件
                    SocketChannel channel = (SocketChannel) next.channel();

                    ByteBuffer attachment = (ByteBuffer) next.attachment();
                    attachment.clear();
                    int read = channel.read(attachment);
                    if (read > 0) {
                        attachment.flip();
                        List<Byte> bu = new ArrayList<>();
                        while (attachment.hasRemaining()) {
                            bu.add(attachment.get());
                        }
                        byte[] bytes = new byte[bu.size()];
                        for (int i = 0; i < bu.size(); i++) {
                            bytes[i] = bu.get(i);
                        }
                        System.out.println("客户端的消息是:" + new String(bytes));
                        System.out.println("缓缓1秒");
                        Thread.sleep(1000);
                    }
                    if (read < 0) {
                        next.cancel();
                        channel.close();
                        System.out.println("我要关闭了！");
                    }

                    if(read == 0){
                        System.out.println("这次数据读出来是0，等待1秒");
                        Thread.sleep(1000);
                    }
                  /*
                    attachment.clear();
                    List<Byte> bu = new ArrayList<>();
                    //-1 表示通道关闭， 0读出数据为0，客户端发数据直到连接关闭
                    while (channel.read(attachment) != -1) {
                        attachment.flip();
                        while (attachment.hasRemaining()) {
                            bu.add(attachment.get());
                        }
                        // 反转从读变写
                        attachment.clear();
                    }

                    byte[] bytes = new byte[bu.size()];
                    for (int i = 0; i < bu.size(); i++) {
                        bytes[i] = bu.get(i);
                    }
                    System.out.println("客户端的消息是:" + new String(bytes));
                    next.cancel();
                    //关闭通道 : 客户端离线了
                    channel.close();*/
//                    attachment.clear();
                }

                //手动移除SelectionKey,防止重复操作
                iterator.remove();

            }


        }
    }
}
