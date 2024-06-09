package minesweeper.plus.services;

import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.OutOfBoundsException;
import minesweeper.plus.core.SpotValues;

import java.util.HashMap;
import java.util.Map;

public class SimpleSpot implements Spot {
    private final Coordinates position;
    private final Board board;
    private SpotValues value = SpotValues.HIDDEN;

    SimpleSpot(Coordinates position, Board board) {
        this.position = position;
        this.board = board;
    }

    @Override
    public Map<Coordinates, SpotValues> leftClick() {
        Map<Coordinates, SpotValues> result = new HashMap<>();
        if(isClicked() || isSafe())
            return result;
        try {
            result = board.clickThis(this);
        } catch (OutOfBoundsException ignored) {}
        return result;
    }

    @Override
    public boolean rightClick() {
        if(value == SpotValues.FLAG) {
            value = SpotValues.HIDDEN;
            return true;
        }
        else if(value == SpotValues.HIDDEN) {
            value = SpotValues.FLAG;
            return true;
        }
        return false;
    }

    @Override
    public SpotValues getValue() {
        return value;
    }

    @Override
    public void setValue(SpotValues s) {
        value = s;
    }

    @Override
    public Coordinates getPosition() {
        return position;
    }

    @Override
    public boolean isClicked() {
        return value != SpotValues.FLAG && value != SpotValues.HIDDEN;
    }
    @Override
    public boolean isSafe() {
        return value == SpotValues.FLAG;
    }

}
