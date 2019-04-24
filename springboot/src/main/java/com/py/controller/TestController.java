package com.py.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @作者: zgy
 * @描述:
 */
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello  springboot 项目创建成功啦";
    }

}
