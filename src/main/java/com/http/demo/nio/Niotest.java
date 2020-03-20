package com.http.demo.nio;

import java.nio.IntBuffer;

public class Niotest {

    public static void main(String[] args) {

        IntBuffer intBuffer = IntBuffer.allocate(5);



        for (int i = 0; i < intBuffer.capacity(); i++) {


            intBuffer.put(i * 2);
        }
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println("值:" + intBuffer.get());
        }
    }
}
