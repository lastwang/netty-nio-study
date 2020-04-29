package com.http.demo.base;

import java.util.List;

public interface BaseMapper<T, K> {

    int insert(T t);

    List<T> getAll();

    List<T> getObjectsByRecord(T t);

    T getOneByPrimary(K k);

    int updateByPrimary(T t);

    int deleteByPrimary(K k);

    int deleteByRecord(T t);

}
