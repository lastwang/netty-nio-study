package com.http.demo.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelTest {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();

        String s = "helsaddddddddd挖的发生ddddaslo";

        FileOutputStream out = new FileOutputStream("d:\\test.txt");
        ByteBuffer byteBuffer = ByteBuffer.wrap(s.getBytes());
        FileChannel channel = out.getChannel();
        channel.write(byteBuffer);
        channel.close();
/*
        out.write(s.getBytes());
        out.flush();
*/

        out.close();

        long end =System.nanoTime();

        //channel 8923866
        // 5045949
        System.out.println(end - start);
    }
}
