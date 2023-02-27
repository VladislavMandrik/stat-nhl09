package com.example.demo.controller;

import com.example.demo.repository.*;
import com.example.demo.service.StatsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RequiredArgsConstructor
@Controller
public class PlayerStatsControllerImpl implements PlayerStatsController {

    private final String UPLOAD_DIR = "upload/";
    private final StatsServiceImpl statsService;
    private final PlayerStatsRepository statsRepository;
    private final GoalieStatsRepository goalieStatsRepository;
    private final PlayerStatsRepositoryNHL statsRepositoryNHL;
    private final GoalieStatsRepositoryNHL goalieStatsRepositoryNHL;
    private final DefensemanStatsRepository defensemanStatsRepository;
    private final DefensemanStatsRepositoryNHL defensemanStatsRepositoryNHL;
    private final TransfersRepository transfersRepository;

    @PostMapping("/create")
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
                goalieStatsRepositoryNHL.findGoalieStatsByGamesBetween(11, 150));
        return "goalieStatsNHL_page";
    }

    @GetMapping("/defenders")
    public String defenders(Model model) {
        model.addAttribute("defenders",
                defensemanStatsRepository.findAllByOrderByPointsDesc());
        return "defenderStats_page";
    }

    @GetMapping("/nhl/defenders")
    public String defendersNHL(Model model) {
        model.addAttribute("defenders",
                defensemanStatsRepositoryNHL.findAllByOrderByPointsDesc());
        return "defenderStatsNHL_page";
    }

    @GetMapping("/nhl/transfers")
    public String transfersNHL(Model model) {
        model.addAttribute("transfers",
                transfersRepository.findAllByOrderByPlayerAsc());
        return "transfers_page";
    }

    @GetMapping("/uploaded")
    public String upl() {
        return "upload_page";
    }






    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

//         check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/statistic/uploaded";
        }

//         normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

//         save the file on the local file system
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

//         return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/statistic/uploaded";
    }
}
