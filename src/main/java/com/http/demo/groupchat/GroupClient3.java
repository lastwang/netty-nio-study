package com.http.demo.groupchat;

import java.util.Scanner;

public class GroupClient3 {

    private static final String HOST = "127.0.0.1";
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int PORT = 9803;

    private static final int SERVER_PORT = 9800;

    public static void main(String[] args) {
        ClientBase clientBase = new ClientBase("client3", HOST, PORT, SERVER_HOST, SERVER_PORT);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> clientBase.close()));

        new Thread(() -> {
            while (true) {
                clientBase.receviedMsg();
                try {
                    Thread.sleep(3000);
//                    log.info("每隔3秒接受信息");
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {

            String msg = scanner.nextLine();

            clientBase.sendMsg(msg);
        }

    }


}
