package com.example.requestthrottling.controller;

import com.example.requestthrottling.advice.RateLimit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RateLimit(limit = 1, timeout = 5)
    @RequestMapping("")
    public String index() {
        return "hello";
    }
}
