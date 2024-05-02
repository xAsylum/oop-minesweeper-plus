package core;

public class MineException extends Exception {
    int x; int y;
    MineException(int x, int y){this.x=x; this.y=y;}
    MineException(){}
}
