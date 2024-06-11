package minesweeper.plus.services;

import minesweeper.plus.core.*;

import java.util.*;

public class SimpleBoard implements Board {
    private final Coordinates size;
    private final Spot[][][] fields;
    private final int noMines;
    private int noClicks = 0;
    private boolean alive = true;
    public Minefield minefield = null;

    public SimpleBoard(Coordinates size, int noMines) throws OutOfBoundsException {
        if(size == null || !new Coordinates(0, 0, 0).bounded(size) || noMines < 0)
            throw new OutOfBoundsException();
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

    @Override
    public Map<Coordinates, SpotValues> clickThis(Spot guess) throws OutOfBoundsException {
        if(!guess.getPosition().bounded(getSize()))
            throw new OutOfBoundsException();
        if(minefield == null)
            minefield = new SimpleMinefield(getSize(), getNoMines(), guess.getPosition());

        Map<Coordinates, SpotValues> result = new HashMap<>();
        Queue<Spot> queue = new LinkedList<>();
        queue.add(guess);
        while(!queue.isEmpty()) {
            Spot tempSpot = queue.remove();
            if(!tempSpot.isClicked() && !tempSpot.isSafe()) {
                noClicks++;
                SpotValues value = minefield.clickThis(tempSpot.getPosition());
                if(value == SpotValues.MINE)
                    alive = false;
                tempSpot.setValue(value);
                result.put(tempSpot.getPosition(), value);
                if(value == SpotValues.N00) {
                    queue.addAll(getNeighbourhood(tempSpot.getPosition()));
                }
            }
        }
        return result;
    }

    @Override
    public Coordinates getSize() {
        return size;
    }

    @Override
    public int getNoMines() {
        return noMines;
    }

    @Override
    public Set<Spot> getNeighbourhood(Coordinates position) {
        Set<Spot> result = new HashSet<>();
        Set<Coordinates> temp;
        try {
            temp = minefield.getNeighbourhood(position);
        } catch (OutOfBoundsException e) { return result; }
        for(Coordinates c : temp) {
            result.add(getSpot(c));
        }
        return result;
    }

    @Override
    public Spot getSpot(Coordinates position) {
        if(!position.bounded(size))
            return null;
        return fields[position.xValue][position.yValue][position.zValue];
    }

    @Override
    public boolean won() {
        return alive() && noMines + noClicks == size.xValue*size.yValue*size.zValue;
    }

    @Override
    public boolean alive() {
        return alive;
    }

}
