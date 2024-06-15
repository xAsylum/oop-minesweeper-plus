package minesweeper.plus.stats;

import minesweeper.plus.core.Coordinates;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class WorkWithFiles {

    public static void saveStats(Map<String, StatsOut> data) throws IOException {

        File f = new File("data/stats.txt");
        f.getParentFile().mkdirs();
        f.createNewFile();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for (Map.Entry<String, StatsOut> x : data.entrySet()) {
                String statsInText = x.getKey().replace(',', ' ');
                writer.write(
                        statsInText + " "
                                + x.getValue().getWins() + " "
                                + x.getValue().getLooses());
                writer.newLine();
            }
            writer.close();
        } catch (Exception ignored) {

        }
    }
    public static Map<String, StatsOut> loadStats() throws IOException {
        Map<String, StatsOut> result = new HashMap<>();
        Path path = Paths.get("data/stats.txt");
        BufferedReader reader = Files.newBufferedReader(path);
        Stream<String> data = reader.lines();
        data.forEach((line) -> {
            String[] statsLine = line.split(" ");
            int x = Integer.parseInt(statsLine[0]);
            int y = Integer.parseInt(statsLine[1]);
            int z = Integer.parseInt(statsLine[2]);
            int bombsCount = Integer.parseInt(statsLine[3]);
            int wins = Integer.parseInt(statsLine[4]);
            int loses = Integer.parseInt(statsLine[5]);
            result.put(new StatsIn( new Coordinates(x,y,z), bombsCount).toString(), new StatsOut(wins, loses));
        });
        reader.close();
        return result;
    }

}
