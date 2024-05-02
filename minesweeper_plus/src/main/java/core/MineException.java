package core;

public class MineException extends Exception {
    String err;
    MineException(){}
    MineException(String s){
        err=s;
    }
}