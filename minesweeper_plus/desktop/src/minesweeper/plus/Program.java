package minesweeper.plus;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import minesweeper.plus.MyGdxGame;

import minesweeper.plus.core.Coordinates;
import minesweeper.plus.services.Board;
import minesweeper.plus.services.SimpleBoard;

public class Program {
    public static void main(String[] args) {        //HERE BE DRAGONS
        //plan on what to do here:
//        Coordinates size = new Coordinates(10, 10, 1);
//        int noMines = 5;
//        Board board = new SimpleBoard(size, noMines);
        //names can (should) be changed, but constructors should (please) look like this
//        Viewmodel viewmodel = new Viewmodel(board);
//        View view = new View(viewmodel);




        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(15);
        config.setTitle("Minesweeper_Plus");
        new Lwjgl3Application(new MyGdxGame(), config);
    }
}