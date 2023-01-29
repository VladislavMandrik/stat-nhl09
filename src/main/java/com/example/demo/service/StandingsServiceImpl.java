package com.example.demo.service;

import com.example.demo.model.Standings;
import com.example.demo.repository.StandingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@RequiredArgsConstructor
@Service
public class StandingsServiceImpl implements StandingsService {

    private final StandingsRepository standingsRepository;
    private static final String PATH = "D:/KHL ECHL/teamstat";
    private static final String FULLTEAMSTAT_TXT = "fullteamstat.txt";
    private static final String CALENDAR = "calendar.txt";
    private static final String DATA = "data.txt";

    public void createStandings() {
        getFileNames();
        getDataFromFiles();
        createCalendar();
        standingsRepository.saveAll(createMapGameTable());
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

//            createFullTeamName(words);

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

    public List<Standings> createMapGameTable() {
        Map<String, Integer> games = new TreeMap<>();
        Map<String, Integer> points = new TreeMap<>();
        Map<String, List<String>> finalCalendar = new TreeMap<>();
        Map<String, Integer> loses = new TreeMap<>();
        Map<String, Integer> wins = new TreeMap<>();
        Map<String, Integer> losesOT = new TreeMap<>();
        Map<String, Integer> goalsScored = new TreeMap<>();
        Map<String, Integer> goalsMissing = new TreeMap<>();
        List<Standings> list = new ArrayList<>();
        List<String> tableWithout = new ArrayList<>();
        List<String> tableWith = new ArrayList<>();
        List<String> plus1 = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(CALENDAR));

            String c;

            while ((c = reader.readLine()) != null) {
                String[] words = c.split(",");

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
        games.forEach((team, game) -> list.add(
                new Standings(team, game, wins.get(team), loses.get(team), losesOT.get(team),
                        goalsScored.get(team), goalsMissing.get(team), points.get(team))));
        return list;
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
}