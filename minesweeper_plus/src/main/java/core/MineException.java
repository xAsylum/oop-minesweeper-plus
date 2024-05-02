package core;

public class MineException extends Exception {
    int x;
    int y;
    int z;
    public MineException (int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
