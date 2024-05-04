package minesweeper.plus.core;

import java.util.Map;
import java.util.Set;

//creates and stores the minefield and answers questions about it
public interface Minefield {

    Minefield newField(int width, int height, int depth, int noMines, Coordinates firstGuess) throws OutOfBoundsException;
    Coordinates getSize();

    int clickThis(Coordinates guess) throws MineException, OutOfBoundsException;
                //returns #mines in proximity of this field
                //OR MineException if mine OR OutOfBounds if out of bounds
    Set<Map.Entry<Coordinates, Integer>> getNeighbourhood (Coordinates guess) throws NotEmptyException, OutOfBoundsException;
                //quick-clear empty areas, returns an entrySet

}