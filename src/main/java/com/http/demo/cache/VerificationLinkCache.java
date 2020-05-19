package com.http.demo.cache;

import com.google.common.base.Preconditions;
import com.http.demo.cache.intf.Cache;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@NotThreadSafe
public class VerificationLinkCache<K, V> implements Cache<K, V> {

    private final LinkedList<K> keys = new LinkedList<>();

    private final Map<K, V> cache = new HashMap<>();

    private final int limit;

    public VerificationLinkCache(int limit) {
        this.limit = limit;
    }

    @Override
    public V put(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);

        if (limit <= size()) {
            K oldkey = keys.removeFirst();
            cache.remove(oldkey);
        }
        keys.addLast(key);

        return cache.put(key, value);
    }

    @Override
    public V get(K key) {
        boolean exit = keys.remove(key);

        if (!exit)
            return null;

        keys.addLast(key);

        return cache.get(key);
    }

    @Override
    public V remove(K key) {

        keys.remove(key);
        return cache.remove(key);
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public int limit() {
        return limit;
    }

    @Override
    public void clear() {
        keys.clear();
        cache.clear();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for(K key: keys){
            builder.append(key).append("=").append(cache.get(key)).append(";");
        }

        return builder.toString();
    }
}
