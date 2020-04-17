package com.http.demo;

import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Main {

    //A+B
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        while (scanner.hasNextLine()) {
//            String s = scanner.nextLine();
//            String[] s1 = s.split(" ");
//
//            long l = Long.valueOf(s1[0]) + Long.valueOf(s1[1]);
//
//            System.out.println(l);
//
//        }
//
//
//    }

    @Test
    public void test1() {

        char[][] map = new char[][]{{'.', 'X', '.', '.'}, {'.', '.', '.', '.'}, {'X', 'X', '.', '.'}, {'.', '.', '.', '.'}};
        deepSearch(map, map.length, map.length);

        System.out.println(max_sum);
//        int count = 0;
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNext()) {
//
//            int i = scanner.nextInt();
//
//            char[][] len = new char[i][];
//
//            // 输入
//            for (int row = 0; row < i; row++) {
//                String s = scanner.nextLine();
//                len[row] = s.toCharArray();
//            }
//
//            //放碉堡
//            count = deepSearch(len, i, i);
//
//        }
    }

    private int count = 0;

    private int max_sum = 0;
    private int max_num = 0;

    char[][] copyMap;
    //为放过
    private int flag = 0;

    int deepSearch(char[][] map, final int row, final int col) {

        for (int i = 0; i < row; i++) {

            for (int j = 0; j < col; j++) {



                max_num =0;
                flag =0;
                if (map[i][j] == '.') {
//                    disPlay(map, row, col);

                    flag = 1;
                    count++;

                    copyMap = new char[row][col];
                    for (int a = 0; a < row; a++) {

                        for (int b = 0; b < col; b++) {
                            copyMap[a][b] = map[a][b];
                        }
                    }


                    map[i][j] = '*';



                    // 上
                    for (int k = i - 1; k >= 0; k--) {
                        if (map[k][j] == '.') {
                            map[k][j] = '1';
                        } else if (map[k][j] == 'X') {
                            break;
                        }
                    }

                    // 左
                    for (int k = j - 1; k >= 0; k--) {
                        if (map[i][k] == '.') {
                            map[i][k] = '1';
                        } else if (map[i][k] == 'X') {
                            break;
                        }
                    }

                    // 下
                    for (int k = i + 1; k < row; k++) {
                        if (map[k][j] == '.') {
                            map[k][j] = '1';
                        } else if (map[k][j] == 'X') {
                            break;
                        }
                    }

                    // 右
                    for (int k = j + 1; k < col; k++) {
                        if (map[i][k] == '.') {
                            map[i][k] = '1';
                        } else if (map[i][k] == 'X') {
                            break;
                        }
                    }

                    disPlay(map,row,col);
                    // 接着往下放
                    count = deepSearch(map, row, col) - 1;


                    for (int a = 0; a < row; a++) {
                        for (int b = 0; b < col; b++) {
                            map[a][b] = copyMap[a][b];
                        }
                    }

                }
//
                if (flag == 1) {

                    flag = 0;
                    count--;
                }

                if(max_sum<max_num){
                    max_sum = max_num;
                    max_num =0;
                }




                if (i == row - 1 && j == col - 1) {
                    if(count>max_num){
                        max_num =count;
                    }
                    return count;
                }

            }
        }
        System.out.println("*****");
        return count;
    }

    void disPlay(char map[][], final int row, final int col) {
        System.out.println("-------");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
        System.out.println("count = " + count);
        System.out.println("-------");
    }










        public static void main(String[] args) throws InterruptedException {
            Stream<Logger> s = Stream.of(
                    new Logger("Error "),
                    new Logger("Warning "),
                    new Logger("Debug "));
            ExecutorService es =
                    Executors.newCachedThreadPool();
            s.sequential().forEach(l -> es.execute(l));
            es.shutdown();
            es.awaitTermination(10, TimeUnit.SECONDS);
        }

}
class Logger implements Runnable {
    String msg;
    public Logger(String msg) {
        this.msg = msg;
    }
    public void run() {
        System.out.print(msg);
    }
}
class MyThread extends Thread {

    public void run() {
        Sync sync = new Sync();
        sync.test();
    }
}
class Sync {

    public static synchronized void test() {
        System.out.println("test开始..");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test结束..");
    }
}