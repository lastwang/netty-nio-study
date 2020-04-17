package com.http.demo;

public class DynamicProgramming {

    public static void main(String[] args) {

//        System.out.println(fibonacci(100));
//        System.out.println(count);
        System.out.println(fibonacciS(100));
        System.out.println(countS);
        System.out.println(dyPro(100));
        System.out.println(countT);
    }

    // 斐波那数列
    static long count = 0;
    static long countS = 0;
    static long countT = 0;

    public synchronized static long fibonacci(long n) {
        count++;

        if (n == 1 || n == 2) return 1;

        return fibonacci(n - 1) + fibonacci(n - 2);

    }

    public static long fibonacciS(int n) {

        long[] men = new long[n + 1];
        for (int i = 0; i < n + 1; i++) {
            men[i] = 0;
        }

        return helper(men, n);
    }

    static long helper(long[] men, int n) {

        if (n == 1 || n == 2) return 1;
        if (men[n] == 0 && n > 2) {
            countS++;
            men[n] = helper(men, n - 1) + helper(men, n - 2);
        }

        return men[n];
    }

    static long dyPro(int n) {
        if (n < 3) return 1;
        long pre = 1;
        long prw = 1;

        for (int i = 0; i < n - 2; i++) {
            countT++;
            long prn = pre + prw;
            pre = prw;
            prw = prn;
        }

        return prw;


    }


}
