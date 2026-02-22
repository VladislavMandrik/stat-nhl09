package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RequestMapping("/statistic")
public interface PlayerStatsController {
    String createStats();
    String stats(Model model);
    String goalie(Model model);
    String defenders(Model model);
    String transfers(Model model);
    ResponseEntity<Object> downloadFilePlayerstat() throws IOException;
    ResponseEntity<Object> downloadFileTeamstat() throws IOException;
    ResponseEntity<Object> logs() throws IOException;
}