package com.example.demo.controller;

import com.example.demo.repository.DefensemanStatsRepository;
import com.example.demo.repository.GoalieStatsRepository;
import com.example.demo.repository.PlayerStatsRepository;
import com.example.demo.repository.TeamStatsRepository;
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
    private final TeamStatsRepository teamStatsRepository;
    private final DefensemanStatsRepository defensemanStatsRepository;

    @GetMapping("/create")
    public String createStats() {
        statsService.createStats();
        return "stats-created_page";
    }

    @GetMapping("/stats")
    public String stats(Model model) {
        model.addAttribute("stats", statsRepository.findAll());
        return "stats_page";
    }

    @GetMapping("/goalie")
    public String goalie(Model model) {
        model.addAttribute("goalieStats",
                goalieStatsRepository.findGoalieStatsByGamesBetween(10, 100));
        return "goalieStats_page";
    }

    @GetMapping("/team")
    public String team(Model model) {
        model.addAttribute("teamStats",
                teamStatsRepository.findAll());
        return "teamStats_page";
    }

    @GetMapping("/defenders")
    public String defenders(Model model) {
        model.addAttribute("defenders",
                defensemanStatsRepository.findAll());
        return "defenderStats_page";
    }
}
