package com.http.demo.groupchat;

import java.util.Scanner;

public class GroupClient {


    public static void main(String[] args) {
        ClientBase clientBase = new ClientBase();

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
