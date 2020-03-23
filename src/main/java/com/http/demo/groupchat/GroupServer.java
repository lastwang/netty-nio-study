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

    public GroupServer() {

        try {
            serverSocketChannel = ServerSocketChannel.open();

            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));

            selector = Selector.open();

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            log.error("聊天服务器初始化失败!", e);
        } finally {
            close();
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

                        }

                        if (key.isReadable()) {
                            ByteBuffer attachment = (ByteBuffer) key.attachment();
                            SocketChannel clientSocket = (SocketChannel) key.channel();

                            List<Byte> chat = new ArrayList<>();
                            attachment.clear();
                            while (clientSocket.read(attachment) != -1) {

                                attachment.flip();
                                for (int i = 0; i < attachment.limit(); i++) {
                                    chat.add(attachment.get());
                                }

                                attachment.clear();
                            }

                            byte[] msg = new byte[chat.size()];
                            for (int i = 0; i < chat.size(); i++) {
                                msg[i] = chat.get(i);
                            }
                            log.info("{}发送信息是:{}", clientSocket.getRemoteAddress(), new String(msg));

                            clientSocket.close();
                        }
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

    public void readDate(SelectionKey key) {

        ByteBuffer attachment = (ByteBuffer) key.attachment();
        SocketChannel clientSocket = (SocketChannel) key.channel();
        try {
            List<Byte> chat = new ArrayList<>();
            attachment.clear();
            while (clientSocket.read(attachment) != -1) {

                attachment.flip();
                for (int i = 0; i < attachment.limit(); i++) {
                    chat.add(attachment.get());
                }

                attachment.clear();
            }

            byte[] msg = new byte[chat.size()];
            for (int i = 0; i < chat.size(); i++) {
                msg[i] = chat.get(i);
            }
            String msgStr = new String(msg);
            log.info("{}发送信息是:{}", clientSocket.getRemoteAddress(), msgStr);

            // 给其他客户端发送消息
            sendMsgToOtherClient(msgStr, clientSocket);


        } catch (IOException e) {
            log.error("客户端数据读取错误", e);
        } finally {
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    log.error("客户端关闭错误", e);
                }
            }
        }


    }

    public void sendMsgToOtherClient(String msg, SocketChannel selfClient) {
        ByteBuffer msgBuffer = ByteBuffer.wrap(msg.getBytes());

        for (SelectionKey key : selector.keys()) {
            Channel client = key.channel();

            if (client instanceof SocketChannel && client != selfClient) {

                try {
                    ((SocketChannel) client).write(msgBuffer);
                } catch (IOException e) {
                    try {
                        log.error("客户端{}离线了", ((SocketChannel) client).getLocalAddress(), e);
                        // 取消注册
                        key.cancel();
                        client.close();
                    } catch (IOException ex) {
                        log.error("客户端错误", ex);
                    }
                } finally {
                    // 从零开始写
                    msgBuffer.clear();
                }
            }



        }
    }

    public void close() {
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
