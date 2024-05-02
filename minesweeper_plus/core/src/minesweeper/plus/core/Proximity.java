package minesweeper.plus.core;

import java.util.ArrayList;

//there could be different types of proximity,
public interface Proximity {
    int count(int x, int y, int z);
    ArrayList<ArrayList<Integer>> neighbourhood(int x, int y, int z) throws OutOfBoundsException;
    //list of sublists of length 3, each sublist is one field on the minefield
}