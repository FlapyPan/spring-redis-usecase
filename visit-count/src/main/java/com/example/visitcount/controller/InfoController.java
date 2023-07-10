package com.example.visitcount.controller;

import com.example.visitcount.advice.VisitCounter;
import com.example.visitcount.service.VisitCountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@VisitCounter
@RestController
public class InfoController {

    private final VisitCountService visitCountService;

    public InfoController(VisitCountService visitCountService) {
        this.visitCountService = visitCountService;
    }

    @RequestMapping("/info")
    public String info() {
        return "visit count: " + visitCountService.count();
    }
}
