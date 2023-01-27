package com.example.demo.controller;

import org.springframework.ui.Model;

public interface StandingsController {
    String createStats();

    String standings(Model model);
}
