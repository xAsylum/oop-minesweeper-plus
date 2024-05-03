package minesweeper.plus.core;

import java.util.ArrayList;

//there could be different types of proximity,
public interface Proximity {
    int count(Coordinates guess);
    ArrayList<Coordinates> neighbourhood(Coordinates guess) throws OutOfBoundsException;
    //list of fields nearby in the given proximity
}