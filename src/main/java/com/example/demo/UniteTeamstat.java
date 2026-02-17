package com.example.demo;

import java.io.*;
import java.util.*;

public class UniteTeamstat {

    public static void main(String[] args) {

        final String TEAMSTAT = "D:/KHL ECHL/Unite/teamstat";
        final String UNITETEAMSTAT_TXT = "uniteteamstat.txt";
        List<String> str = new ArrayList<>();

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(UNITETEAMSTAT_TXT))) {
            File dir = new File(TEAMSTAT);
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
            Map<Integer, TeamstatUnite> map = new TreeMap<>();
            Map<Integer, TeamstatUnite> newMap = new TreeMap<>();

            BufferedReader reader = new BufferedReader(new FileReader(UNITETEAMSTAT_TXT));

            String c;
            int count = 0;

            while ((c = reader.readLine()) != null) {
                String[] words = c.split(",");

                if (Objects.equals(words[0], "Home") && count == 0) {
                    map.put(0, new TeamstatUnite(words[0], words[1], Integer.valueOf(words[2]), Integer.valueOf(words[3]), Integer.valueOf(words[4]),
                            Integer.valueOf(words[5]), Integer.valueOf(words[6]), Integer.valueOf(words[7]), Integer.valueOf(words[8]),
                            Integer.valueOf(words[9]), Integer.valueOf(words[10]), Integer.valueOf(words[11]), Integer.valueOf(words[12]),
                            Integer.valueOf(words[13]), Integer.valueOf(words[14]), Integer.valueOf(words[15]), words[16]));
                    count++;
                } else if (Objects.equals(words[0], "Away") && count == 1) {
                    map.put(1, new TeamstatUnite(words[0], words[1], Integer.valueOf(words[2]), Integer.valueOf(words[3]), Integer.valueOf(words[4]),
                            Integer.valueOf(words[5]), Integer.valueOf(words[6]), Integer.valueOf(words[7]), Integer.valueOf(words[8]),
                            Integer.valueOf(words[9]), Integer.valueOf(words[10]), Integer.valueOf(words[11]), Integer.valueOf(words[12]),
                            Integer.valueOf(words[13]), Integer.valueOf(words[14]), Integer.valueOf(words[15]), words[16]));
                    count++;
                } else if (Objects.equals(words[0], "Home") && count == 2) {
                    map.put(2, new TeamstatUnite(words[0], words[1], Integer.valueOf(words[2]), Integer.valueOf(words[3]), Integer.valueOf(words[4]),
                            Integer.valueOf(words[5]), Integer.valueOf(words[6]), Integer.valueOf(words[7]), Integer.valueOf(words[8]),
                            Integer.valueOf(words[9]), Integer.valueOf(words[10]), Integer.valueOf(words[11]), Integer.valueOf(words[12]),
                            Integer.valueOf(words[13]), Integer.valueOf(words[14]), Integer.valueOf(words[15]), words[16]));
                    count++;
                } else if (Objects.equals(words[0], "Away") && count == 3) {
                    map.put(3, new TeamstatUnite(words[0], words[1], Integer.valueOf(words[2]), Integer.valueOf(words[3]), Integer.valueOf(words[4]),
                            Integer.valueOf(words[5]), Integer.valueOf(words[6]), Integer.valueOf(words[7]), Integer.valueOf(words[8]),
                            Integer.valueOf(words[9]), Integer.valueOf(words[10]), Integer.valueOf(words[11]), Integer.valueOf(words[12]),
                            Integer.valueOf(words[13]), Integer.valueOf(words[14]), Integer.valueOf(words[15]), words[16]));
                    count++;
                } else if (Objects.equals(words[1], "Team")) {
                    str.add(0, words[0] + ","  +  words[1] + "," +  words[2] + "," +  words[3] + ","
                            +  words[4] + "," +  words[5] + "," +  words[6] + "," +  words[7] + "," +  words[8] + ","
                            +  words[9] + "," +  words[10] + "," +  words[11] + "," +  words[12] + "," +  words[13] + ","
                            +  words[14] + "," +  words[15] + "," +  words[16]);
                }
            }

            Integer f = Integer.valueOf(String.valueOf(map.get(0).getWord16().charAt(0))) + Integer.valueOf(String.valueOf(map.get(2).getWord16().charAt(0)));
            Integer t = Integer.valueOf(String.valueOf(map.get(0).getWord16().charAt(2))) + Integer.valueOf(String.valueOf(map.get(2).getWord16().charAt(2)));

            Integer f1 = Integer.valueOf(String.valueOf(map.get(1).getWord16().charAt(0))) + Integer.valueOf(String.valueOf(map.get(3).getWord16().charAt(0)));
            Integer t1 = Integer.valueOf(String.valueOf(map.get(1).getWord16().charAt(2))) + Integer.valueOf(String.valueOf(map.get(3).getWord16().charAt(2)));

            String s = f + "/" + t;
            String s1 = f1 + "/" + t1;

            map.forEach((integer, teamstatUnit) -> {
                if (integer == 0) {
                    newMap.put(0, new TeamstatUnite(teamstatUnit.getWord0(), teamstatUnit.getWord1(),
                            map.get(integer).getWord2() + map.get(2).getWord2(),
                            map.get(integer).getWord3() + map.get(2).getWord3(),
                            map.get(integer).getWord4() + map.get(2).getWord4(),
                            map.get(integer).getWord5() + map.get(2).getWord5(),
                            map.get(integer).getWord6() + map.get(2).getWord6(),
                            map.get(integer).getWord7() + map.get(2).getWord7(),
                            map.get(integer).getWord8() + map.get(2).getWord8(),
                            map.get(integer).getWord9() + map.get(2).getWord9(),
                            teamstatUnit.getWord10(), teamstatUnit.getWord11(), teamstatUnit.getWord12(), teamstatUnit.getWord13(),
                            teamstatUnit.getWord14(), teamstatUnit.getWord15(), s));

                }
            });

            map.forEach((integer, teamstatUnit) -> {
                if (integer == 1) {
                    newMap.put(1, new TeamstatUnite(teamstatUnit.getWord0(), teamstatUnit.getWord1(), map.get(integer).getWord2() + map.get(3).getWord2(),
                            map.get(integer).getWord3() + map.get(3).getWord3(),
                            map.get(integer).getWord4() + map.get(3).getWord4(),
                            map.get(integer).getWord5() + map.get(3).getWord5(),
                            map.get(integer).getWord6() + map.get(3).getWord6(),
                            map.get(integer).getWord7() + map.get(3).getWord7(),
                            map.get(integer).getWord8() + map.get(3).getWord8(),
                            map.get(integer).getWord9() + map.get(3).getWord9(),
                            teamstatUnit.getWord10(), teamstatUnit.getWord11(), teamstatUnit.getWord12(), teamstatUnit.getWord13(),
                            teamstatUnit.getWord14(), teamstatUnit.getWord15(), s1));

                }
            });

            try (PrintWriter writer = new PrintWriter("D:/KHL ECHL/Unite/unit.csv")) {
                writer.write(str.get(0) + "\n");
                for (Map.Entry<Integer, TeamstatUnite> mapN : newMap.entrySet()) {
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
                    writer.write(mapN.getValue().getWord13() + ",");
                    writer.write(mapN.getValue().getWord14() + ",");
                    writer.write(mapN.getValue().getWord15() + ",");
                    writer.write(mapN.getValue().getWord16() + "\n");

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
