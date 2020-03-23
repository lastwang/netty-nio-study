package com.http.demo.groupchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class GroupClient {

    private static final Logger log = LoggerFactory.getLogger(GroupClient.class);

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 9800;

    private SocketChannel socketChannel;

    private Selector selector;

    private String userName;

    public GroupClient() {
        try {
            socketChannel = SocketChannel.open();
            selector = Selector.open();
            socketChannel.configureBlocking(false);

            socketChannel.socket().bind(new InetSocketAddress(HOST, PORT));

            socketChannel.register(selector, SelectionKey.OP_READ);

            userName = socketChannel.getLocalAddress().toString().substring(1);
        } catch (IOException e) {
            log.error("客户端初始化失败!", e);
        }
    }

    public static void main(String[] args) {
        GroupClient groupClient = new GroupClient();

        new Thread(() -> {
            while (true) {
                groupClient.receviedMsg();
                try {
                    Thread.sleep(3000);
                    log.info("每隔3秒接受信息");
                } catch (InterruptedException e) {
                    log.error("线程睡眠失败!", e);
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {

            String msg = scanner.nextLine();

            groupClient.sendMsg(msg);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> groupClient.close()));
    }

    private void sendMsg(String message) {
        log.info("{}:发送消息是:{}", userName, message);

        try {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        } catch (IOException e) {
            log.error("发送消息失败", e);
        }

    }

    private void receviedMsg() {
        try {
            int select = selector.select(2000);

            if (select > 0) {

                Iterator <SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {

                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);

                        int read = channel.read(buffer);

                        if (read > 0) {
                            log.info("收到消息:{}", new String(buffer.array()));
                        } else if (read < 0) {
                            log.info("服务器已断开");
                            key.cancel();
                            channel.close();
                        }
                    }
                }

            } else {
                log.info("等待服务器给消息");
            }

        } catch (IOException e) {
            log.error("客户端接收消息失败!", e);
        }
    }

    private void close() {
        if (socketChannel != null) {
            try {
                socketChannel.close();
            } catch (IOException e) {
                log.error("客户端关闭失败！", e);
            }
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                log.error("选择器关闭失败!", e);
            }
        }

    }
}
