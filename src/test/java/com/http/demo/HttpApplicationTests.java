package com.http.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

//@SpringBootTest
class HttpApplicationTests {

    static boolean flag = false;

    @Test
    void test3() {
        String s = "pwwkew";
        System.out.println(test2(s));
    }

    int test2(String s) {


        if (s.length() == 0) {
            return 0;
        }
        if (s.length() == 2) {
            if (s.charAt(0) == s.charAt(1))
                return 1;
            return 2;
        }
        HashMap<String, Integer> map = new HashMap<>();

        List<Integer> ans = new ArrayList<>();
        map.put(String.valueOf(s.charAt(0)), 0);

        int i = 0;

        for (int j = i + 1; j < s.length(); ) {

            if (map.containsKey(String.valueOf(s.charAt(j)))) {
                ans.add(j - i);
                Integer index = map.get(String.valueOf(s.charAt(j)));
                map.put(String.valueOf(s.charAt(j)), j);
                i = index + 1;
                j = i + 1;

            } else {
                map.put(String.valueOf(s.charAt(j)), j);
                j++;
            }
        }


        ans.sort(Comparator.comparingInt(a -> a));

        return ans.get(0);
    }

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
