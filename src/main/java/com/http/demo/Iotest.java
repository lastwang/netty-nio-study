package com.http.demo;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Iotest {

    public static void main(String[] args) throws IOException {

        //BIO
        //1. 创建线程池
        //2. 如果有客户端连接，就创建一个线程与之通信
        ExecutorService fixedPool = Executors.newFixedThreadPool(1);


        ServerSocket socket = new ServerSocket();

        socket.bind(new InetSocketAddress(8099));

        try {
            // 监听客户端连接
            while (true) {
                final Socket accept = socket.accept();
                // 启动一个线程处理
                System.out.println("连接一个客户端");
                fixedPool.execute(() -> send(accept));
                //堵塞
                fixedPool.execute(() -> handle(accept));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handle(Socket socket) {
        System.out.println("id+name" + Thread.currentThread().getId() + Thread.currentThread().getName());
        int len = 0;
        char[] buffer = new char[1024];
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            StringBuilder s = new StringBuilder();
            while ((len = inputStreamReader.read(buffer)) > 0) {
                s.append(new String(buffer, 0, len));
                System.out.println(s.toString());
            }
            System.out.println(s.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private static void send(Socket socket) {
        System.out.println("id+name" + Thread.currentThread().getId() + Thread.currentThread().getName());

        Scanner scanner = new Scanner(System.in);

        try (OutputStream outputStream = socket.getOutputStream()) {

            while (scanner.hasNext()) {
                String next = scanner.next();
                System.out.println("next is :" + next);

                outputStream.write(next.getBytes(Charset.defaultCharset()));

                outputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
