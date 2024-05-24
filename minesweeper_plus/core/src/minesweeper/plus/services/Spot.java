package minesweeper.plus.services;

import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.MineException;

import java.util.Map;

public interface Spot {

    //returns only the fields that were uncovered at this move
    Map<Coordinates, SpotValues> leftClick();

    //returns true, if the state of the Spot.isSafe() has changed successfully
    //otherwise returns false (ex. when the spot has already been clicked)
    boolean rightClick();

    //just returns the enum value of this spot
    SpotValues getValue();

    //returns true if the spot has already been clicked
    boolean isClicked();

    //returns true if the spot is safe - has a flag on it (is not leftClickable)
    boolean isSafe();
}
