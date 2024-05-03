package minesweeper.plus.core;

public class MineException extends Exception {
    Coordinates field;
    public MineException() {};
    public MineException (Coordinates guess) {
        this.field = guess;
    }
}
