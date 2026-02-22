package com.example.demo.controller;

import com.example.demo.repository.StandingsRepository;
import com.example.demo.repository.TeamStatsRepository;
import com.example.demo.service.StandingsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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
        model.addAttribute("standings", standingsRepository.findAllByOrderByPointsDesc());
        model.addAttribute("a", standingsRepository.findByGroupA());
        model.addAttribute("b", standingsRepository.findByGroupB());
        return "standings_page";
    }

    @GetMapping("/api/standings")
    @ResponseBody
    public Map<String, Object> standingsApi() {
        Map<String, Object> response = new HashMap<>();
        response.put("standings", standingsRepository.findAllByOrderByPointsDesc());
        response.put("groupA", standingsRepository.findByGroupA());
        response.put("groupB", standingsRepository.findByGroupB());
        return response;
    }

    // Метод для Thymeleaf (HTML)
    @GetMapping("/teamstats")
    public String teamstats(Model model) {
        model.addAttribute("teamStats",
                teamStatsRepository.findAllByOrderByPowerPlayDesc());
        return "teamStats_page";
    }

    @GetMapping("/api/teamstats")
    @ResponseBody
    public Object teamstatsApi() {
        return teamStatsRepository.findAllByOrderByPowerPlayDesc();
    }

    @PostMapping("/api/create")
    @ResponseBody
    public String createStandingsApi() {
        standingsService.createStandings();
        return "Table created successfully";
    }
}
