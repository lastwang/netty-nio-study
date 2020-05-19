package com.http.demo.cache;


import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CacheTest {
    //        VerificationLinkCache<String, String> cache = new VerificationLinkCache<>(3);
//
//        cache.put("1", "1");
//        cache.put("2", "2");
//        cache.put("3", "3");
//
//        System.out.println(cache);
//
//        cache.put("4", "4");
//
//        System.out.println(cache);
//
//        cache.get("3");
//
//        System.out.println(cache);

    // 当应用内存将要被耗尽的时候-->即将发生OOM，垃圾处理器就会把它带走。这么看来，软应用的生命周期还是很长的，可以用来做缓存处理

    void test() throws InterruptedException {
        List<SoftReference<CacheTest>> ls = new ArrayList<>();
        int i = 1;
        for (; ; ) {
            SoftReference<CacheTest> cacheTest = new SoftReference<>(new CacheTest(i));

            ls.add(cacheTest);
            System.out.println("创建第" + i + "对象");
            i++;
            TimeUnit.MILLISECONDS.sleep(500);

        }
    }

    // the reference will be collect when gc
    void test2() throws InterruptedException {
        List<WeakReference<CacheTest>> ls = new ArrayList<>();
        int i = 1;
        for (; ; ) {
            WeakReference<CacheTest> cacheTest = new WeakReference<>(new CacheTest(i));

            ls.add(cacheTest);
            System.out.println("创建第" + i + "对象");
            i++;
            TimeUnit.MILLISECONDS.sleep(500);

        }
    }

    void test3() {
        CacheTest test = new CacheTest(1);
        SoftReference<CacheTest> softReference = new SoftReference<>(test);

        test = null;

        System.out.println(Objects.requireNonNull(softReference.get()).index);
    }

    void test4() throws InterruptedException {
        CacheTest test = new CacheTest(1);
        ReferenceQueue<CacheTest> q = new ReferenceQueue<>();
        MyPhantomReference<CacheTest> phantomReference = new MyPhantomReference<>(test, q, 1);

        test = null;
        System.out.println(phantomReference.get());

        System.gc();

        Reference<? extends CacheTest> remove = q.remove();
        ((MyPhantomReference) remove).doSomething();
    }

    public static void main(String[] args) throws InterruptedException {
        SoftLRUCache<String, byte[]> softLRUCache = new SoftLRUCache<>(100);

        for (int i = 0; i < 100; i++) {
            softLRUCache.putDef(String.valueOf(i), new byte[1024 * 1024 * 2]);

            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println("index:" + i + " join cache");
        }
    }

    private byte[] cache = new byte[1024 * 1024];

    public static class MyPhantomReference<T> extends PhantomReference<T> {

        private int index;

        public MyPhantomReference(T referent, ReferenceQueue<? super T> q, int index) {
            super(referent, q);
            this.index = index;
        }

        public void doSomething() {
            System.out.println("this index:" + index + " is do some thing");
        }
    }

    private int index;

    public CacheTest(int index) {
        this.index = index;
    }

    public CacheTest(byte[] cache) {
        this.cache = cache;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("index =" + index + ";will be 回收");
    }
}
