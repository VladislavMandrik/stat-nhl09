package com.example.demo.service;

import com.example.demo.model.Standings;
import com.example.demo.model.TeamStats;
import com.example.demo.repository.StandingsRepository;
import com.example.demo.repository.TeamStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class StandingsServiceImpl implements StandingsService {

    private final StandingsRepository standingsRepository;
    private final TeamStatsRepository teamStatsRepository;
    private final String PATH = "D:/KHL ECHL/teamstat";
    private final String FULLTEAMSTAT_TXT = "fullteamstat.txt";
    private final String CALENDAR = "calendar.txt";
    private final String DATA = "data.txt";

    public void createStandings() {
        List<Standings> list = new ArrayList<>();
        List<String> tableWithout = new ArrayList<>();
        List<String> tableWith = new ArrayList<>();
        List<String> plus1 = new ArrayList<>();
        List<TeamStats> listTeam = new ArrayList<>();

        Map<String, Integer> games = new TreeMap<>();
        Map<String, Integer> points = new TreeMap<>();
        Map<String, Integer> loses = new TreeMap<>();
        Map<String, Integer> wins = new TreeMap<>();
        Map<String, Integer> losesOT = new TreeMap<>();
        Map<String, Integer> goalsScored = new TreeMap<>();
        Map<String, Integer> goalsMissing = new TreeMap<>();

        Map<String, Integer> OTShots = new TreeMap<>();
        Map<String, Integer> OTGoals = new TreeMap<>();
        Map<String, String> OTGPercentage = new TreeMap<>();
        Map<String, String> OTPG = new TreeMap<>();
        Map<String, String> OTGPG = new TreeMap<>();

        getFileNames();
        getDataFromFiles();
        createCalendar();
        createMapGameTable(games, points, goalsScored, goalsMissing, loses, tableWithout, tableWith, plus1,
                wins, losesOT);

        games.forEach((team, game) -> list.add(
                new Standings(team, game, wins.get(team), loses.get(team), losesOT.get(team),
                        goalsScored.get(team), goalsMissing.get(team), points.get(team))));
        standingsRepository.saveAll(list);

        createTeamstat(OTShots, OTGoals, OTGPercentage, games, OTPG, OTGPG);
        OTShots.forEach((team, shots) -> listTeam.add(new TeamStats(team, shots, OTGoals.get(team),
                OTGPercentage.get(team), games.get(team), OTPG.get(team), OTGPG.get(team))));
        teamStatsRepository.saveAll(listTeam);
    }

    public void getFileNames() {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(DATA))) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(PATH))) {
                for (Path file : stream) {
                    if (!file.toFile().isDirectory()) {
                        printWriter.write(String.valueOf(file.getFileName()));
                        printWriter.write("\n");
                    }
                }
            } catch (IOException | DirectoryIteratorException x) {
                System.err.println(x);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getDataFromFiles() {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(FULLTEAMSTAT_TXT))) {
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

    public void createCalendar() {
        try {
            List<String> gamesTime = new ArrayList<>();
            List<String> gamesData = new ArrayList<>();

            BufferedReader readTeamstatForCalendar = new BufferedReader(new FileReader(FULLTEAMSTAT_TXT));
            BufferedReader readDataForCalendar = new BufferedReader(new FileReader(DATA));

            String c;
            int i;

            while ((c = readTeamstatForCalendar.readLine()) != null) {
                String[] words = c.split(",");
//            for (String s : words) {
//                System.out.println(s);

                createFullTeamName(words);

                if (checkInt(words[2]) && !Objects.equals(words[3], "Á") && !Objects.equals(words[3], "ÎÒ")) {
                    gamesData.add(words[1] + "," + Integer.parseInt(words[2]) + ",");
                }

                if (Objects.equals(words[3], "Á")) {
                    gamesData.add(words[1] + "," + Integer.parseInt(words[2]) + "," + words[3]);
                } else if (Objects.equals(words[3], "ÎÒ")) {
                    gamesData.add(words[1] + "," + Integer.parseInt(words[2]) + "," + words[3]);
                }
            }

            while ((c = readDataForCalendar.readLine()) != null) {
                String[] words = c.split("_");
                gamesTime.add(words[2] + "_" + words[3] + "_" + words[4]);
            }

            try (PrintWriter writer = new PrintWriter(CALENDAR)) {
                int count = 0;
                int j = 0;
                for (i = 0; i < gamesData.size(); i++) {
                    writer.write(gamesData.get(i));
                    count++;
                    if (count == 2) {
                        writer.write(" " + "," + gamesTime.get(j) + "\n");
                        count = 0;
                        j++;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createMapGameTable(Map<String, Integer> games, Map<String, Integer> points, Map<String, Integer> goalsScored,
                                   Map<String, Integer> goalsMissing, Map<String, Integer> loses,
                                   List<String> tableWithout, List<String> tableWith, List<String> plus1,
                                   Map<String, Integer> wins, Map<String, Integer> losesOT) {
        Map<String, List<String>> finalCalendar = new TreeMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(CALENDAR));

            String c;

            while ((c = reader.readLine()) != null) {
                String[] words = c.split(",");

                createFullTeamName(words);

                createGamesAndFinalCalendar(games, points, finalCalendar, words);
                createScoredAndMissingGoals(goalsScored, goalsMissing, words);
                createWinsLosesLosesOTPoints(loses, tableWithout, tableWith, plus1, words);
            }

            for (String s : tableWithout) {
                if (!wins.containsKey(s)) {
                    wins.put(s, 1);
                    points.put(s, 2);
                } else {
                    wins.put(s, wins.get(s) + 1);
                    points.put(s, points.get(s) + 2);
                }
            }

            for (String s : tableWith) {
                if (!wins.containsKey(s)) {
                    wins.put(s, 1);
                    points.put(s, 2);
                } else {
                    points.put(s, points.get(s) + 2);
                    wins.put(s, wins.get(s) + 1);
                }
            }

            for (String s : plus1) {
                if (!wins.containsKey(s)) {
                    points.put(s, 1);
                } else {
                    points.put(s, points.get(s) + 1);
                }

                if (!losesOT.containsKey(s)) {
                    losesOT.put(s, 1);
                } else {
                    losesOT.put(s, losesOT.get(s) + 1);
                }
            }

            for (Map.Entry<String, Integer> item : games.entrySet()) {
                if (!wins.containsKey(item.getKey())) {
                    wins.put(item.getKey(), 0);
                }
                if (!loses.containsKey(item.getKey())) {
                    loses.put(item.getKey(), 0);
                }
                if (!losesOT.containsKey(item.getKey())) {
                    losesOT.put(item.getKey(), 0);
                }
            }

            try (PrintWriter writer = new PrintWriter("D:/KHL ECHL/finalCalendarKHL.txt")) {
                List<String> s;
                for (Map.Entry<String, List<String>> map : finalCalendar.entrySet()) {
                    s = map.getValue();
                    Collections.sort(s);
                    writer.write(map.getKey() + "\n");
                    for (String l : s) {
//                        if (l.indexOf("7", 4) == 4 || l.indexOf(" ") == 0) {
                        writer.write(l + "\n");
//                        }
                    }
//                    for (String l : s) {
//                        if (l.indexOf("8", 4) == 4) {
//                            writer.write(l + "\n");
//                        }
//                    }
                    writer.write("\n\n");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createWinsLosesLosesOTPoints(Map<String, Integer> loses, List<String> tableWithout, List<String> tableWith, List<String> plus1, String[] words) {
        if (Integer.parseInt(words[1]) > Integer.parseInt(words[3]) && Objects.equals(words[4], " ")) {
            tableWithout.add(words[0]);
        } else if (Integer.parseInt(words[1]) < Integer.parseInt(words[3]) && Objects.equals(words[4], " ")) {
            tableWithout.add(words[2]);
        }

        if (Integer.parseInt(words[1]) > Integer.parseInt(words[3]) && !Objects.equals(words[4], " ")) {
            tableWith.add(words[0]);
            plus1.add(words[2]);

        } else if (Integer.parseInt(words[1]) < Integer.parseInt(words[3]) && !Objects.equals(words[4], " ")) {
            tableWith.add(words[2]);
            plus1.add(words[0]);
        }

        if (Integer.parseInt(words[1]) > Integer.parseInt(words[3]) && Objects.equals(words[4], " ") && !loses.containsKey(words[2])) {
            loses.put(words[2], 1);
        } else if (Integer.parseInt(words[1]) > Integer.parseInt(words[3]) && Objects.equals(words[4], " ") && loses.containsKey(words[2])) {
            loses.put(words[2], loses.get(words[2]) + 1);
        } else if (Integer.parseInt(words[1]) < Integer.parseInt(words[3]) && Objects.equals(words[4], " ") && !loses.containsKey(words[0])) {
            loses.put(words[0], 1);
        } else if (Integer.parseInt(words[1]) < Integer.parseInt(words[3]) && Objects.equals(words[4], " ") && loses.containsKey(words[0])) {
            loses.put(words[0], loses.get(words[0]) + 1);
        }
    }

    private void createGamesAndFinalCalendar(Map<String, Integer> games, Map<String, Integer> points, Map<String, List<String>> finalCalendar, String[] words) throws FileNotFoundException {
        if (!games.containsKey(words[0]) && Objects.equals(words[4], " ")) {
            games.put(words[0], 1);
            points.put(words[0], 0);
            finalCalendar.computeIfAbsent(words[0], k -> new ArrayList<>()).add(words[5] + " " + words[0] + " " + words[1] + ":" + words[3] + " " + words[2]);


        } else if (games.containsKey(words[0]) && Objects.equals(words[4], " ")) {
            games.put(words[0], games.get(words[0]) + 1);
            finalCalendar.computeIfAbsent(words[0], k -> new ArrayList<>()).add(words[5] + " " + words[0] + " " + words[1] + ":" + words[3] + " " + words[2]);
            if (!points.containsKey(words[0])) {
                points.put(words[0], 0);
            }

        } else if (!games.containsKey(words[0]) && !Objects.equals(words[4], " ")) {
            games.put(words[0], 1);
            finalCalendar.computeIfAbsent(words[0], k -> new ArrayList<>()).add(words[5] + " " + words[0] + " " + words[1] + ":" + words[3] + "( " + words[4] + ")" + " " + words[2]);
            if (!points.containsKey(words[0])) {
                points.put(words[0], 0);
            }

        } else if (games.containsKey(words[0]) && !Objects.equals(words[4], " ")) {
            games.put(words[0], games.get(words[0]) + 1);
            finalCalendar.computeIfAbsent(words[0], k -> new ArrayList<>()).add(words[5] + " " + words[0] + " " + words[1] + ":" + words[3] + "( " + words[4] + ")" + " " + words[2]);
            if (!points.containsKey(words[0])) {
                points.put(words[0], 0);
            }
        }

        if (!games.containsKey(words[2]) && Objects.equals(words[4], " ")) {
            games.put(words[2], 1);
            finalCalendar.computeIfAbsent(words[2], k -> new ArrayList<>()).add(words[5] + " " + words[0] + " " + words[1] + ":" + words[3] + " " + words[2]);
            if (!points.containsKey(words[2])) {
                points.put(words[2], 0);
            }

        } else if (games.containsKey(words[2]) && Objects.equals(words[4], " ")) {
            games.put(words[2], games.get(words[2]) + 1);
            finalCalendar.computeIfAbsent(words[2], k -> new ArrayList<>()).add(words[5] + " " + words[0] + " " + words[1] + ":" + words[3] + " " + words[2]);
            if (!points.containsKey(words[2])) {
                points.put(words[2], 0);
            }

        } else if (!games.containsKey(words[2]) && !Objects.equals(words[4], " ")) {
            games.put(words[2], 1);
            finalCalendar.computeIfAbsent(words[2], k -> new ArrayList<>()).add(words[5] + " " + words[0] + " " + words[1] + ":" + words[3] + "( " + words[4] + ")" + " " + words[2]);
            if (!points.containsKey(words[2])) {
                points.put(words[2], 0);
            }

        } else if (games.containsKey(words[2]) && !Objects.equals(words[4], " ")) {
            games.put(words[2], games.get(words[2]) + 1);
            finalCalendar.computeIfAbsent(words[2], k -> new ArrayList<>()).add(words[5] + " " + words[0] + " " + words[1] + ":" + words[3] + "( " + words[4] + ")" + " " + words[2]);
            if (!points.containsKey(words[2])) {
                points.put(words[2], 0);
            }
        }
    }

    private void createScoredAndMissingGoals(Map<String, Integer> goalsScored, Map<String, Integer> goalsMissing, String[] words) {
        if (goalsScored.containsKey(words[0])) {
            goalsScored.put(words[0], goalsScored.get(words[0]) + Integer.parseInt(words[1]));
        } else {
            goalsScored.put(words[0], Integer.parseInt(words[1]));
        }

        if (goalsScored.containsKey(words[2])) {
            goalsScored.put(words[2], goalsScored.get(words[2]) + Integer.parseInt(words[3]));
        } else {
            goalsScored.put(words[2], Integer.parseInt(words[3]));
        }

        if (goalsMissing.containsKey(words[0])) {
            goalsMissing.put(words[0], goalsMissing.get(words[0]) + Integer.parseInt(words[3]));
        } else {
            goalsMissing.put(words[0], Integer.parseInt(words[3]));
        }

        if (goalsMissing.containsKey(words[2])) {
            goalsMissing.put(words[2], goalsMissing.get(words[2]) + Integer.parseInt(words[1]));
        } else {
            goalsMissing.put(words[2], Integer.parseInt(words[1]));
        }
    }

    public boolean checkInt(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
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
        }
    }

    private void createTeamstat(Map<String, Integer> OTS, Map<String, Integer> OTG, Map<String,
            String> OTGPercentage, Map<String, Integer> games, Map<String, String> OTPG, Map<String, String> OTGPG) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FULLTEAMSTAT_TXT));

            String c;
            while ((c = reader.readLine()) != null) {
                String[] words = c.split(",");

                createFullTeamName(words);

                if (!OTS.containsKey(words[1]) && checkInt(words[6]) && checkInt(words[3])) {
                    OTS.put(words[1], Integer.valueOf(words[6]));
                    OTG.put(words[1], Integer.valueOf(words[7]));
                } else if (OTS.containsKey(words[1]) && checkInt(words[6]) && checkInt(words[3])) {
                    OTS.put(words[1], OTS.get(words[1]) + Integer.parseInt(words[6]));
                    OTG.put(words[1], OTG.get(words[1]) + Integer.parseInt(words[7]));
                } else if (!OTS.containsKey(words[1]) && checkInt(words[6]) && !checkInt(words[3])) {
                    OTS.put(words[1], Integer.valueOf(words[7]));
                    OTG.put(words[1], Integer.valueOf(words[8]));
                } else if (OTS.containsKey(words[1]) && checkInt(words[6]) && !checkInt(words[3])) {
                    OTS.put(words[1], OTS.get(words[1]) + Integer.parseInt(words[7]));
                    OTG.put(words[1], OTG.get(words[1]) + Integer.parseInt(words[8]));
                }
            }

            for (Map.Entry<String, Integer> map : OTG.entrySet()) {
                if (OTG.get(map.getKey()) != 0) {
                    if (OTS.containsKey(map.getKey())) {
                        Double i = Double.valueOf(OTG.get(map.getKey()));
                        Double j = Double.valueOf(OTS.get(map.getKey()));
                        String formattedDouble = new DecimalFormat("#0.0").format((100 * i) / (j));
                        OTGPercentage.put(map.getKey(), formattedDouble);

                        String createOTGPG = new DecimalFormat("#0.0").format(i / Double.valueOf(games.get(map.getKey())));
                        OTGPG.put(map.getKey(), createOTGPG);
                    }
                } else {
                    OTGPercentage.put(map.getKey(), "0");
                    OTGPG.put(map.getKey(), "0");
                }
            }

            for (Map.Entry<String, Integer> map : OTS.entrySet()) {
                if (OTS.get(map.getKey()) != 0) {
                    if (games.containsKey(map.getKey())) {
                        String formattedDouble = new DecimalFormat("#0.0")
                                .format(Double.valueOf(OTS.get(map.getKey())) / Double.valueOf(games.get(map.getKey())));
                        OTPG.put(map.getKey(), formattedDouble);
                    }
                } else {
                    OTPG.put(map.getKey(), "0");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}