package minesweeper.plus.stats;

import java.util.HashMap;
import java.util.Map;

//class holding statistics for our game
public class Statistics {
    Map<String, StatsOut> stats;
    public Statistics() {
        this.stats = new HashMap<>();
    }
    void init(StatsIn s) {
        if((stats.get(s.toString()) == null)) {
            stats.put(s.toString(), new StatsOut());
            //System.out.println("adding");
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
