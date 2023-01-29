package com.example.demo.controller;

import com.example.demo.repository.StatsRepository;
import com.example.demo.service.StatsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class StatsControllerImpl implements StatsController {

    private final StatsServiceImpl statsService;
    private final StatsRepository statsRepository;

    @GetMapping("/create")
    public String createStats() {
        statsService.createStats();
        return "stats-created_page.html";
    }

    @GetMapping("/stats")
    public String stats(Model model) {
        model.addAttribute("stats", statsRepository.findAll());
        return "stats_page";
    }
}
