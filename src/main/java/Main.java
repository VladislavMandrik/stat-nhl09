import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    private static final String PATH = "D:/Замена жесткого/ВХЛ/teamstat";
    private static final String FULLTEAMSTAT_TXT = "fullteamstat.txt";
    private static final String CALENDAR = "calendarVHL.txt";

    public static void main(String[] args) throws IOException {

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(FULLTEAMSTAT_TXT))) {
            File dir = new File(PATH);
            for (File file : dir.listFiles()) {
                Scanner scanner = new Scanner(new FileInputStream(file)).useDelimiter("\\A");
                if (scanner.hasNext()) {
                    printWriter.write(scanner.next());
                }
            }
        }

        try (PrintWriter printWriter = new PrintWriter(new FileWriter("data.txt"))) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(PATH))) {
                for (Path file : stream) {
                    if (!file.toFile().isDirectory()) {
//                        System.out.println(file.getFileName());
                        printWriter.write(String.valueOf(file.getFileName()));
                        printWriter.write("\n");
                    }
                }
            } catch (IOException | DirectoryIteratorException x) {
                System.err.println(x);
            }
        }

        List<String> tableWithout = new ArrayList<>();
        List<String> plus2 = new ArrayList<>();
        List<String> plus1 = new ArrayList<>();
        List<String> data = new ArrayList<>();

        Map<String, Integer> goalsScored = new TreeMap<>();
        Map<String, Integer> missingGoals = new TreeMap<>();
        Map<String, Integer> points = new TreeMap<>();
        Map<String, Integer> games = new TreeMap<>();
        Map<String, List<String>> finalCalendar = new TreeMap<>();
        Map<String, Integer> count3 = new TreeMap<>();

        BufferedReader readTeamstatForCalendar = new BufferedReader(new FileReader(FULLTEAMSTAT_TXT));
        BufferedReader readDataForCalendar = new BufferedReader(new FileReader("data.txt"));
        createCalendar(readTeamstatForCalendar, readDataForCalendar, data);

        BufferedReader readCalendarForTable = new BufferedReader(new FileReader(CALENDAR));
        BufferedReader readCalendarForMissGoal = new BufferedReader(new FileReader(CALENDAR));
        BufferedReader CalendarReader = new BufferedReader(new FileReader(CALENDAR));

        Map<String, List<Integer>> firstParam = createMapTable(
                createListWinAndMapGameTable(tableWithout, plus2, plus1, games, finalCalendar, points, readCalendarForTable),
                createMapWinsAndPointsTable(tableWithout, plus2, plus1, count3, points),
                createMapGoalsScoredTable(goalsScored, CalendarReader),
                createMapGoalsMissingTable(missingGoals, readCalendarForMissGoal),
                points);

        new JTableExamples(hashMapToTwoDimensionalArray(firstParam));
    }

    private static boolean checkInt(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void createCalendar(BufferedReader reader, BufferedReader reader1, List<String> data) throws IOException {
        String c;
        List<String> list = new ArrayList<>();
        int i;


        while ((c = reader.readLine()) != null) {
            String[] words = c.split(",");
//            for (String s : words) {
//                System.out.println(s);

            createFullTeamName(words);

            if (checkInt(words[2]) && !Objects.equals(words[3], "Б") && !Objects.equals(words[3], "ОТ")) {
                list.add(words[1] + "," + Integer.parseInt(words[2]) + ",");
            }

            if (Objects.equals(words[3], "Б")) {
                list.add(words[1] + "," + Integer.parseInt(words[2]) + "," + words[3]);
            } else if (Objects.equals(words[3], "ОТ")) {
                list.add(words[1] + "," + Integer.parseInt(words[2]) + "," + words[3]);
            }
        }

        while ((c = reader1.readLine()) != null) {
            String[] words = c.split("__");
            data.add(words[2]);
        }

        try (
                PrintWriter writer = new PrintWriter(CALENDAR)) {
            int count = 0;
            int j = 0;
            for (i = 0; i < list.size(); i++) {
                writer.write(list.get(i));
                count++;
                if (count == 2) {
                    writer.write(" " + "," + data.get(j) + "\n");
                    count = 0;
                    j++;
                }
            }
        }
    }

    private static void createFullTeamName(String[] words) {
        if (Objects.equals(words[1], "VF")) {
            words[1] = "Динамо СПб(AK46)";
        } else if (Objects.equals(words[1], "PEL")) {
            words[1] = "Cокол(FORWARDS)";
        } else if (Objects.equals(words[1], "MVD")) {
            words[1] = "Югра(Maksik17)";
        } else if (Objects.equals(words[1], "LHC")) {
            words[1] = "СКА-Нева(Pablo)";
        } else if (Objects.equals(words[1], "EVD")) {
            words[1] = "ЦСК ВВС(pROxORd)";
        } else if (Objects.equals(words[1], "DIF")) {
            words[1] = "Буран(Gagarin)";
        } else if (Objects.equals(words[1], "FBK")) {
            words[1] = "Дизель(matey)";
        } else if (Objects.equals(words[1], "ASS")) {
            words[1] = "Горняк(Kulyash28)";
        } else if (Objects.equals(words[1], "BIF")) {
            words[1] = "Барс(TRAKTOR)";
        } else if (Objects.equals(words[1], "ROG")) {
            words[1] = "Звезда(NINTENDO)";
        } else if (Objects.equals(words[1], "LHF")) {
            words[1] = "Ростов(Darlove)";
        } else if (Objects.equals(words[1], "TOG")) {
            words[1] = "Лада(ASTAF)";
        } else if (Objects.equals(words[1], "TPS")) {
            words[1] = "Челмет(TRAKTORIST)";
        } else if (Objects.equals(words[1], "KAR")) {
            words[1] = "Рубин(NO PASSARAN)";
        } else if (Objects.equals(words[1], "MOD")) {
            words[1] = "Химик(Muscovite)";
        } else if (Objects.equals(words[1], "TIK")) {
            words[1] = "ХК Тамбов(Arcanys)";
        } else if (Objects.equals(words[1], "ILV")) {
            words[1] = "Молот-Прикамье(Deverter)";
        } else if (Objects.equals(words[1], "JYP")) {
            words[1] = "Нефтяник(Ilyuxa)";
        } else if (Objects.equals(words[1], "NOV")) {
            words[1] = "Металлург(Maxer)";
        } else if (Objects.equals(words[1], "BLU")) {
            words[1] = "Ермак(IIp9n1k)";
        } else if (Objects.equals(words[1], "SSK")) {
            words[1] = "ХК Рязань(YarLoc)";
        } else if (Objects.equals(words[1], "SAI")) {
            words[1] = "Торос(DALI)";
        }
    }

    private static Map<String, Integer> createListWinAndMapGameTable
            (List<String> tableWithout, List<String> tableWith, List<String> plus1, Map<String, Integer> games, Map<String, List<String>> finalCalendar, Map<String, Integer> points, BufferedReader
                    reader) throws IOException {
        String c;

        while ((c = reader.readLine()) != null) {
            String[] words = c.split(",");
//            for (String s : words) {
//                System.out.println(s);
//            }
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
        }

        try (PrintWriter writer = new PrintWriter("finalCalendarVHL.txt")) {
            List<String> s;
            for (Map.Entry<String, List<String>> map : finalCalendar.entrySet()) {
                s = map.getValue();
                Collections.sort(s);
                writer.write(map.getKey() + "\n");
                for (String l : s) {
                    if (l.indexOf("7", 4) == 4 || l.indexOf(" ") == 0) {
                        writer.write(l + "\n");
                    }
                }
                for (String l : s) {
                    if (l.indexOf("8", 4) == 4) {
                        writer.write(l + "\n");
                    }
                }
                writer.write("\n\n");
            }
        }
        return games;
    }

    private static Map<String, List<Integer>> createMapTable(Map<String, Integer> game,
                                                             Map<String, Integer> win,
                                                             Map<String, Integer> goalSc,
                                                             Map<String, Integer> mapGoalsMissingTable, Map<String, Integer> points) {

        Map<String, List<Integer>> dataTable = new HashMap<>();

        for (Map.Entry<String, Integer> item : game.entrySet()) {
            dataTable.put(item.getKey(), new ArrayList<>(List.of(item.getValue())));
            if (!win.containsKey(item.getKey())) {
                win.put(item.getKey(), 0);
            }
        }

        for (Map.Entry<String, Integer> item : win.entrySet()) {
            if (dataTable.containsKey(item.getKey())) {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            } else {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(0);
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            }
        }

        for (Map.Entry<String, Integer> item : goalSc.entrySet()) {
            if (dataTable.containsKey(item.getKey())) {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            } else {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            }
        }

        for (Map.Entry<String, Integer> item : mapGoalsMissingTable.entrySet()) {
            if (dataTable.containsKey(item.getKey())) {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            } else {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            }
        }

        for (Map.Entry<String, Integer> item : points.entrySet()) {
            if (dataTable.containsKey(item.getKey())) {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            } else {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            }
        }

//            VHL

        dataTable.computeIfAbsent("Динамо МО(Trusha)", k -> new ArrayList<>(Collections.nCopies(5, 0)));
        dataTable.computeIfAbsent("Торос(FUNTIK)", k -> new ArrayList<>(Collections.nCopies(5, 0)));
        dataTable.computeIfAbsent("Южный Урал(Champion1)", k -> new ArrayList<>(Collections.nCopies(5, 0)));


//        for (Map.Entry<String, List<Integer>> map : dataTable.entrySet()) {
//            System.out.println("Key: " + map.getKey() + " Value: " + map.getValue());
//        }

        return dataTable;
    }

    private static Map<String, Integer> createMapWinsAndPointsTable
            (List<String> without, List<String> plus2, List<String> plus1, Map<String, Integer> wins, Map<String, Integer> points) {
        for (String s : without) {
            if (!wins.containsKey(s)) {
                wins.put(s, 1);
                points.put(s, 2);
            } else {
                wins.put(s, wins.get(s) + 1);
                points.put(s, points.get(s) + 2);
            }
        }

        for (String s : plus2) {
            if (!wins.containsKey(s)) {
                points.put(s, 2);
            } else {
                points.put(s, points.get(s) + 2);
            }
        }

        for (String s : plus1) {
            if (!wins.containsKey(s)) {
                points.put(s, 1);
            } else {
                points.put(s, points.get(s) + 1);
            }
        }

//        for (Map.Entry<String, Integer> map : points.entrySet()) {
//            System.out.println("Key: " + map.getKey() + " Value: " + map.getValue());
//        }

        return wins;
    }

    private static Map<String, Integer> createMapGoalsScoredTable(Map<String, Integer> m, BufferedReader reader) throws
            IOException {
        String c;

        while ((c = reader.readLine()) != null) {
            String[] words = c.split(",");

            if (m.containsKey(words[0])) {
                m.put(words[0], m.get(words[0]) + Integer.parseInt(words[1]));
            } else {
                m.put(words[0], Integer.parseInt(words[1]));
            }

            if (m.containsKey(words[2])) {
                m.put(words[2], m.get(words[2]) + Integer.parseInt(words[3]));
            } else {
                m.put(words[2], Integer.parseInt(words[3]));
            }
        }
//        for (Map.Entry<String, Integer> map : m.entrySet()) {
//            System.out.println("Key: " + map.getKey() + " Value: " + map.getValue());
//        }
        return m;
    }

    private static Map<String, Integer> createMapGoalsMissingTable(Map<String, Integer> m, BufferedReader reader) throws
            IOException {
        String c;

        while ((c = reader.readLine()) != null) {
            String[] words = c.split(",");

            if (m.containsKey(words[0])) {
                m.put(words[0], m.get(words[0]) + Integer.parseInt(words[3]));
            } else {
                m.put(words[0], Integer.parseInt(words[3]));
            }

            if (m.containsKey(words[2])) {
                m.put(words[2], m.get(words[2]) + Integer.parseInt(words[1]));
            } else {
                m.put(words[2], Integer.parseInt(words[1]));
            }
        }
//        for (Map.Entry<String, Integer> map : m.entrySet()) {
//            System.out.println("Key: " + map.getKey() + " Value: " + map.getValue());
//        }
        return m;
    }

    private static Object[][] hashMapToTwoDimensionalArray(Map<String, List<Integer>> count) {

        Object[][] result = new Object[count.size()][6];
        Object[] keys = count.keySet().toArray(new Object[0]);

        List<Integer> s;
        int i = 0;

        for (Map.Entry<String, List<Integer>> m : count.entrySet()) {
            s = m.getValue();

            result[i][0] = keys[i];
            result[i][1] = String.valueOf(s.get(0));
            result[i][2] = String.valueOf(s.get(1));
            result[i][3] = String.valueOf(s.get(2));
            result[i][4] = String.valueOf(s.get(3));
            result[i][5] = String.valueOf(s.get(4));
            i++;
        }

//        System.out.println(Arrays.deepToString(result));

        return result;
    }
}