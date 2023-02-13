package com.example.demo.service;

import com.example.demo.model.DefensemanStats;
import com.example.demo.model.GoalieStats;
import com.example.demo.model.PlayerStats;
import com.example.demo.repository.DefensemanStatsRepository;
import com.example.demo.repository.GoalieStatsRepository;
import com.example.demo.repository.PlayerStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {
    private final PlayerStatsRepository statsRepository;
    private final GoalieStatsRepository goalieStatsRepository;
    private final DefensemanStatsRepository defensemanStatsRepository;

    private final String PATHPLAYERSTAT = "D:/IIHF ECHL/playerstat";
    private final String PATHDEFENSEMANS = "D:/IIHF ECHL/Defenseman";
    private final String FULLPLAYERSTAT_TXT = "fullplayerstat.txt";
    private final String FULLDEFENSEMANS_TXT = "fulldefensemans.txt";
    private final int GOALS = 4;
    private final int ASSISTS = 5;

    public void createStats() {
        List<PlayerStats> list = new ArrayList<>();
        List<GoalieStats> listGoalie = new ArrayList<>();
        List<DefensemanStats> defensemansList = new ArrayList<>();

        getFullStats();
        getFullDefensemans();

        List<String> goals = createStatsForTable(GOALS);
        List<String> assists = createStatsForTable(ASSISTS);
        Map<String, Integer> gamesMap = new TreeMap<>();
        Map<String, Integer> goalsMap = new TreeMap<>();
        Map<String, Integer> assistsMap = new TreeMap<>();
        Map<String, Integer> points = new TreeMap<>();
        Map<String, String> PPG = new TreeMap<>();
        Map<String, String> defensemans = new TreeMap<>();
        Map<String, Integer> plusMinus = new TreeMap<>();

        Map<String, Integer> goalieGames = new TreeMap<>();
        Map<String, Integer> shotsAgainst = new TreeMap<>();
        Map<String, Integer> goalsAgainst = new TreeMap<>();
        Map<String, String> savePercentage = new TreeMap<>();
        Map<String, String> GAA = new TreeMap<>();
        Map<String, Double> TOI = new TreeMap<>();
        Map<String, Integer> goalieAssists = new TreeMap<>();

        createGamesAndPlusMinusMap(gamesMap, plusMinus);
        createMapFromList(goals, goalsMap);
        createMapFromList(assists, assistsMap);
        createMapFromList(createPoints(goals, assists), points);
        createPPG(gamesMap, points, PPG);

        gamesMap.forEach((player, games) ->
                list.add(new PlayerStats(player, games, goalsMap.get(player), assistsMap.get(player),
                        points.get(player), PPG.get(player), plusMinus.get(player))));
        statsRepository.saveAll(replaceNullOnZero(list));

        createDefensemans(defensemans, list, defensemansList);
        defensemanStatsRepository.saveAll(defensemansList);

        createGoalieStats(goalieGames, shotsAgainst, goalsAgainst, savePercentage, GAA, TOI, goalieAssists);
        goalieGames.forEach((player, games) ->
                listGoalie.add(new GoalieStats(player, games, shotsAgainst.get(player), goalsAgainst.get(player),
                        savePercentage.get(player), GAA.get(player), TOI.get(player), goalieAssists.get(player))));
        goalieStatsRepository.saveAll(listGoalie);
    }

    private void getFullStats() {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(FULLPLAYERSTAT_TXT))) {
            File dir = new File(PATHPLAYERSTAT);
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

    private void getFullDefensemans() {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(FULLDEFENSEMANS_TXT))) {
            File dir = new File(PATHDEFENSEMANS);
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

    private void createGamesAndPlusMinusMap(Map<String, Integer> gamesMap, Map<String, Integer> plusMinus) {

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

                if (!Objects.equals(words[2], "Goalie")) {

                    if (!plusMinus.containsKey(words[0] + " (" + words[1] + ")") && !Objects.equals(words[0], "Player Name")) {
                        plusMinus.put(words[0] + " (" + words[1] + ")", Integer.valueOf(words[7]));
                    } else if (plusMinus.containsKey(words[0] + " (" + words[1] + ")") && !Objects.equals(words[0], "Player Name")) {
                        plusMinus.put(words[0] + " (" + words[1] + ")", plusMinus.get(words[0] + " (" + words[1] + ")") + Integer.parseInt(words[7]));
                    }
                } else {
                    plusMinus.put(words[0] + " (" + words[1] + ")", Integer.valueOf("-1111"));
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
        } else if (Objects.equals(words[1], "CSK")) {
            words[1] = "ЦСКА";
        } else if (Objects.equals(words[1], "SKA")) {
            words[1] = "СКА";
        } else if (Objects.equals(words[1], "SPK")) {
            words[1] = "Сочи";
        } else if (Objects.equals(words[1], "AMK")) {
            words[1] = "Амур";
        } else if (Objects.equals(words[1], "MAN")) {
            words[1] = "Динамо Минск";
        } else if (Objects.equals(words[1], "NVG")) {
            words[1] = "Торпедо";
        } else if (Objects.equals(words[1], "SVL")) {
            words[1] = "Северсталь";
        } else if (Objects.equals(words[1], "FRL")) {
            words[1] = "Адмирал";
        } else if (Objects.equals(words[1], "YAR")) {
            words[1] = "Локомотив";
        } else if (Objects.equals(words[1], "DEG")) {
            words[1] = "Куньлунь";
        } else if (Objects.equals(words[1], "KEV")) {
            words[1] = "Барыс";
        } else if (Objects.equals(words[1], "ABK")) {
            words[1] = "Ак Барс";
        } else if (Objects.equals(words[1], "KHM")) {
            words[1] = "Спартак";
        } else if (Objects.equals(words[1], "SVT")) {
            words[1] = "Салават Юлаев";
        } else if (Objects.equals(words[1], "CKV")) {
            words[1] = "Витязь";
        } else if (Objects.equals(words[1], "ING")) {
            words[1] = "Автомобилист";
        } else if (Objects.equals(words[1], "NIZ")) {
            words[1] = "Нефтехимик";
        } else if (Objects.equals(words[1], "SIB")) {
            words[1] = "Сибирь";
        } else if (Objects.equals(words[1], "BEL")) {
            words[1] = "BLR";
        } else if (Objects.equals(words[1], "SLV")) {
            words[1] = "SVK";
        }
    }

    private void createPPG(Map<String, Integer> games, Map<String, Integer> points, Map<String, String> PPG) {
        for (Map.Entry<String, Integer> map : points.entrySet()) {
            String formattedDouble = new DecimalFormat("#0.00").format(Double.valueOf(points.get(map.getKey())) / Double.valueOf(games.get(map.getKey())));
            PPG.put(map.getKey(), formattedDouble);
        }
    }

    private List<PlayerStats> replaceNullOnZero(List<PlayerStats> list) {
        list.forEach(playerStats -> {
            if (playerStats.getGoals() == null) {
                playerStats.setGoals(0);
            }
            if (playerStats.getAssists() == null) {
                playerStats.setAssists(0);
            }
            if (playerStats.getPoints() == null) {
                playerStats.setPoints(0);
            }
            if (playerStats.getPPG() == null) {
                playerStats.setPPG("0,00");
            }
        });
        return list;
    }

    private void createGoalieStats(Map<String, Integer> games, Map<String, Integer> shotsAgainst,
                                   Map<String, Integer> goalsAgainst, Map<String, String> savePercentage,
                                   Map<String, String> GAA, Map<String, Double> TOI, Map<String, Integer> goalieAssist) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FULLPLAYERSTAT_TXT));

            String c;
            Double d = 0.0;
            while ((c = reader.readLine()) != null) {
                String[] words = c.split(",");

                createFullTeamName(words);

                if (Objects.equals(words[2], "Goalie")) {

                    if (!games.containsKey(words[0] + " (" + words[1] + ")") && !Objects.equals(words[0], "Player Name") && !Objects.equals(words[10], "0")) {
                        games.put(words[0] + " (" + words[1] + ")", 1);
                    } else if (games.containsKey(words[0] + " (" + words[1] + ")") && !Objects.equals(words[0], "Player Name") && !Objects.equals(words[10], "0")) {
                        games.put(words[0] + " (" + words[1] + ")", games.get(words[0] + " (" + words[1] + ")") + 1);
                    }

                    if (Integer.parseInt(words[10]) > 0 && !shotsAgainst.containsKey(words[0] + " (" + words[1] + ")")) {
                        shotsAgainst.put(words[0] + " (" + words[1] + ")", Integer.parseInt(words[10]));
                        goalsAgainst.put(words[0] + " (" + words[1] + ")", Integer.parseInt(words[8]));

                        if (words[3].charAt(3) == '3') {
                            d = 0.5;
                        } else if (words[3].charAt(3) == '6') {
                            d = 1.0;
                        } else if (words[3].charAt(3) == '1') {
                            d = 0.17;
                        } else if (words[3].charAt(3) == '2') {
                            d = 0.33;
                        } else if (words[3].charAt(3) == '4') {
                            d = 0.66;
                        } else if (words[3].charAt(3) == '5') {
                            d = 0.83;
                        }

                        TOI.put(words[0] + " (" + words[1] + ")", Double.valueOf(String.valueOf(words[3].charAt(0)) + String.valueOf(words[3].charAt(1))) + d);
                    } else if (Integer.parseInt(words[10]) > 0 && shotsAgainst.containsKey(words[0] + " (" + words[1] + ")")) {
                        shotsAgainst.put(words[0] + " (" + words[1] + ")", shotsAgainst.get(words[0] + " (" + words[1] + ")") + Integer.parseInt(words[10]));
                        goalsAgainst.put(words[0] + " (" + words[1] + ")", goalsAgainst.get(words[0] + " (" + words[1] + ")") + Integer.parseInt(words[8]));
                        TOI.put(words[0] + " (" + words[1] + ")", TOI.get(words[0] + " (" + words[1] + ")") + Double.valueOf(String.valueOf(words[3].charAt(0)) + String.valueOf(words[3].charAt(1))) + d);
                        System.out.println(Double.valueOf(String.valueOf(words[3].charAt(0))));
                        System.out.println();
                        System.out.println();

                        System.out.println();
                        System.out.println();
                        System.out.println();
                        System.out.println();
                        System.out.println();
                        System.out.println();
                        System.out.println();

                    }
                }
            }

            for (Map.Entry<String, Integer> map : goalsAgainst.entrySet()) {
                Double i = Double.valueOf(shotsAgainst.get(map.getKey()));
                Double j = Double.valueOf(goalsAgainst.get(map.getKey()));
                Double m = TOI.get(map.getKey());

                String formattedDouble = new DecimalFormat("#0.0").format((100 * i) / (i + j));
                savePercentage.put(map.getKey(), formattedDouble);

                String createGAA = new DecimalFormat("#0.00").format((60 * j) / m);
                GAA.put(map.getKey(), createGAA);
            }

            List<PlayerStats> goalieAssistsList = statsRepository.findPlayerStatsByPlusMinusIs("-1111");
            goalieAssistsList.forEach(playerStats -> goalieAssist.put(playerStats.getPlayer(), playerStats.getAssists()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDefensemans(Map<String, String> defensemans, List<PlayerStats> list, List<DefensemanStats> defensemansList) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FULLDEFENSEMANS_TXT));

            String c;
            while ((c = reader.readLine()) != null) {
                String[] words = c.split(",");

                if (Objects.equals(words[6], "CYK")) {
                    words[6] = "Трактор";
                } else if (Objects.equals(words[6], "DYN")) {
                    words[6] = "Динамо Москва";
                } else if (Objects.equals(words[6], "MAG")) {
                    words[6] = "Металлург Мг";
                } else if (Objects.equals(words[6], "AVG")) {
                    words[6] = "Авангард";
                } else if (Objects.equals(words[6], "CSK")) {
                    words[6] = "ЦСКА";
                } else if (Objects.equals(words[6], "SKA")) {
                    words[6] = "СКА";
                } else if (Objects.equals(words[6], "SPK")) {
                    words[6] = "Сочи";
                } else if (Objects.equals(words[6], "AMK")) {
                    words[6] = "Амур";
                } else if (Objects.equals(words[6], "MAN")) {
                    words[6] = "Динамо Минск";
                } else if (Objects.equals(words[6], "NVG")) {
                    words[6] = "Торпедо";
                } else if (Objects.equals(words[6], "SVL")) {
                    words[6] = "Северсталь";
                } else if (Objects.equals(words[6], "FRL")) {
                    words[6] = "Адмирал";
                } else if (Objects.equals(words[6], "YAR")) {
                    words[6] = "Локомотив";
                } else if (Objects.equals(words[6], "DEG")) {
                    words[6] = "Куньлунь";
                } else if (Objects.equals(words[6], "KEV")) {
                    words[6] = "Барыс";
                } else if (Objects.equals(words[6], "ABK")) {
                    words[6] = "Ак Барс";
                } else if (Objects.equals(words[6], "KHM")) {
                    words[6] = "Спартак";
                } else if (Objects.equals(words[6], "SVT")) {
                    words[6] = "Салават Юлаев";
                } else if (Objects.equals(words[6], "CKV")) {
                    words[6] = "Витязь";
                } else if (Objects.equals(words[6], "ING")) {
                    words[6] = "Автомобилист";
                } else if (Objects.equals(words[6], "NIZ")) {
                    words[6] = "Нефтехимик";
                } else if (Objects.equals(words[6], "SIB")) {
                    words[6] = "Сибирь";
                } else if (Objects.equals(words[6], "BEL")) {
                    words[6] = "BLR";
                } else if (Objects.equals(words[6], "SLV")) {
                    words[6] = "SVK";
                }

                if (!defensemans.containsKey(words[2] + " " + words[1] + " (" + words[6] + ")") && !Objects.equals(words[1], "F. Name")) {
                    defensemans.put(words[2] + " " + words[1] + " (" + words[6] + ")", "D");
                }
            }

            list.forEach(playerStats -> {
                if (defensemans.containsKey(playerStats.getPlayer())) {
                    defensemansList.add(new DefensemanStats(playerStats.getPlayer(), playerStats.getGames(),
                            playerStats.getGoals(), playerStats.getAssists(), playerStats.getPoints(), playerStats.getPPG(), playerStats.getPlusMinus()));
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}