package minesweeper.plus.core;

import java.util.Set;

//there could be different types of proximity,
public interface Proximity {
    //list of fields nearby in the given proximity, might be out ouf bounds
    Set<Coordinates> neighbourhood(Coordinates guess);
}