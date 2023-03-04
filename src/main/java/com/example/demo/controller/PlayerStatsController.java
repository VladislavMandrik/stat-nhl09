package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/statistic")
public interface PlayerStatsController {
    String createStats();

    String stats(Model model);

    String goalie(Model model);

    String defenders(Model model);

    String transfers(Model model);
}