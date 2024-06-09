package minesweeper.plus.services;

import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.OutOfBoundsException;
import minesweeper.plus.core.SpotValues;

import java.util.Map;
import java.util.Set;

public interface Board {

    //includes quick-clearing large areas
    Map<Coordinates, SpotValues> clickThis(Spot guess) throws OutOfBoundsException;

    //returns size of the board
    Coordinates getSize();

    int getNoMines();

    //returns set of neighbouring spots, all within bounds
    Set<Spot> getNeighbourhood(Coordinates position);

    //returns spot that's on the given position
    Spot getSpot(Coordinates position);

    //returns true if the game is finished, false otherwise
    boolean won();

    //returns true if any bomb has already been clicked
    boolean dead();
}
