package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/statistic")
public interface StatsController {
    String createStats();

    String stats(Model model);
}