package minesweeper.plus.core;

public class NotEmptyException extends Exception {
    Coordinates field;
    public NotEmptyException() {};
    public NotEmptyException (Coordinates guess) {
        this.field = guess;
    }
}