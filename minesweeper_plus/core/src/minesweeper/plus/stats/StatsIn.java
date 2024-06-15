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

    @Override
    public String toString() {
        return coords.toString() + " " + noMines;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof StatsIn)) return false;
        StatsIn tmp = (StatsIn) obj;
        return (tmp.coords.equals(coords) && tmp.noMines == noMines);
    }
}
