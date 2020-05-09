package com.http.demo.controller;

import com.http.demo.base.JsonBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health/check")
    public JsonBean check() {
        return JsonBean.success();
    }
}
