package minesweeper.plus.services;

import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.MineException;
import minesweeper.plus.core.OutOfBoundsException;

import java.util.HashMap;
import java.util.Map;

import static minesweeper.plus.services.NumberToSpotValue.getSpotValue;

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
        try {
            int a = board.clickThis(position);
            result.put(position, getSpotValue(a));
        } catch (MineException e) {
            result.put(position, SpotValues.MINE);
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
    public boolean isClicked() {
        return value != SpotValues.FLAG && value != SpotValues.HIDDEN;
    }
    @Override
    public boolean isSafe() {
        return value == SpotValues.FLAG;
    }

}
