package com.example.demo.controller;

import com.example.demo.repository.DefensemanStatsRepository;
import com.example.demo.repository.GoalieStatsRepository;
import com.example.demo.repository.PlayerStatsRepository;
import com.example.demo.repository.TransfersRepository;
import com.example.demo.service.StatsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
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

@RequiredArgsConstructor
@Controller
public class PlayerStatsControllerImpl implements PlayerStatsController {

    private final String UPLOAD_DIR = "upload/";
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
}

