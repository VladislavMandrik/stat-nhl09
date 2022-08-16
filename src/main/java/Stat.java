import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Stat {
    private static final String CHECK_TXT = "check.txt";
    private static final int GOAL = 4;
    private static final int ASSIST = 5;

    public static void main(String[] args) throws IOException {

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(CHECK_TXT))) {
            File dir = new File("D:/Замена жесткого/тест/плей");
            for (File file : dir.listFiles()) {
                Scanner scanner = new Scanner(new FileInputStream(file)).useDelimiter("\\A");
                if (scanner.hasNext()) {
                    printWriter.write(scanner.next());
                }
            }
        }

        List<String> scorers = new ArrayList<>();
        List<String> assists = new ArrayList<>();

        Map<String, Integer> shotsAgainst = new TreeMap<>();
        Map<String, Integer> goalsAgainst = new TreeMap<>();
        Map<String, Integer> games = new TreeMap<>();
        Map<String, Integer> goaliesGames = new TreeMap<>();
        Map<String, String> savePercentage = new TreeMap<>();

        BufferedReader reader = new BufferedReader(new FileReader(CHECK_TXT));
        BufferedReader reader1 = new BufferedReader(new FileReader(CHECK_TXT));
        BufferedReader goalieReader = new BufferedReader(new FileReader(CHECK_TXT));
        BufferedReader goalieGamesReader = new BufferedReader(new FileReader(CHECK_TXT));
        BufferedReader gamesReader = new BufferedReader(new FileReader(CHECK_TXT));


        createStat(scorers, reader, GOAL);
        createStat(assists, reader1, ASSIST);

        Map<String, Integer> goals = new TreeMap<>();
        Map<String, Integer> assistsMap = new TreeMap<>();
        Map<String, Integer> pointsMap = new TreeMap<>();

        sortedStat(scorers, goals);
        sortedStat(assists, assistsMap);
        sortedStat(unitArrays(scorers, assists), pointsMap);
        createMapGamesTable(games, gamesReader);
        createGoalieStat(shotsAgainst, goalsAgainst, savePercentage, goalieReader);
        createMapGoaliesGamesTable(goaliesGames, goalieGamesReader);

        new JTableForStat(hashMapToTwoDimensionalArray(createMapTable(games, goals, assistsMap, pointsMap)));

        new JTableForGoaliesStats(hashMapGoaliesToTwoDimensionalArray(createGoalieMapTable(shotsAgainst, goalsAgainst, goaliesGames, savePercentage)));
    }

    private static void createStat(List<String> l, BufferedReader reader, int i) throws IOException {
        String c;
        while ((c = reader.readLine()) != null) {
            String[] words = c.split(",");

            createFullTeamName(words);

            if (Objects.equals(words[i], "1")) {
                l.add(words[0] + " (" + words[1] + ")");
            } else if (Objects.equals(words[i], "2")) {
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
            } else if (Objects.equals(words[i], "3")) {
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
            } else if (Objects.equals(words[i], "4")) {
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
            } else if (Objects.equals(words[i], "5")) {
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
            } else if (Objects.equals(words[i], "6")) {
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
            } else if (Objects.equals(words[i], "7")) {
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
            } else if (Objects.equals(words[i], "8")) {
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
                l.add(words[0] + " (" + words[1] + ")");
            } else if (Objects.equals(words[i], "0")) {
            } else if (Objects.equals(words[i], "9")) {
                System.out.println("\n\n\n????? 8!!!!!!!!!!!!!!!!!!1\n\n\n");
            } else if (Objects.equals(words[i], "10")) {
                System.out.println("\n\n\n????? 8!!!!!!!!!!!!!!!!!!1\n\n\n");
            } else if (Objects.equals(words[i], "11")) {
                System.out.println("\n\n\n????? 8!!!!!!!!!!!!!!!!!!1\n\n\n");
            } else if (Objects.equals(words[i], "12")) {
                System.out.println("\n\n\n????? 8!!!!!!!!!!!!!!!!!!1\n\n\n");
            } else if (Objects.equals(words[i], "13")) {
                System.out.println("\n\n\n????? 8!!!!!!!!!!!!!!!!!!1\n\n\n");
            } else if (Objects.equals(words[i], "14")) {
                System.out.println("\n\n\n????? 8!!!!!!!!!!!!!!!!!!1\n\n\n");

            }
        }
    }

    private static void createGoalieStat(Map<String, Integer> shotsAgn, Map<String, Integer> goalsAgn, Map<String, String> svPrs, BufferedReader reader) throws IOException {
        String c;
        while ((c = reader.readLine()) != null) {
            String[] words = c.split(",");

            if (Objects.equals(words[2], "Goalie")) {

                createFullTeamName(words);

                if (Integer.parseInt(words[10]) > 0 && !shotsAgn.containsKey(words[0] + " (" + words[1] + ")")) {
                    shotsAgn.put(words[0] + " (" + words[1] + ")", Integer.parseInt(words[10]));
                    goalsAgn.put(words[0] + " (" + words[1] + ")", Integer.parseInt(words[8]));
                } else if (Integer.parseInt(words[10]) > 0 && shotsAgn.containsKey(words[0] + " (" + words[1] + ")")) {
                    shotsAgn.put(words[0] + " (" + words[1] + ")", shotsAgn.get(words[0] + " (" + words[1] + ")") + Integer.parseInt(words[10]));
                    goalsAgn.put(words[0] + " (" + words[1] + ")", goalsAgn.get(words[0] + " (" + words[1] + ")") + Integer.parseInt(words[8]));
                }
            }
        }

        for (Map.Entry<String, Integer> map : goalsAgn.entrySet()) {
            if (shotsAgn.containsKey(map.getKey())) {
                Double i = Double.valueOf(shotsAgn.get(map.getKey()));
                Double j = Double.valueOf(goalsAgn.get(map.getKey()));
                String formattedDouble = new DecimalFormat("#0.0").format((100 * i) / (i + j));
                svPrs.put(map.getKey(), formattedDouble);
            }
        }

//        for (Map.Entry<String, String> map : svPrs.entrySet()) {
//            System.out.println("Key: " + map.getKey() + " Value: " + map.getValue());
//        }
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
        } else if (Objects.equals(words[1], "IFK")) {
            words[1] = "Зауралье(Stinggy)";
        }
    }

    private static List<String> unitArrays(List<String> scorers, List<String> assists) {
        List<String> l = new ArrayList<>();
        l.addAll(scorers);
        l.addAll(assists);
        return l;
    }

    private static void sortedStat(List<String> l, Map<String, Integer> count) {
        for (String s : l) {
            if (!count.containsKey(s)) {
                count.put(s, 1);
            } else {
                count.put(s, count.get(s) + 1);
            }
        }
    }

    private static void createMapGamesTable
            (Map<String, Integer> games, BufferedReader reader) throws IOException {
        String c;

        while ((c = reader.readLine()) != null) {
            String[] words = c.split(",");
//            for (String s : words) {
//                System.out.println(s);
//            }

            createFullTeamName(words);

            if (!games.containsKey(words[0] + " (" + words[1] + ")") && !Objects.equals(words[0], "Player Name")) {
                games.put(words[0] + " (" + words[1] + ")", 1);
            } else if (games.containsKey(words[0] + " (" + words[1] + ")") && !Objects.equals(words[0], "Player Name")) {
                games.put(words[0] + " (" + words[1] + ")", games.get(words[0] + " (" + words[1] + ")") + 1);
            }

//            for (Map.Entry<String, Integer> map : games.entrySet()) {
//                System.out.println("Key: " + map.getKey() + " Value: " + map.getValue());
//            }
        }
    }

    private static void createMapGoaliesGamesTable
            (Map<String, Integer> games, BufferedReader reader) throws IOException {
        String c;

        while ((c = reader.readLine()) != null) {
            String[] words = c.split(",");
//            for (String s : words) {
//                System.out.println(s);
//            }

            createFullTeamName(words);

            if (Objects.equals(words[2], "Goalie")) {

                if (!games.containsKey(words[0] + " (" + words[1] + ")") && !Objects.equals(words[0], "Player Name") && !Objects.equals(words[10], "0")) {
                    games.put(words[0] + " (" + words[1] + ")", 1);
                } else if (games.containsKey(words[0] + " (" + words[1] + ")") && !Objects.equals(words[0], "Player Name") && !Objects.equals(words[10], "0")) {
                    games.put(words[0] + " (" + words[1] + ")", games.get(words[0] + " (" + words[1] + ")") + 1);
                }
            }
        }

//        for (Map.Entry<String, Integer> map : games.entrySet()) {
//            System.out.println("Key: " + map.getKey() + " Value: " + map.getValue());
//        }
    }

    private static Map<String, List<Object>> createGoalieMapTable(Map<String, Integer> shotsAg, Map<String, Integer> goalsAg, Map<String, Integer> games, Map<String, String> svPrc) {

        Map<String, List<Object>> dataTable = new HashMap<>();

        for (Map.Entry<String, Integer> item : games.entrySet()) {
            dataTable.put(item.getKey(), new ArrayList<>(List.of(item.getValue())));
        }

        for (Map.Entry<String, Integer> item : shotsAg.entrySet()) {
            if (dataTable.containsKey(item.getKey())) {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            } else {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(0);
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            }
        }

        for (Map.Entry<String, Integer> item : goalsAg.entrySet()) {
            if (dataTable.containsKey(item.getKey())) {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            }
        }

        for (Map.Entry<String, String> item : svPrc.entrySet()) {
            if (dataTable.containsKey(item.getKey())) {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            } else {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            }
        }

//        for (Map.Entry<String, List<Object>> item : dataTable.entrySet()) {
//            System.out.println(item);
//        }

        return dataTable;
    }

    private static Map<String, List<Integer>> createMapTable(Map<String, Integer> games, Map<String, Integer> goals,
                                                             Map<String, Integer> assists,
                                                             Map<String, Integer> points) {

        Map<String, List<Integer>> dataTable = new HashMap<>();

        for (Map.Entry<String, Integer> item : games.entrySet()) {
            dataTable.put(item.getKey(), new ArrayList<>(List.of(item.getValue())));
            if (!goals.containsKey(item.getKey())) {
                goals.put(item.getKey(), 0);
            }
            if (!points.containsKey(item.getKey())) {
                points.put(item.getKey(), 0);
            }
            if (!assists.containsKey(item.getKey())) {
                assists.put(item.getKey(), 0);
            }
        }

        for (Map.Entry<String, Integer> item : goals.entrySet()) {
            if (dataTable.containsKey(item.getKey())) {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            } else {
                dataTable.computeIfAbsent(item.getKey(), k -> new ArrayList<>()).add(item.getValue());
            }
        }

        for (Map.Entry<String, Integer> item : assists.entrySet()) {
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

//        for (Map.Entry<String, List<Integer>> item : dataTable.entrySet()) {
//            System.out.println(item);
//        }

        return dataTable;
    }

    private static Object[][] hashMapToTwoDimensionalArray(Map<String, List<Integer>> count) {

        Object[][] result = new Object[count.size()][5];
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
            i++;
        }

//        System.out.println(Arrays.deepToString(result));

        return result;
    }

    private static Object[][] hashMapGoaliesToTwoDimensionalArray(Map<String, List<Object>> count) {

        Object[][] result = new Object[count.size()][5];
        Object[] keys = count.keySet().toArray(new Object[0]);

        List<Object> s;
        int i = 0;

        for (Map.Entry<String, List<Object>> m : count.entrySet()) {
            s = m.getValue();

            result[i][0] = keys[i];
            result[i][1] = String.valueOf(s.get(0));
            result[i][2] = String.valueOf(s.get(1));
            result[i][3] = String.valueOf(s.get(2));
            result[i][4] = String.valueOf(s.get(3));
            i++;
        }

//        System.out.println(Arrays.deepToString(result));

        return result;
    }
}
