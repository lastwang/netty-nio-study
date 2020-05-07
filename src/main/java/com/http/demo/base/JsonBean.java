package com.http.demo.base;

import org.apache.http.HttpStatus;

public class JsonBean<T> {

    private Integer code = HttpStatus.SC_OK;

    private String message;

    private T data;

    public static JsonBean success() {
        return new JsonBean();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
