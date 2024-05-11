package minesweeper.plus.services;

import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.MineException;
import minesweeper.plus.core.OutOfBoundsException;

import java.util.Map;
import java.util.Set;

public interface Board {

    //clicking on some spot, should only be accessed via Spot.leftClick()
    Integer clickThis(Coordinates guess) throws MineException, OutOfBoundsException;
    Set<Map.Entry<Coordinates, Integer>> instantiateClick(Coordinates guess) throws OutOfBoundsException, MineException;

    //returns size of the board
    Coordinates getSize();

    //returns spot that's on the given position
    Spot getSpot(Coordinates position) throws OutOfBoundsException;

    //returns true if all mine-free spots have already been clicked
    boolean isFinished();


}
