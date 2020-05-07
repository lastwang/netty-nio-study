package com.http.demo.controller;

import com.http.demo.base.JsonBean;
import com.http.demo.controller.login.LoginBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("login")
    public JsonBean login(@RequestBody LoginBean user) {

        JsonBean jsonBean = new JsonBean();

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());

        subject.login(token);

        return jsonBean;
    }
}
