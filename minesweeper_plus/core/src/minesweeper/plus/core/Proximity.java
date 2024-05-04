package minesweeper.plus.core;

import java.util.Set;

//there could be different types of proximity,
public interface Proximity {
    Set<Coordinates> neighbourhood(Coordinates guess);
    //list of fields nearby in the given proximity
}