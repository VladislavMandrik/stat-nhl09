package com.example.demo;

import java.io.*;
import java.util.*;

public class UnitePlayerstat {
    public static void main(String[] args) {

        final String PLAYERSTAT = "E:/KHL ECHL/Unite/playerstat";
        final String UNITEPLAYERSTAT_TXT = "uniteplayerstat.txt";
        List<String> str = new ArrayList<>();

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(UNITEPLAYERSTAT_TXT))) {
            File dir = new File(PLAYERSTAT);
            for (File file : dir.listFiles()) {
                Scanner scanner = new Scanner(new FileInputStream(file)).useDelimiter("\\A");
                if (scanner.hasNext()) {
                    printWriter.write(scanner.next());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Map<Integer, PlayerstatUnite> map = new TreeMap<>();
            Map<Integer, GoalieUnite> mapG = new TreeMap<>();
            Map<Integer, PlayerstatUnite> newMap = new TreeMap<>();
            Map<Integer, GoalieUnite> newMapG = new TreeMap<>();

            BufferedReader reader = new BufferedReader(new FileReader(UNITEPLAYERSTAT_TXT));

            String c;
            int count = 0;
            int countG = 0;

            while ((c = reader.readLine()) != null) {
                String[] words = c.split(",");

                if (!Objects.equals(words[0], "Player Name") && Objects.equals(words[2], "Player")) {
                    map.put(count, new PlayerstatUnite(words[0], words[1], words[2], words[3], Integer.valueOf(words[4]),
                            Integer.valueOf(words[5]), Integer.valueOf(words[6]), Integer.valueOf(words[7]), Integer.valueOf(words[8]),
                            Integer.valueOf(words[9]), Integer.valueOf(words[10]), Integer.valueOf(words[11]), Integer.valueOf(words[12]),
                            Integer.valueOf(words[13])));
                    count++;
                } else if (Objects.equals(words[1], "Team")) {
                    str.add(0, words[0] + "," + words[1] + "," + words[2] + "," + words[3] + ","
                            + words[4] + "," + words[5] + "," + words[6] + "," + words[7] + "," + words[8] + ","
                            + words[9] + "," + words[10] + "," + words[11] + "," + words[12] + "," + words[13]);
                } else if (!Objects.equals(words[0], "Player Name") && Objects.equals(words[2], "Goalie")) {
                    mapG.put(countG, new GoalieUnite(words[0], words[1], words[2], words[3], words[4],
                            Integer.valueOf(words[5]), Integer.valueOf(words[6]), words[7], Integer.valueOf(words[8]),
                            Integer.valueOf(words[9]), Integer.valueOf(words[10]), words[11], Integer.valueOf(words[12]),
                            Integer.valueOf(words[13])));
                    countG++;
                }
            }


            int count2 = 0;
            for (Map.Entry<Integer, PlayerstatUnite> mapf : map.entrySet()) {
                if (mapf.getKey() == count2) {
                    newMap.put(count2, new PlayerstatUnite(mapf.getValue().getWord0(), mapf.getValue().getWord1(), mapf.getValue().getWord2(),
                            mapf.getValue().getWord3(),
                            mapf.getValue().getWord4() + map.get(count2 + 36).getWord4(),
                            mapf.getValue().getWord5() + map.get(count2 + 36).getWord5(),
                            mapf.getValue().getWord6() + map.get(count2 + 36).getWord6(),
                            mapf.getValue().getWord7() + map.get(count2 + 36).getWord7(),
                            mapf.getValue().getWord8() + map.get(count2 + 36).getWord8(),
                            mapf.getValue().getWord9() + map.get(count2 + 36).getWord9(),
                            mapf.getValue().getWord10() + map.get(count2 + 36).getWord10(),
                            mapf.getValue().getWord11() + map.get(count2 + 36).getWord11(),
                            mapf.getValue().getWord12(), mapf.getValue().getWord13()));

                    if (count2 != 35) {
                        count2++;
                    }
                } else {
                    break;
                }
            }

            String[] strings = new String[4];
            int cou = 0;
            for (int i = 0; i < 4; i++) {

                int a = Integer.parseInt(mapG.get(i).getWord3().substring(0,2));
                int b = Integer.parseInt(mapG.get(i+4).getWord3().substring(0,2));

                String f = String.valueOf(a + b);

                if (f.equals("0")){
                    f = "00";
                }

                strings[cou] = f + ":" + "00";
                cou++;
            }


            int count3 = 0;
            for (Map.Entry<Integer, GoalieUnite> mapf : mapG.entrySet()) {
                if (mapf.getKey() == count3) {
                    newMapG.put(count3, new GoalieUnite(mapf.getValue().getWord0(), mapf.getValue().getWord1(), mapf.getValue().getWord2(),
                            strings[count3],
                            mapf.getValue().getWord4(),
                            mapf.getValue().getWord5() + mapG.get(count3 + 4).getWord5(),
                            mapf.getValue().getWord6() + mapG.get(count3 + 4).getWord6(),
                            mapf.getValue().getWord7(),
                            mapf.getValue().getWord8() + mapG.get(count3 + 4).getWord8(),
                            mapf.getValue().getWord9() + mapG.get(count3 + 4).getWord9(),
                            mapf.getValue().getWord10() + mapG.get(count3 + 4).getWord10(),
                            mapf.getValue().getWord11(),
                            mapf.getValue().getWord12(), mapf.getValue().getWord13()));

                    if (count3 != 3) {
                        count3++;
                    }
                } else {
                    break;
                }
            }


            try (PrintWriter writer = new PrintWriter("E:/KHL ECHL/Unite/unitplayerstat.csv")) {
                writer.write(str.get(0) + "\n");

                int count4 = 0;
                for (Map.Entry<Integer, PlayerstatUnite> mapN : newMap.entrySet()) {
                    if (count4 < 18) {
                        writer.write(mapN.getValue().getWord0() + ",");
                        writer.write(mapN.getValue().getWord1() + ",");
                        writer.write(mapN.getValue().getWord2() + ",");
                        writer.write(mapN.getValue().getWord3() + ",");
                        writer.write(mapN.getValue().getWord4() + ",");
                        writer.write(mapN.getValue().getWord5() + ",");
                        writer.write(mapN.getValue().getWord6() + ",");
                        writer.write(mapN.getValue().getWord7() + ",");
                        writer.write(mapN.getValue().getWord8() + ",");
                        writer.write(mapN.getValue().getWord9() + ",");
                        writer.write(mapN.getValue().getWord10() + ",");
                        writer.write(mapN.getValue().getWord11() + ",");
                        writer.write(mapN.getValue().getWord12() + ",");
                        writer.write(mapN.getValue().getWord13() + "\n");
                        count4++;

                        if (count4 == 18) {
                            writer.write(newMapG.get(0).getWord0() + ",");
                            writer.write(newMapG.get(0).getWord1() + ",");
                            writer.write(newMapG.get(0).getWord2() + ",");
                            writer.write(newMapG.get(0).getWord3() + ",");
                            writer.write(newMapG.get(0).getWord4() + ",");
                            writer.write(newMapG.get(0).getWord5() + ",");
                            writer.write(newMapG.get(0).getWord6() + ",");
                            writer.write(newMapG.get(0).getWord7() + ",");
                            writer.write(newMapG.get(0).getWord8() + ",");
                            writer.write(newMapG.get(0).getWord9() + ",");
                            writer.write(newMapG.get(0).getWord10() + ",");
                            writer.write(newMapG.get(0).getWord11() + ",");
                            writer.write(newMapG.get(0).getWord12() + ",");
                            writer.write(newMapG.get(0).getWord13() + "\n");

                            writer.write(newMapG.get(1).getWord0() + ",");
                            writer.write(newMapG.get(1).getWord1() + ",");
                            writer.write(newMapG.get(1).getWord2() + ",");
                            writer.write(newMapG.get(1).getWord3() + ",");
                            writer.write(newMapG.get(1).getWord4() + ",");
                            writer.write(newMapG.get(1).getWord5() + ",");
                            writer.write(newMapG.get(1).getWord6() + ",");
                            writer.write(newMapG.get(1).getWord7() + ",");
                            writer.write(newMapG.get(1).getWord8() + ",");
                            writer.write(newMapG.get(1).getWord9() + ",");
                            writer.write(newMapG.get(1).getWord10() + ",");
                            writer.write(newMapG.get(1).getWord11() + ",");
                            writer.write(newMapG.get(1).getWord12() + ",");
                            writer.write(newMapG.get(1).getWord13() + "\n");
                            count4++;
                        }
                    } else if (count4 > 18) {
                        writer.write(mapN.getValue().getWord0() + ",");
                        writer.write(mapN.getValue().getWord1() + ",");
                        writer.write(mapN.getValue().getWord2() + ",");
                        writer.write(mapN.getValue().getWord3() + ",");
                        writer.write(mapN.getValue().getWord4() + ",");
                        writer.write(mapN.getValue().getWord5() + ",");
                        writer.write(mapN.getValue().getWord6() + ",");
                        writer.write(mapN.getValue().getWord7() + ",");
                        writer.write(mapN.getValue().getWord8() + ",");
                        writer.write(mapN.getValue().getWord9() + ",");
                        writer.write(mapN.getValue().getWord10() + ",");
                        writer.write(mapN.getValue().getWord11() + ",");
                        writer.write(mapN.getValue().getWord12() + ",");
                        writer.write(mapN.getValue().getWord13() + "\n");
                        count4++;

                        if (count4 == 37) {
                            writer.write(newMapG.get(2).getWord0() + ",");
                            writer.write(newMapG.get(2).getWord1() + ",");
                            writer.write(newMapG.get(2).getWord2() + ",");
                            writer.write(newMapG.get(2).getWord3() + ",");
                            writer.write(newMapG.get(2).getWord4() + ",");
                            writer.write(newMapG.get(2).getWord5() + ",");
                            writer.write(newMapG.get(2).getWord6() + ",");
                            writer.write(newMapG.get(2).getWord7() + ",");
                            writer.write(newMapG.get(2).getWord8() + ",");
                            writer.write(newMapG.get(2).getWord9() + ",");
                            writer.write(newMapG.get(2).getWord10() + ",");
                            writer.write(newMapG.get(2).getWord11() + ",");
                            writer.write(newMapG.get(2).getWord12() + ",");
                            writer.write(newMapG.get(2).getWord13() + "\n");

                            writer.write(newMapG.get(3).getWord0() + ",");
                            writer.write(newMapG.get(3).getWord1() + ",");
                            writer.write(newMapG.get(3).getWord2() + ",");
                            writer.write(newMapG.get(3).getWord3() + ",");
                            writer.write(newMapG.get(3).getWord4() + ",");
                            writer.write(newMapG.get(3).getWord5() + ",");
                            writer.write(newMapG.get(3).getWord6() + ",");
                            writer.write(newMapG.get(3).getWord7() + ",");
                            writer.write(newMapG.get(3).getWord8() + ",");
                            writer.write(newMapG.get(3).getWord9() + ",");
                            writer.write(newMapG.get(3).getWord10() + ",");
                            writer.write(newMapG.get(3).getWord11() + ",");
                            writer.write(newMapG.get(3).getWord12() + ",");
                            writer.write(newMapG.get(3).getWord13() + "\n");
                            count4++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}