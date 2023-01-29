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

    private final StandingsServiceImpl standingsService;
    private final StandingsRepository standingsRepository;

    @GetMapping("/create")
    public String createStandings() {
        standingsService.createStandings();
        return "table-created_page";
    }

    @GetMapping("/standings")
    public String standings(Model model) {
        model.addAttribute("standings", standingsRepository.findAll());
        return "standings_page";
    }
}
