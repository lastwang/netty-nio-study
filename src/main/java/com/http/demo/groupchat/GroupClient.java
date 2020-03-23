package com.http.demo.groupchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class GroupClient {

    private static final Logger log = LoggerFactory.getLogger(GroupClient.class);

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 9800;

    private SocketChannel socketChannel;

    private Selector selector;

    public GroupClient() {
        try {
            socketChannel = SocketChannel.open();
        } catch (IOException e) {

        } finally {
            close();
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
