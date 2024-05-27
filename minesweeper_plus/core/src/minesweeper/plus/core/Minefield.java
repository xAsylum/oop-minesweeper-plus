package minesweeper.plus.core;

import minesweeper.plus.services.SpotValues;

import java.util.Set;

//creates and stores the minefield and answers questions about it
public interface Minefield {

//    Minefield (Coordinates size, int noMines, Coordinates firstGuess) throws OutOfBoundsException;
    // constructor will look like this
    Coordinates getSize();

    //returns no. mines in proximity of this field
    //OR MineException if mine OR OutOfBounds if out of bounds
    SpotValues clickThis(Coordinates guess) throws OutOfBoundsException;

    //returns coordinates of neighbouring fields, all of them are within bounds
    Set<Coordinates> getNeighbourhood (Coordinates guess) throws OutOfBoundsException;

}