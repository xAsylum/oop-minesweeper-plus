package minesweeper.plus;

import com.badlogic.gdx.Game;
import minesweeper.plus.views.Menu;

public class MyGdxGame extends Game {
    @Override
    public void create () {
        this.setScreen(new Menu());
    }

    @Override
    public void render () {
        super.render();
    }
}