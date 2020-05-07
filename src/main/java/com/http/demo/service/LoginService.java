package com.http.demo.service;

import com.http.demo.user.User;
import com.http.demo.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;

    public User getUserByName(User userName) {
        List<User> objectsByRecord = userMapper.getObjectsByRecord(userName);

        if (objectsByRecord != null && objectsByRecord.size() > 0)
            return objectsByRecord.get(0);
        return null;
    }
}
