package com.http.demo.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapperByteTest {

    public static void main(String[] args) throws IOException {

        RandomAccessFile file = new RandomAccessFile("d:\\test.txt", "rw");
        FileChannel channel = file.getChannel();

        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 4);

        map.put(0, (byte) 'H');
        map.put(3, (byte) '4');

        file.close();
    }
}
