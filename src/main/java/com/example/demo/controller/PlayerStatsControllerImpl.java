package com.example.demo.controller;

import com.example.demo.repository.*;
import com.example.demo.service.StatsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class PlayerStatsControllerImpl implements PlayerStatsController {

    private final StatsServiceImpl statsService;
    private final PlayerStatsRepository statsRepository;
    private final GoalieStatsRepository goalieStatsRepository;
    private final PlayerStatsRepositoryNHL statsRepositoryNHL;
    private final GoalieStatsRepositoryNHL goalieStatsRepositoryNHL;
    private final DefensemanStatsRepository defensemanStatsRepository;

    @GetMapping("/create")
    public String createStats() {
        statsService.createStats();
        return "stats-created_page";
    }

    @GetMapping("/stats")
    public String stats(Model model) {
        model.addAttribute("stats", statsRepository.findAllByOrderByPointsDesc());
        return "stats_page";
    }

    @GetMapping("/nhl/stats")
    public String statsNHL(Model model) {
        model.addAttribute("stats", statsRepositoryNHL.findAllByOrderByPointsDesc());
        return "statsNHL_page";
    }

    @GetMapping("/goalie")
    public String goalie(Model model) {
        model.addAttribute("goalieStats",
                goalieStatsRepository.findAllByOrderBySavePercentageDesc());
        return "goalieStats_page";
    }

    @GetMapping("/nhl/goalie")
    public String goalieNHL(Model model) {
        model.addAttribute("goalieStats",
                goalieStatsRepositoryNHL.findAllByOrderBySavePercentageDesc());
        return "goalieStatsNHL_page";
    }

    @GetMapping("/defenders")
    public String defenders(Model model) {
        model.addAttribute("defenders",
                defensemanStatsRepository.findAllByOrderByPointsDesc());
        return "defenderStats_page";
    }
}
