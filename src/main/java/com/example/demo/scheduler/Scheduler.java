package com.example.demo.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class Scheduler {
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
}
