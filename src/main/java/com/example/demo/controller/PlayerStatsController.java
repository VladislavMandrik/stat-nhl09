package com.example.demo.controller;

import org.springframework.boot.Banner;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/statistic")
public interface PlayerStatsController {
    String createStats();

    String stats(Model model);

    String statsNHL(Model model);

    String goalie(Model model);

    String goalieNHL(Model model);

    String defenders(Model model);
}