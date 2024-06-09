package minesweeper.plus.stats;

import minesweeper.plus.core.Coordinates;

//input class in statistics
class StatsIn {
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
}
