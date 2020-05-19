package com.http.demo.cache.intf;

public interface Cache<K, V> {

    V put(K key, V value);

    default void putDef(K key, V value) {
    }

    V get(K key);

    V remove(K key);

    int size();

    int limit();

    void clear();
}
