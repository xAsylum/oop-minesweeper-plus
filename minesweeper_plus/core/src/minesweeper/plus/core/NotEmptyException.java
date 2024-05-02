package minesweeper.plus.core;

public class NotEmptyException extends Exception {
    int x;
    int y;
    int z;
    public NotEmptyException() {};
    public NotEmptyException (int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}