package com.http.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HttpApplicationTests {

    static boolean flag = false;

    @Test
    void contextLoads() {
    }

    void test() throws InterruptedException {
        Thread wait = new Thread(() -> {
            while (!flag) {

//                System.out.println("wait");


            }
            System.out.println("-----stop------");
        });
        wait.start();

        Thread.sleep(10);
        flag = true;

        System.out.println("主线程完了");
        Thread.sleep(100000000);
    }

    static int a = 0;


    public static void main(String[] args) throws InterruptedException {


        for (int i = 0; i < 400; i++) {
            Thread A = new Thread(() -> {
                a = 1;
                flag = true;
            });


            Thread B = new Thread(() -> {
                if (flag) {
                    a = a * 1;
                }
                if (a == 0) {
                    System.out.println("ok");
                } else {
                    System.out.println("no");
                }
            });
            A.start();
            B.start();
            A.join();
            B.join();

            a = 0;
            flag = false;
        }
    }

}
