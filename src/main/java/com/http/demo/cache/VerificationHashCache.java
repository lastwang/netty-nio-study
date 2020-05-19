package com.http.demo.cache;

import com.google.common.base.Preconditions;
import com.http.demo.cache.intf.Cache;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 验证信息不会频繁更新
 * 将验证消息加载到本地jvm内存中,减少不必要的性能损害
 */
@NotThreadSafe
public class VerificationHashCache<K, V> implements Cache<K, V> {

    private final VerificationMap<K, V> cache;

    public VerificationHashCache(int limit) {
        Preconditions.checkArgument(limit > 0, "limit must large than zero");
        cache = new VerificationMap<>(limit);
        this.limit = limit;
    }

    public VerificationHashCache() {
        this.limit = 3;
        cache = new VerificationMap<>(limit);
    }

    private final int limit;

    private static class VerificationMap<K, V> extends LinkedHashMap<K, V> {

        private final int limit;

        private VerificationMap(int limit) {
            super(16, 0.75F, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > limit;
        }
    }

    @Override
    public V put(K key, V value) {
       return cache.put(key, value);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public V remove(K key) {
        return cache.remove(key);
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public int limit() {
        return limit;
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
