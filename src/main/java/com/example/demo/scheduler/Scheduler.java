package com.example.demo.scheduler;

import com.example.demo.model.Playerstat;
import com.example.demo.model.Teamstat;
import com.example.demo.repository.PlayerstatRepo;
import com.example.demo.repository.TeamstatRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class Scheduler {
    private final PlayerstatRepo playerstatRepo;
    private final TeamstatRepo teamstatRepo;

    @Scheduled(fixedRate = 900000)
    public void scheduleFixedRateTask() {

        URL url = null;
        HttpURLConnection connection = null;

        try {
            url = new URL("https://ekhl.onrender.com/statistic/nhl/stats");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            System.out.println(LocalDateTime.now() + " " + connection.getRequestMethod() + " warm up request was performed with status " + connection.getResponseCode());
        } catch (IOException e) {
            System.err.println(LocalDateTime.now() + " " + "An error occurred during warm up request" + url);
        } finally {
            Objects.requireNonNull(connection).disconnect();
        }
    }

    @Scheduled(fixedRate = 7200000)
    public void scheduleFixedRateCopy() {
        try {
            BufferedReader readerP = new BufferedReader(new FileReader("fullplayerstat.txt"));
            BufferedReader readerT = new BufferedReader(new FileReader("fullteamstat.txt"));

            playerstatRepo.truncateTable();
            teamstatRepo.truncateTable();
            String c;
            while ((c = readerP.readLine()) != null) {
                playerstatRepo.save(new Playerstat(c));
            }

            while ((c = readerT.readLine()) != null) {
                teamstatRepo.save(new Teamstat(c));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
