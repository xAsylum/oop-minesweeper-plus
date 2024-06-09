package minesweeper.plus.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.OutOfBoundsException;
import minesweeper.plus.services.SimpleBoard;
import minesweeper.plus.viewmodels.SimpleViewModel;

public class Menu extends ScreenAdapter {

    View view;
    SimpleBoard board;
    int level=0;
    private Table table;
    private BitmapFont font;
    private Stage menuStage;
    private Stage gameMenuStage;
    boolean printLevel = false;
    boolean createdLevel = false;

    void createMenu() {
        menuStage = new Stage();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuStage.act(Gdx.graphics.getDeltaTime());

        table = new Table();
        table.setFillParent(true);
        menuStage.addActor(table);


        TextButtonStyle style = new TextButtonStyle();
        style.up = new TextureRegionDrawable(new Texture("menu_button_play.png"));
        style.down = new TextureRegionDrawable(new Texture("menu_button_play.png"));
        style.font = new BitmapFont();
        TextButton button1 = new TextButton("", style);
        button1.setTransform(true);
        button1.setScale(10, 8);
        button1.setX(100);
        button1.setY(100);
        button1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                createdLevel = false;
                printLevel = true;
            }
        });
        table.add(button1);

    }

    void setupLevel() {
        Gdx.input.setInputProcessor(null);
        try {
            Coordinates boardSize = new Coordinates(14, 10, 3);            //change board size here!
            int numberOfMines = 25;            //change number of mines here!

            board = new SimpleBoard(boardSize, numberOfMines);
            view = new SimpleView(new SimpleViewModel(board));
            createdLevel = true;
        } catch (OutOfBoundsException e) {
            System.out.println("Wrong starting conditions");
            createdLevel = false;
            printLevel = false;
        }
    }

    void createGameMenu() {
        gameMenuStage = new Stage();
    }

    @Override
    public void show() {

        createMenu();
        createGameMenu();

        Gdx.input.setInputProcessor(menuStage);
        menuStage.draw();
    }

    @Override
    public void render (float delta) {

        try {
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                ++level;
                if (level >= board.getSize().zValue) {
                    level = board.getSize().zValue - 1;
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                --level;
                if (level < 0) {
                    level = 0;
                }
            }
            if (printLevel) {
                if(!createdLevel) {
                    setupLevel();
                }
                view.draw(level);
                if(board.dead() || board.dead()) { //end game
                    Gdx.input.setInputProcessor(menuStage);
                    printLevel = false; // add timer or a button to retry
                }
            } else {
                ScreenUtils.clear(255, 255, 255, 0);

                board = null;
                view = null;
                menuStage.act(delta);

                menuStage.draw();
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void resize (int width, int height) {
        menuStage.getViewport().update(width, height, true);
    }


    @Override
    public void dispose () {

    }
}

