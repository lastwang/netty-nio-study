package com.http.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ServerSocketChannelImpTest {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel open = ServerSocketChannel.open();
        open.socket().bind(new InetSocketAddress(8012));

        SocketChannel accept = open.accept();

        handle(accept);

        accept.close();
    }

    private static void handle(SocketChannel socketChannel) {

        ByteBuffer[] byteBuffers = new ByteBuffer[2];

        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        try {
            while (true) {
                int readth = 0;

                while (readth < 8) {
                    long read = socketChannel.read(byteBuffers);
                    readth += read;
                }

                Arrays.asList(byteBuffers).forEach(ByteBuffer::flip);

                int writeth = 0;
                while (writeth <8) {
                    long write = socketChannel.write(byteBuffers);
                    writeth += write;
                }
                Arrays.asList(byteBuffers).forEach(ByteBuffer::clear);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
