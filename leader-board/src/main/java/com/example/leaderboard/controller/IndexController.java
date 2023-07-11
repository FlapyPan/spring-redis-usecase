package com.example.leaderboard.controller;

import com.example.leaderboard.entity.Score;
import com.example.leaderboard.service.ScoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    private final ScoreService scoreService;

    public IndexController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @RequestMapping("")
    public String indexPage(Model model) throws JsonProcessingException {
        List<Score> scores = scoreService.leaderBoard();
        model.addAttribute("scores", scores);
        return "index";
    }
}
