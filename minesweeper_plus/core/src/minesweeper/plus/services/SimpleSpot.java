package minesweeper.plus.services;

import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.MineException;
import minesweeper.plus.core.OutOfBoundsException;

public class SimpleSpot implements Spot {
    private final Coordinates position;
    private final Board board;
    private boolean clicked = false;
    private int value;
    private boolean safe = false;

    SimpleSpot(Coordinates position, Board board) {
        this.position = position;
        this.board = board;
    }
    public Integer leftClick() throws MineException {
        if(isSafe())
            return null;
        if(isClicked())
            return value;
        try {
            value = board.clickThis(position);
        } catch (OutOfBoundsException e) {
            throw new RuntimeException("Catastrophic failure in SimpleSpot.leftClick, OOB");
        }
        clicked = true;
        return value;
    }
    public boolean rightClick() {
        if(isClicked())
            return false;
        safe = !safe;
        return true;
    }
    public boolean isClicked() {
        return clicked;
    }
    public boolean isSafe() {
        return safe;
    }

}
