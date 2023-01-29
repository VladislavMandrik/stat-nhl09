package com.example.demo.service;

import com.example.demo.model.Stats;
import com.example.demo.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@RequiredArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    private static final String PATH = "D:/KHL ECHL/playerstat";
    private static final String FULLPLAYERSTAT_TXT = "fullplayerstat.txt";
    private static final int GOALS = 4;
    private static final int ASSISTS = 5;

    public void createStats() {
        List<Stats> list = new ArrayList<>();

        getFullStats();

        List<String> goals = createStatsForTable(GOALS);
        List<String> assists = createStatsForTable(ASSISTS);
        Map<String, Integer> gamesMap = new TreeMap<>();
        Map<String, Integer> goalsMap = new TreeMap<>();
        Map<String, Integer> assistsMap = new TreeMap<>();
        Map<String, Integer> points = new TreeMap<>();

        createGamesMap(gamesMap);
        createMapFromList(goals, goalsMap);
        createMapFromList(assists, assistsMap);
        createMapFromList(createPoints(goals, assists), points);

        gamesMap.forEach((player, games) -> list.add(new Stats(player, games, goalsMap.get(player), assistsMap.get(player), points.get(player))));
        statsRepository.saveAll(list);
    }

    private void getFullStats() {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(FULLPLAYERSTAT_TXT))) {
            File dir = new File(PATH);
            for (File file : dir.listFiles()) {
                Scanner scanner = new Scanner(new FileInputStream(file)).useDelimiter("\\A");
                if (scanner.hasNext()) {
                    printWriter.write(scanner.next());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> createStatsForTable(int i) {
        try {
            List<String> statsList = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(FULLPLAYERSTAT_TXT));

            String c;
            while ((c = reader.readLine()) != null) {
                String[] words = c.split(",");

                createFullTeamName(words);

                if (Objects.equals(words[i], "1")) {
                    statsList.add(words[0] + " (" + words[1] + ")");
                } else if (Objects.equals(words[i], "2")) {
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                } else if (Objects.equals(words[i], "3")) {
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                } else if (Objects.equals(words[i], "4")) {
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                } else if (Objects.equals(words[i], "5")) {
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                } else if (Objects.equals(words[i], "6")) {
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                } else if (Objects.equals(words[i], "7")) {
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                } else if (Objects.equals(words[i], "8")) {
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                    statsList.add(words[0] + " (" + words[1] + ")");
                } else if (Objects.equals(words[i], "9")) {
                    throw new RuntimeException("Много!!!!!!!!!!!!!!!1");
                } else if (Objects.equals(words[i], "10")) {
                    throw new RuntimeException("Много!!!!!!!!!!!!!!!1");
                } else if (Objects.equals(words[i], "11")) {
                    throw new RuntimeException("Много!!!!!!!!!!!!!!!1");
                } else if (Objects.equals(words[i], "12")) {
                    throw new RuntimeException("Много!!!!!!!!!!!!!!!1");
                } else if (Objects.equals(words[i], "13")) {
                    throw new RuntimeException("Много!!!!!!!!!!!!!!!1");
                } else if (Objects.equals(words[i], "14")) {
                    throw new RuntimeException("Много!!!!!!!!!!!!!!!1");
                }
            }
            return statsList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createGamesMap(Map<String, Integer> gamesMap) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FULLPLAYERSTAT_TXT));

            String c;
            while ((c = reader.readLine()) != null) {
                String[] words = c.split(",");

                createFullTeamName(words);

                if (!gamesMap.containsKey(words[0] + " (" + words[1] + ")") && !Objects.equals(words[0], "Player Name")) {
                    gamesMap.put(words[0] + " (" + words[1] + ")", 1);
                } else if (gamesMap.containsKey(words[0] + " (" + words[1] + ")") && !Objects.equals(words[0], "Player Name")) {
                    gamesMap.put(words[0] + " (" + words[1] + ")", gamesMap.get(words[0] + " (" + words[1] + ")") + 1);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> createPoints(List<String> scorers, List<String> assists) {
        List<String> pointsList = new ArrayList<>();

        pointsList.addAll(scorers);
        pointsList.addAll(assists);
        return pointsList;
    }

    private void createMapFromList(List<String> scorers, Map<String, Integer> m) {
        for (String s : scorers) {
            if (!m.containsKey(s)) {
                m.put(s, 1);
            } else {
                m.put(s, m.get(s) + 1);
            }
        }
    }

    private void createFullTeamName(String[] words) {
        if (Objects.equals(words[1], "CYK")) {
            words[1] = "Трактор";
        } else if (Objects.equals(words[1], "DYN")) {
            words[1] = "Динамо Москва";
        } else if (Objects.equals(words[1], "MAG")) {
            words[1] = "Металлург Мг";
        } else if (Objects.equals(words[1], "AVG")) {
            words[1] = "Авангард";
        }
    }
}
