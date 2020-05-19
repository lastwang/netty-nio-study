package com.http.demo.cache;

public class CacheTest {

    public static void main(String[] args) {
        VerificationCache<String, String> cache = new VerificationCache<>(3);

        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");

        System.out.println(cache);

        cache.put("4", "4");

        System.out.println(cache);

        cache.get("3");

        System.out.println(cache);

    }
}
