package minesweeper.plus.services;

import minesweeper.plus.core.MineException;

public interface Spot {

    //returns null if the spot is safe, otherwise
    //returns number of mines around or MineException if this spot has a mine
    Integer leftClick() throws MineException;

    //returns true, if the state of the Spot.isSafe() has changed successfully
    //otherwise returns false (ex. when the spot has already been clicked)
    boolean rightClick();

    //returns true if the spot has already been clicked
    boolean isClicked();

    //returns true if the spot is safe - has a flag on it (is not leftClickable)
    boolean isSafe();
}
