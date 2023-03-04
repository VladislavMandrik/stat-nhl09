package com.example.demo.controller;

import com.example.demo.repository.DefensemanStatsRepository;
import com.example.demo.repository.GoalieStatsRepository;
import com.example.demo.repository.PlayerStatsRepository;
import com.example.demo.repository.TransfersRepository;
import com.example.demo.service.StatsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class PlayerStatsControllerImpl implements PlayerStatsController {

    private final String UPLOADPLAYERSTAT_DIR = "upload playerstat/";
    private final String UPLOADTEAMSTAT_DIR = "upload teamstat/";
    private final StatsServiceImpl statsService;
    private final PlayerStatsRepository statsRepository;
    private final GoalieStatsRepository goalieStatsRepository;
    private final DefensemanStatsRepository defensemanStatsRepository;
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

    @GetMapping("/goalie")
    public String goalie(Model model) {
        model.addAttribute("goalieStats",
                goalieStatsRepository.findAllByOrderBySavePercentageDesc());
        return "goalieStats_page";
    }

    @GetMapping("/defenders")
    public String defenders(Model model) {
        model.addAttribute("defenders",
                defensemanStatsRepository.findAllByOrderByPointsDesc());
        return "defenderStats_page";
    }

    @GetMapping("/transfers")
    public String transfers(Model model) {
        model.addAttribute("transfers",
                transfersRepository.findAllByOrderByPlayerAsc());
        return "transfers_page";
    }

    @GetMapping("/uploaded")
    public String upl() {
        return "upload_page";
    }


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("playerstat") MultipartFile playerstat,
                             @RequestParam("teamstat") MultipartFile teamstat, RedirectAttributes attributes) {

//         check if file is empty
        if (playerstat.isEmpty() || teamstat.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/statistic/uploaded";
        }

        if (!playerstat.getOriginalFilename().contains("playerstat") || !teamstat.getOriginalFilename().contains("teamstat")) {
            attributes.addFlashAttribute("message", "Проверьте правильность выбора файлов teamstat и playerstat.");
            return "redirect:/statistic/uploaded";
        }

//         normalize the file path
        String fileNamePlayerstat = StringUtils.cleanPath(playerstat.getOriginalFilename());
        String fileNameTeamstat = StringUtils.cleanPath(teamstat.getOriginalFilename());

//         save the file on the local file system
        try {
            Path pathPl = Paths.get(UPLOADPLAYERSTAT_DIR + fileNamePlayerstat);
            Path pathT = Paths.get(UPLOADTEAMSTAT_DIR + fileNameTeamstat);

            Files.copy(playerstat.getInputStream(), pathPl, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(teamstat.getInputStream(), pathT, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

//         return success response
        attributes.addFlashAttribute("message", "Файлы успешно загружены, статистика обновлена!");
        statsService.createStats();
        return "redirect:/statistic/uploaded";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadFile() throws IOException {
        String filename = "fullplayerstat.txt";
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(
                file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }

    @RequestMapping(value = "/download1", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadFile1() throws IOException {
        String filename = "fullteamstat.txt";
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(
                file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }
}

