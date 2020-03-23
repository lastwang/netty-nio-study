package com.http.demo.groupchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ClientBase {

    private static final Logger log = LoggerFactory.getLogger(ClientBase.class);

    private static final String HOST = "127.0.0.1";
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int PORT = 9801;

    private static final int SERVER_PORT = 9800;

    private SocketChannel socketChannel;

    private Selector selector;

    private String userName;

    public ClientBase(String userName, String host, int port, String serverHost, int serverPort) {
        try {
            socketChannel = SocketChannel.open();
            selector = Selector.open();
            socketChannel.configureBlocking(false);

            socketChannel.socket().bind(new InetSocketAddress(host, port));

            socketChannel.register(selector, SelectionKey.OP_READ);

            if (userName == null) {
                this.userName = socketChannel.getLocalAddress().toString().substring(1);
            } else {
                this.userName = userName;
            }
            init(new InetSocketAddress(serverHost, serverPort));
        } catch (IOException e) {
            log.error("客户端初始化失败!", e);
        }
    }

    public ClientBase() {
        try {
            socketChannel = SocketChannel.open();
            selector = Selector.open();
            socketChannel.configureBlocking(false);

            socketChannel.socket().bind(new InetSocketAddress(HOST, PORT));

            socketChannel.register(selector, SelectionKey.OP_READ);

            this.userName = socketChannel.getLocalAddress().toString().substring(1);

            init(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
        } catch (IOException e) {
            log.error("客户端初始化失败!", e);
        }
    }

    protected void init(SocketAddress inetSocketAddress) throws IOException {
        if (!socketChannel.connect(inetSocketAddress)) {
            // 没有连接成功
            while (!socketChannel.finishConnect()) {
                //客户端连接需要时间
                System.out.println("客户端连接需要时间，");
            }
        }
    }

    public void sendMsg(String message) {
        log.info("{}:发送消息是:{}", userName, message);

        try {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        } catch (IOException e) {
            log.error("发送消息失败", e);
        }

    }

    public void receviedMsg() {
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
                        buffer.flip();
                        byte[] tmp = new byte[buffer.limit()];
                        for (int i = 0; i < buffer.limit(); i++) {
                            tmp[i] = buffer.get();
                        }
                        if (read > 0) {
                            log.info("收到消息:{}", new String(tmp));
                        } else if (read < 0) {
                            log.info("服务器已断开");
                            key.cancel();
                            channel.close();
                        }
                    }
                    iterator.remove();
                }

            } else {
//                log.info("等待服务器给消息");
            }

        } catch (IOException e) {
            log.error("客户端接收消息失败!", e);
        }
    }

    public void close() {
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
        log.info("通道关闭成功!");
    }
}
