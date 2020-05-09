package com.http.demo.controller;

import com.http.demo.base.JsonBean;
import com.http.demo.controller.requset.ServerMsg;
import com.http.demo.service.MsgHandlerSv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerMsgController {

    @Autowired
    private MsgHandlerSv msgHandlerSv;

    @PostMapping("serverMsg")
    public JsonBean serverMsg (ServerMsg serverMsg) {


        return JsonBean.success();
    }
}
