package minesweeper.plus.stats;


//output class in statistics
public class StatsOut {
    int wins;
    int looses;
    public StatsOut() {
        this.wins = 0;
        this.looses = 0;
    }
    public StatsOut(int w, int l) {
        this.wins = w;
        this.looses = l;
    }
    public void won() {
        ++wins;
    }
    public void lost() {
        ++looses;
    }
    public int getWins() {
        return this.wins;
    }
    public int getLooses() {
        return this.looses;
    }
    public int getTotal() {
        return getWins() + getLooses();
    }
}