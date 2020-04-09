package com.http.demo.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelIn {

    public static void main(String[] args) throws IOException {
//        copy();
//        copyByreadMore();

        transfrom();
        /*
        File file = new File("d:\\test.txt");
        FileInputStream in = new FileInputStream(file);

        FileChannel channel = in.getChannel();

//        byte[] bytes = new byte[(int)file.length()];
        ByteBuffer buffer = ByteBuffer.allocate((int)file.length());
        channel.read(buffer);

        String s = new String(buffer.array());


        channel.close();
        in.close();
        System.out.println(s);*/
    }


    // 读一次
    private static void copy() throws IOException {
        File file = new File("d:\\test.txt");

        FileInputStream in = new FileInputStream(file);

        FileChannel channel = in.getChannel();

        ByteBuffer allocate = ByteBuffer.allocate((int) file.length());

        channel.read(allocate);
        channel.close();
        in.close();
        FileOutputStream out = new FileOutputStream("d:\\test2.txt");

        FileChannel channel1 = out.getChannel();
        // 此时allocate 的下标是读的末尾，需要翻转
        allocate.flip();
        channel1.write(allocate);

        channel1.close();
        channel1.close();


    }

    private static void copyByreadMore() throws IOException {
        File file = new File("d:\\test.txt");

        FileInputStream in = new FileInputStream(file);
        FileOutputStream out = new FileOutputStream("d:\\test3.txt");

        FileChannel channel = in.getChannel();
        FileChannel channel1 = out.getChannel();

        ByteBuffer allocate = ByteBuffer.allocate(5);

        while (true) {

            allocate.clear();
            int read = channel.read(allocate);
            if (read < 0) {
                break;
            }

            allocate.flip();

            channel1.write(allocate);


        }

        channel.close();
        in.close();
        channel1.close();
        out.close();


    }

    private static void transfrom() throws IOException {
        File file = new File("d:\\test.txt");

        FileInputStream in = new FileInputStream(file);
        FileOutputStream out = new FileOutputStream("d:\\text3.txt");

        FileChannel channel1 = out.getChannel();
        FileChannel channel = in.getChannel();

        channel.transferTo(0, file.length(), channel1);

        channel1.close();
        channel.close();
        in.close();
        out.close();
    }
}
