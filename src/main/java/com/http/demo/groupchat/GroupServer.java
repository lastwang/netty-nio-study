package com.http.demo.groupchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

/**
 * 聊天服务器服务端
 */
public class GroupServer {

    private static final Logger log = LoggerFactory.getLogger(GroupServer.class);

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private static final int PORT = 9800;

    public static void main(String[] args) {
        GroupServer groupServer = new GroupServer();

        groupServer.listen();
    }

    public GroupServer() {

        try {
            serverSocketChannel = ServerSocketChannel.open();

            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));

            selector = Selector.open();

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            log.error("聊天服务器初始化失败!", e);
        }
    }

    /**
     * 轮询selector
     */
    protected void listen() {
        try {
            while (true) {
                int select = selector.select(1000);

                if (select > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                    while (iterator.hasNext()) {

                        SelectionKey key = iterator.next();

                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();

                            socketChannel.configureBlocking(false);

                            socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                            log.info("{}上线了",socketChannel.getRemoteAddress());
                        }

                        if (key.isReadable()) {
                            readDate(key);
                        }

                        iterator.remove();
                    }


                } else {
                    log.info("没有监听到事件,请等待1秒");
                }
            }
        } catch (IOException e) {
            log.error("监听select错误!", e);
        } finally {
            close();
        }

    }

    private void readDate(SelectionKey key) {

        ByteBuffer attachment = (ByteBuffer) key.attachment();
        SocketChannel clientSocket = (SocketChannel) key.channel();
        try {
            List<Byte> chat = new ArrayList<>();
            attachment.clear();
            int read = clientSocket.read(attachment);
            if (read < 0) {
                key.cancel();
            } else if (read > 0) {

                attachment.flip();
                for (int i = 0; i < attachment.limit(); i++) {
                    chat.add(attachment.get());
                }

                byte[] msg = new byte[chat.size()];
                for (int i = 0; i < chat.size(); i++) {
                    msg[i] = chat.get(i);
                }
                String msgStr = new String(msg);
                log.info("{}发送信息是:{}", clientSocket.getRemoteAddress(), msgStr);

                // 给其他客户端发送消息
                sendMsgToOtherClient(msgStr, clientSocket);
            }
        } catch (IOException e) {
            try {
                log.error("客户端{}:离线了",clientSocket.getRemoteAddress(), e);
                key.cancel();
                clientSocket.close();
            } catch (IOException ex) {
                log.error("客户端错误!",ex);
            }
        }


    }

    private void sendMsgToOtherClient(String msg, SocketChannel selfClient) throws IOException {
        log.info("客户端{}:消息转发中",selfClient.getRemoteAddress());
        ByteBuffer msgBuffer = ByteBuffer.wrap(msg.getBytes());

        for (SelectionKey key : selector.keys()) {
            Channel client = key.channel();

            if (client instanceof SocketChannel && client != selfClient) {

                try {
                    ((SocketChannel) client).write(msgBuffer);
                } finally {
                    // 从零开始写
                    msgBuffer.clear();
                }
            }


        }
    }

    private void close() {
        if (serverSocketChannel != null) {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                log.error("serverSocketChannel 关闭错误！", e);
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                log.error("selector 关闭错误！", e);
            }
        }
    }
}
