package com.http.demo.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

public class GuavaCache {

    public static CacheLoader<String, String> loadData() {

        return new CacheLoader<>() {
            @Override
            public String load(String key) throws Exception {
                return null;
            }
        };
    }

    /**
     * TTL time => update/write/read 都会刷新time
     * expireAfterAccess
     * <p>
     * time => update/write 会刷新time
     * expireAfterWrite
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
//        LoadingCache<String, String> cache = CacheBuilder.newBuilder().expireAfterAccess(2L, TimeUnit.SECONDS).maximumSize(3).build(loadData());
//
//        cache.put("1", "1");
//        cache.put("2", "2");
//        cache.put("3", "3");
//
//        cache.getIfPresent("4");
//        TimeUnit.SECONDS.sleep(1L);
//        cache.getIfPresent("3");
//        TimeUnit.SECONDS.sleep(1L);
//        cache.getIfPresent("3");
//        TimeUnit.SECONDS.sleep(1L);
//        System.out.println(cache.getIfPresent("3"));
//        System.out.println(cache.getIfPresent("2"));
//        cache.put("4", "4");
//        System.out.println(cache.getIfPresent("1"));

        LoadingCache<String, String> cache1 = CacheBuilder.newBuilder().expireAfterWrite(2L, TimeUnit.SECONDS).maximumSize(3).build(loadData());

        cache1.put("1", "1");
        cache1.put("2", "2");
        cache1.put("3", "3");

        cache1.getIfPresent("4");
        TimeUnit.SECONDS.sleep(1L);
        cache1.getIfPresent("3");
        TimeUnit.SECONDS.sleep(1L);
        cache1.getIfPresent("3");
        TimeUnit.SECONDS.sleep(1L);
        System.out.println(cache1.getIfPresent("3"));
        System.out.println(cache1.getIfPresent("2"));
    }
    /**
     * .weakKeys().weakValues() 发送gc时回收
     *
    *
     */
}
