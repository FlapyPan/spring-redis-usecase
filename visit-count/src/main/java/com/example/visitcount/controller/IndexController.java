package com.example.visitcount.controller;

import com.example.visitcount.advice.VisitCounter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @VisitCounter
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
