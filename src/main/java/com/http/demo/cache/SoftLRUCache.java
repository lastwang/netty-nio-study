package com.http.demo.cache;

import com.google.common.base.Preconditions;
import com.http.demo.cache.intf.Cache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

public class SoftLRUCache<K, V> implements Cache<K, V> {

    private final int limit;

    private final SoftLRULinkMap<K, V> softLRULinkMap;

    public SoftLRUCache(int limit) {
        Preconditions.checkArgument(limit > 0, "the limit must large than zero");
        this.limit = limit;
        softLRULinkMap = new SoftLRULinkMap<>(128, limit);
    }

    private static class SoftLRULinkMap<K, V> extends LinkedHashMap<K, SoftReference<V>> {

        private final int limit;

        private SoftLRULinkMap(int initialCapacity, int limit) {
            super(initialCapacity, 0.75F, true);
            this.limit = limit;
        }


        @Override
        protected boolean removeEldestEntry(Map.Entry<K, SoftReference<V>> eldest) {
            return size() > limit;
        }
    }

    @Override
    public void putDef(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);

        softLRULinkMap.put(key,new SoftReference<V>(value));
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V get(K key) {
        SoftReference<V> value = softLRULinkMap.get(key);

        if(value == null) return null;
        return value.get();
    }

    @Override
    public V remove(K key) {
        return softLRULinkMap.remove(key).get();
    }

    @Override
    public int size() {
        return softLRULinkMap.size();
    }

    @Override
    public int limit() {
        return limit;
    }

    @Override
    public void clear() {
        softLRULinkMap.clear();
    }

}
