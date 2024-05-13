package minesweeper.plus;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Program {
    public static void main(String[] args) {        //HERE BE DRAGONS

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(15);
        config.setTitle("Minesweeper_Plus");
        new Lwjgl3Application(new MyGdxGame(), config);
    }
}