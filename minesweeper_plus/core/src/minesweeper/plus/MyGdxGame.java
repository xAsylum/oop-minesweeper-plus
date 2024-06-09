package minesweeper.plus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.OutOfBoundsException;
import minesweeper.plus.services.SimpleBoard;
import minesweeper.plus.viewmodels.SimpleViewModel;
import minesweeper.plus.views.Menu;
import minesweeper.plus.views.SimpleView;
import minesweeper.plus.views.View;

import static java.lang.System.exit;

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