package minesweeper.plus.core;

public class OutOfBoundsException extends Exception {
    Coordinates field;
    public OutOfBoundsException() {};
    public OutOfBoundsException (Coordinates guess) {
        this.field = guess;
    }
}