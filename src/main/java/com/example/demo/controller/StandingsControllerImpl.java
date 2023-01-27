package com.example.demo.controller;

import com.example.demo.repository.StandingsRepository;
import com.example.demo.service.StandingsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class StandingsControllerImpl implements StandingsController {

    private final StandingsServiceImpl statService;
    private final StandingsRepository statsRepository;

    @GetMapping("/create")
    public String createStats() {
        statService.createStandings();
        return "table-created_page";
    }

    @GetMapping("/standings")
    public String standings(Model model) {
        model.addAttribute("standings", statsRepository.findAll());
        return "standings_page";
    }
}
