package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/table")
public interface StandingsController {
    String createStandings();

    String standings(Model model);
}
