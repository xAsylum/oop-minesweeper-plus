package minesweeper.plus.core;

import java.util.Map;
import java.util.Set;

//creates and stores the minefield and answers questions about it
public interface Minefield {

//    Minefield (Coordinates size, int noMines, Coordinates firstGuess) throws OutOfBoundsException;
    // constructor will look like this
    Coordinates getSize();

    //intantiateClick - method to get connected component of the board, bounded by nonzero fields
    Set<Map.Entry<Coordinates, Integer>> instantiateClick(Coordinates guess) throws OutOfBoundsException, MineException;

    //returns no. mines in proximity of this field
    //OR MineException if mine OR OutOfBounds if out of bounds
    int clickThis(Coordinates guess) throws MineException, OutOfBoundsException;

    //quick-clear empty areas, returns an entrySet
    Set<Map.Entry<Coordinates, Integer>> getNeighbourhood (Coordinates guess) throws NotEmptyException, OutOfBoundsException;

}