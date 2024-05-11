package minesweeper.plus.services;

import minesweeper.plus.core.*;

import java.util.Map;
import java.util.Set;

public class SimpleBoard implements Board {
    private final Coordinates size;
    private final Spot[][][] fields;
    private final int noMines;
    private int noClicks = 0;
    public Minefield minefield = null;
    public SimpleBoard(Coordinates size, int noMines) {
        this.size = size;
        this.noMines = noMines;
        fields = new Spot[size.xValue][size.yValue][size.zValue];
        for(int i=0; i<size.xValue; i++) {
            for(int j=0; j<size.yValue; j++) {
                for(int k=0; k<size.zValue; k++)
                    fields[i][j][k] = new SimpleSpot(new Coordinates(i,j,k), this);
            }
        }
    }
    public Integer clickThis(Coordinates guess) throws MineException, OutOfBoundsException {
        if(minefield == null) {
            minefield = new SimpleMinefield(size, noMines, guess);
        }
        noClicks++;
        return minefield.clickThis(guess);
    }
    public Set<Map.Entry<Coordinates, Integer>> instantiateClick(Coordinates guess) throws OutOfBoundsException, MineException {
        return minefield.instantiateClick(guess);
    }

    @Override
    public Coordinates getSize() {
        return size;
    }

    @Override
    public Spot getSpot(Coordinates position) throws OutOfBoundsException {
        if(!position.bounded(size))
            throw new OutOfBoundsException();
        return fields[position.xValue][position.yValue][position.zValue];
    }

    public boolean isFinished() {
        return noClicks + noMines >= size.xValue * size.yValue * size.zValue;
    }



}
