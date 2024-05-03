package minesweeper.plus.core;

import java.util.ArrayList;

//creates and stores the minefield and answers questions about it
public interface Minefield {

    public Minefield newField(int width, int height, int depth, int noMines, Coordinates firstGuess);

    public int clickThis(Coordinates guess) throws MineException, OutOfBoundsException;
                //returns #mines in proximity of this field
                //OR MineException if mine OR OutOfBounds if out of bounds
    public ArrayList<ArrayList<Integer>> getNeighbourhood (Coordinates guess) throws NotEmptyException, OutOfBoundsException;
                //quick-clear empty areas

}