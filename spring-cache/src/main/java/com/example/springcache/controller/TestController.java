package com.example.springcache.controller;

import com.example.springcache.entity.Info;
import com.example.springcache.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping("")
    public List<Info> getInfos() {
        return testService.listInfos();
    }

    @RequestMapping("/del-cache")
    public String delCache() {
        testService.delCache();
        return "ok";
    }
}
