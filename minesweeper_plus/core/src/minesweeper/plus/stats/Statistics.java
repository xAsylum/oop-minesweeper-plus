package minesweeper.plus.stats;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//class holding statistics for our game
class Statistics {
    Map<StatsIn, StatsOut> stats;
    public Statistics() {
        this.stats = new HashMap<>();
    }
    void init(StatsIn s) {
        stats.computeIfAbsent(s, k -> new StatsOut());
    }
    public void won(StatsIn s) {
        init(s);
        stats.get(s).won();
    }
    public void lost(StatsIn s) {
        init(s);
        stats.get(s).lost();
    }
    public int getWins(StatsIn s) {
        if(stats.get(s) == null)
            return 0;
        return stats.get(s).getWins();
    }
    public int getLooses(StatsIn s) {
        if(stats.get(s) == null)
            return 0;
        return stats.get(s).getLooses();
    }
    public int getTotal(StatsIn s) {
        if(stats.get(s) == null)
            return 0;
        return stats.get(s).getTotal();
    }
}