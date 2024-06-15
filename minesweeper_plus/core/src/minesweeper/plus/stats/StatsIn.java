package minesweeper.plus.stats;

import minesweeper.plus.core.Coordinates;

//input class in statistics
public class StatsIn {
    final Coordinates coords;
    final int noMines;

    public StatsIn(Coordinates coords, int noMines) {
        this.coords = coords;
        this.noMines = noMines;
    }
    public Coordinates getCoords() {
        return coords;
    }
    public int getNoMines() {
        return noMines;
    }

    @Override
    public String toString() {
        return coords.toString() + " " + String.valueOf(noMines);
    }
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof StatsIn)) return false;
        StatsIn tmp = (StatsIn) obj;
        return (tmp.coords.equals(coords) && tmp.noMines == noMines);
    }
}
