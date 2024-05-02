package minesweeper.plus.core;

public class OutOfBoundsException extends Exception {
    int x;
    int y;
    int z;
    public OutOfBoundsException() {};
    public OutOfBoundsException (int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}