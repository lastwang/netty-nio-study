package com.http.demo.user;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class User {

    private Integer userId;

    private String userName;

    private String password = "123";

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userId", userId)
                .append("userName", userName)
                .append("password", password)
                .toString();
    }
}
