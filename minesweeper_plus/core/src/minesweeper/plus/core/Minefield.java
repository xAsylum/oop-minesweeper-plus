package minesweeper.plus.core;

import java.util.ArrayList;

//creates and stores the minefield and answers questions about it
public interface Minefield {

    public Minefield newField(int width, int height, int depth, int noMines);

    public int clickThis(int x, int y, int z) throws MineException, OutOfBoundsException;
                //returns #mines in proximity of this field
                //OR MineException if mine OR OutOfBounds if out of bounds
    public ArrayList<ArrayList<Integer>> getNeighbourhood (int x, int y, int z) throws NotEmptyException, OutOfBoundsException;
                //quick-clear empty areas

}