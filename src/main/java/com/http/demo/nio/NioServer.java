package com.http.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class NioServer {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(9800));

        Selector selector = Selector.open();

        // 设置位非堵塞

        serverSocketChannel.configureBlocking(Boolean.FALSE);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("等待一秒!");
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
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(8));
                }

                if (next.isReadable()) { //OP_READ事件
                    //socketChannel的读事件
                    SocketChannel channel = (SocketChannel) next.channel();

                    ByteBuffer attachment = (ByteBuffer) next.attachment();
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

                    //关闭通道
                    channel.close();
//                    attachment.clear();
                }

                //手动移除SelectionKey,防止重复操作
                iterator.remove();

            }


        }
    }
}
