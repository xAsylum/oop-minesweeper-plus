package minesweeper.plus.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import minesweeper.plus.services.Board;
import minesweeper.plus.services.SimpleBoard;
import minesweeper.plus.viewmodels.SimpleViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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

    private Consumer<Integer> updateBlock;
    Map<String, TextButtonStyle> styles;

    void setupStyles() {
        styles = new HashMap<>();
        TextButtonStyle playButtonStyle = new TextButtonStyle();
        playButtonStyle.up = new TextureRegionDrawable(new Texture("menu_button_play.png"));
        playButtonStyle.down = new TextureRegionDrawable(new Texture("menu_button_play.png"));
        playButtonStyle.font = new BitmapFont();
        styles.put("play", playButtonStyle);

        TextButtonStyle settingsButtonStyle = new TextButtonStyle();
        settingsButtonStyle.up = new TextureRegionDrawable(new Texture("menu_settings_button.png"));
        settingsButtonStyle.down = new TextureRegionDrawable(new Texture("menu_settings_button.png"));
        settingsButtonStyle.font = new BitmapFont();
        styles.put("settings", settingsButtonStyle);

        TextButtonStyle buttonUpStyle = new TextButtonStyle();
        buttonUpStyle.up = new TextureRegionDrawable(new Texture("game_button_up.png"));
        buttonUpStyle.down = new TextureRegionDrawable(new Texture("game_button_up.png"));
        buttonUpStyle.font = new BitmapFont();
        styles.put("up", buttonUpStyle);

        TextButtonStyle buttonDownStyle = new TextButtonStyle();
        buttonDownStyle.up = new TextureRegionDrawable(new Texture("game_button_down.png"));
        buttonDownStyle.down = new TextureRegionDrawable(new Texture("game_button_down.png"));
        buttonDownStyle.font = new BitmapFont();
        styles.put("down", buttonDownStyle);

        TextButtonStyle blockStyle = new TextButtonStyle();
        blockStyle.up = new TextureRegionDrawable(new Texture("menu_button.png"));
        blockStyle.down = new TextureRegionDrawable(new Texture("menu_button.png"));
        blockStyle.font = new BitmapFont();
        styles.put("block", blockStyle);


        TextButtonStyle exitStyle = new TextButtonStyle();
        exitStyle.up = new TextureRegionDrawable(new Texture("game_button_exit.png"));
        exitStyle.down = new TextureRegionDrawable(new Texture("game_button_exit.png"));
        exitStyle.font = new BitmapFont();
        styles.put("exit", exitStyle);
    }

    void createMenu() {
        menuStage = new Stage();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuStage.act(Gdx.graphics.getDeltaTime());
        TextButton playButton = new TextButton("", styles.get("play"));
        playButton.setTransform(true);
        playButton.setWidth(100);
        playButton.setHeight(100);
        playButton.setPosition(200, 100);
        playButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                createdLevel = false;
                printLevel = true;
            }
        });

        TextButton settingsButton = new TextButton("", styles.get("settings"));
        menuStage.addActor(playButton);
        settingsButton.setTransform(true);
        settingsButton.setWidth(100);
        settingsButton.setHeight(100);
        settingsButton.setPosition(Gdx.graphics.getWidth() - 2 * settingsButton.getWidth() - 100, 100);
        menuStage.addActor(settingsButton);

    }



    void setupLevel() {
        Gdx.input.setInputProcessor(gameMenuStage);
        try {
            Coordinates boardSize = new Coordinates(14, 10, 3);            //change board size here!
            int numberOfMines = 25;            //change number of mines here!

            board = new SimpleBoard(boardSize, numberOfMines);
            view = new SimpleView(new SimpleViewModel(board));
            createdLevel = true;
            level = 0;
            updateBlock.accept(0);
        } catch (OutOfBoundsException e) {
            System.out.println("Wrong starting conditions");
            createdLevel = false;
            printLevel = false;
        }
    }

    void createGameMenu() {
        gameMenuStage = new Stage();

        TextButton upButton = new TextButton("", styles.get("up"));
        upButton.setTransform(true);
        upButton.setWidth(60);
        upButton.setHeight(60);
        upButton.setPosition(Gdx.graphics.getWidth() - upButton.getWidth() - 2, Gdx.graphics.getHeight() - 130);
        upButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                --level;
                if (level < 0) {
                    level = 0;
                }
                updateBlock.accept(level);
            }
        });
        gameMenuStage.addActor(upButton);

        TextButton downButton = new TextButton("", styles.get("down"));
        downButton.setTransform(true);
        downButton.setWidth(60);
        downButton.setHeight(60);
        downButton.setPosition(Gdx.graphics.getWidth() - upButton.getWidth() - 2, 0);
        downButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                ++level;
                if (level >= board.getSize().zValue) {
                    level = board.getSize().zValue - 1;
                }
                updateBlock.accept(level);
            }
        });


        TextButton exitButton = new TextButton("", styles.get("exit"));
        exitButton.setTransform(true);
        exitButton.setWidth(60);
        exitButton.setHeight(60);
        exitButton.setPosition(Gdx.graphics.getWidth() - exitButton.getWidth() - 2, Gdx.graphics.getHeight() - exitButton.getHeight() * 1.1f);
        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                printLevel = false;
                Gdx.input.setInputProcessor(menuStage);
            }
        });

        TextButton block = new TextButton("", styles.get("block"));
        block.setTransform(true);
        block.setWidth(60);
        block.setHeight(60);
        block.setPosition(upButton.getX(), upButton.getY() - block.getHeight());
        updateBlock = (n) -> {
            block.setPosition(upButton.getX(), (board.getSize().zValue - n - 1) * (upButton.getY() - downButton.getY())/board.getSize().zValue + block.getHeight());
        };
        gameMenuStage.addActor(block);

        gameMenuStage.addActor(downButton);
        gameMenuStage.addActor(exitButton);
    }

    @Override
    public void show() {
        setupStyles();
        createMenu();
        createGameMenu();

        Gdx.input.setInputProcessor(menuStage);
        menuStage.draw();
    }

    @Override
    public void render (float delta) {

        try {
            if (printLevel) {
                if(!createdLevel) {
                    setupLevel();
                }
                view.draw(level);
                if(board.dead() || board.dead()) { //end game
                    //Gdx.input.setInputProcessor(menuStage);
                    ///printLevel = false; // add timer or a button to retry
                }
                gameMenuStage.act(delta);
                gameMenuStage.draw();
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
        gameMenuStage.getViewport().update(width, height, true);
    }


    @Override
    public void dispose () {

    }
}

