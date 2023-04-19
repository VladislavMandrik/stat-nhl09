package com.example.demo.controller;

import com.example.demo.model.Res;
import com.example.demo.model.Results;
import com.example.demo.repository.*;
import com.example.demo.service.StandingsServiceImpl;
import com.example.demo.service.StatsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class PlayerStatsControllerImpl implements PlayerStatsController {
    Logger logger = LoggerFactory.getLogger(PlayerStatsControllerImpl.class);
    private final String UPLOADPLAYERSTAT_DIR = "upload playerstat/";
    private final String UPLOADTEAMSTAT_DIR = "upload teamstat/";
    private final StatsServiceImpl statsService;
    private final PlayerStatsRepository statsRepository;
    private final GoalieStatsRepository goalieStatsRepository;
    private final DefensemanStatsRepository defensemanStatsRepository;
    private final TransfersRepository transfersRepository;
    private final ResRepository resRepository;
    private final StandingsServiceImpl standingsService;
    public String total;

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
    public String upl(Map<String, Object> model) {
        Results results = new Results();
        model.put("results", results);
        return "upload_page";
    }

    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute("results") Results results,
                             @RequestParam("playerstat") MultipartFile playerstat,
                             @RequestParam("teamstat") MultipartFile teamstat, RedirectAttributes attributes) throws IOException {

        if (playerstat.isEmpty() || teamstat.isEmpty()) {
            attributes.addFlashAttribute("message", "Необходимо выбрать и загрузить playerstat и teamstat.");
            return "redirect:/statistic/uploaded";
        }

        if (!playerstat.getOriginalFilename().contains("playerstat") || !teamstat.getOriginalFilename().contains("teamstat")) {
            attributes.addFlashAttribute("message", "Проверьте правильность выбора файлов teamstat и playerstat.");
            return "redirect:/statistic/uploaded";
        }

        String fileNamePlayerstat = StringUtils.cleanPath(playerstat.getOriginalFilename());
        String fileNameTeamstat = StringUtils.cleanPath(teamstat.getOriginalFilename());

        InputStream inputStream = teamstat.getInputStream();

        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while (result != -1) {
            buf.write((byte) result);
            result = bis.read();
        }

        if (!Objects.equals(results.getTotal(), " ")) {
            String resultTeamstat = buf.toString();
            int start = resultTeamstat.indexOf("Away,") + 4;
            int to = start + 29;
            char[] dst = new char[to - start];
            resultTeamstat.getChars(start, to, dst, 0);
            resRepository.save(new Res(Arrays.toString(dst)));
        }

        try {
            Path pathPl = Paths.get(UPLOADPLAYERSTAT_DIR + fileNamePlayerstat);
            Path pathT = Paths.get(UPLOADTEAMSTAT_DIR + fileNameTeamstat);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            logger.warn(authentication.getName() + " " + results.getFirstTeam() + " " + results.getFirstGoals() + " " +
                    results.getTotal() + " " + results.getSecondGoals() + " " + results.getSecondTeam());
            total = results.getTotal();
            logger.warn(authentication.getName() + " " + fileNamePlayerstat);
            logger.warn(authentication.getName() + " " + fileNameTeamstat);


            Files.copy(playerstat.getInputStream(), pathPl, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(teamstat.getInputStream(), pathT, StandardCopyOption.REPLACE_EXISTING);
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        attributes.addFlashAttribute("message", "Файлы успешно загружены, статистика обновлена!");
        statsService.createStats();
        standingsService.createStandings();
        return "redirect:/statistic/uploaded";
    }

    @GetMapping("/downloadP")
    public ResponseEntity<Object> downloadFilePlayerstat() throws IOException {
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

    @GetMapping("/downloadT")
    public ResponseEntity<Object> downloadFileTeamstat() throws IOException {
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

    @GetMapping("/logs")
    public ResponseEntity<Object> logs() throws IOException {
        String filename = "log.txt";
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

    @GetMapping("/downloadCalendar")
    public ResponseEntity<Object> downloadFileCalendar() throws IOException {
        String filename = "finalCalendar.txt";
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