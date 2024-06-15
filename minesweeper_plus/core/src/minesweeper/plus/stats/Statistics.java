package minesweeper.plus.stats;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//class holding statistics for our game
public class Statistics {
    Map<String, StatsOut> stats;
    public Statistics() {
        try {
            this.stats = WorkWithFiles.loadStats();
        }
        catch (Exception ignored) {
            this.stats = new HashMap<>();
        }
    }
    void init(StatsIn s) {
        stats.computeIfAbsent(s.toString(), k -> new StatsOut());
    }
    public void delete() {
        try {
            stats = new HashMap<>();
            WorkWithFiles.saveStats(stats);
        } catch (IOException ignored) {
        }
    }
    public void save() {
        try {
            WorkWithFiles.saveStats(stats);
        } catch (IOException ignored) {
        }
    }
    public void won(StatsIn s) {
        init(s);
        stats.get(s.toString()).won();
    }
    public void lost(StatsIn s) {
        init(s);
        stats.get(s.toString()).lost();
    }
    public int getWins(StatsIn s) {
        if(stats.get(s.toString()) == null)
            return 0;
        return stats.get(s.toString()).getWins();
    }
    public int getLooses(StatsIn s) {
        if(stats.get(s.toString()) == null)
            return 0;
        return stats.get(s.toString()).getLooses();
    }
    public int getTotal(StatsIn s) {
        if(stats.get(s.toString()) == null)
            return 0;
        return stats.get(s.toString()).getTotal();
    }
}
