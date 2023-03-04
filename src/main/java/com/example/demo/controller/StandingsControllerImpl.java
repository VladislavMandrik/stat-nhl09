package com.example.demo.controller;

import com.example.demo.repository.StandingsRepository;
import com.example.demo.repository.TeamStatsRepository;
import com.example.demo.service.StandingsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class StandingsControllerImpl implements StandingsController {

    private final StandingsServiceImpl standingsService;
    private final StandingsRepository standingsRepository;
    private final TeamStatsRepository teamStatsRepository;

    @PostMapping("/create")
    public String createStandings() {
        standingsService.createStandings();
        return "table-created_page";
    }

    @GetMapping("/standings")
    public String standings(Model model) {
        model.addAttribute("standings", standingsRepository.findAll());
        return "standings_page";
    }

    @GetMapping("/teamstats")
    public String teamstats(Model model) {
        model.addAttribute("teamStats",
                teamStatsRepository.findAllByOrderByPowerPlayDesc());
        return "teamStats_page";
    }
}
