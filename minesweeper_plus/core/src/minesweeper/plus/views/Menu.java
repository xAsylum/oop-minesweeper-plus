package minesweeper.plus.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import jdk.tools.jmod.Main;
import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.OutOfBoundsException;
import minesweeper.plus.services.SimpleBoard;
import minesweeper.plus.viewmodels.SimpleViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import static minesweeper.plus.views.Menu.MenuToRender.*;

public class Menu extends ScreenAdapter {


    enum MenuToRender {
      MainMenu,
      Options
    };

    MenuToRender whatToRender = MainMenu;

    View view;
    SimpleBoard board;
    int level=0;
    private Table table;
    private BitmapFont font;
    private Stage menuStage;
    private Stage gameMenuStage;
    private Stage optionsStage;
    boolean printLevel = false;
    boolean createdLevel = false;
    private Consumer<Integer> updateBlock;
    Map<String, TextButtonStyle> buttonStyles;
    Map<String, LabelStyle> labelStyles;
    private int x = 10, y = 10, z = 5, bombsCount = 10;
    void setupFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixelFont.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        font = generator.generateFont(parameter);
    }

    void setupStyles() {
        buttonStyles = new HashMap<>();
        labelStyles = new HashMap<>();


        LabelStyle title = new LabelStyle();
        title.font = font;
        labelStyles.put("title", title);

        TextButtonStyle playButtonStyle = new TextButtonStyle();
        playButtonStyle.up = new TextureRegionDrawable(new Texture("menu_button_play.png"));
        playButtonStyle.down = new TextureRegionDrawable(new Texture("menu_button_play.png"));
        playButtonStyle.font = font;
        buttonStyles.put("play", playButtonStyle);

        TextButtonStyle settingsButtonStyle = new TextButtonStyle();
        settingsButtonStyle.up = new TextureRegionDrawable(new Texture("menu_settings_button.png"));
        settingsButtonStyle.down = new TextureRegionDrawable(new Texture("menu_settings_button.png"));
        settingsButtonStyle.font = new BitmapFont();
        buttonStyles.put("settings", settingsButtonStyle);

        TextButtonStyle buttonUpStyle = new TextButtonStyle();
        buttonUpStyle.up = new TextureRegionDrawable(new Texture("game_button_up.png"));
        buttonUpStyle.down = new TextureRegionDrawable(new Texture("game_button_up.png"));
        buttonUpStyle.font = new BitmapFont();
        buttonStyles.put("up", buttonUpStyle);

        TextButtonStyle buttonIncrStyle = new TextButtonStyle();
        buttonIncrStyle.up = new TextureRegionDrawable(new Texture("menu_button_inc.png"));
        buttonIncrStyle.down = new TextureRegionDrawable(new Texture("menu_button_inc.png"));
        buttonIncrStyle.font = new BitmapFont();
        buttonStyles.put("inc", buttonIncrStyle);

        TextButtonStyle buttonDecrStyle = new TextButtonStyle();
        buttonDecrStyle.up = new TextureRegionDrawable(new Texture("game_button_up.png"));
        buttonDecrStyle.down = new TextureRegionDrawable(new Texture("game_button_up.png"));
        buttonDecrStyle.font = new BitmapFont();
        buttonStyles.put("decr", buttonDecrStyle);

        TextButtonStyle buttonDownStyle = new TextButtonStyle();
        buttonDownStyle.up = new TextureRegionDrawable(new Texture("game_button_down.png"));
        buttonDownStyle.down = new TextureRegionDrawable(new Texture("game_button_down.png"));
        buttonDownStyle.font = new BitmapFont();
        buttonStyles.put("down", buttonDownStyle);

        TextButtonStyle blockStyle = new TextButtonStyle();
        blockStyle.up = new TextureRegionDrawable(new Texture("menu_button.png"));
        blockStyle.down = new TextureRegionDrawable(new Texture("menu_button.png"));
        blockStyle.font = new BitmapFont();
        buttonStyles.put("block", blockStyle);


        TextButtonStyle exitStyle = new TextButtonStyle();
        exitStyle.up = new TextureRegionDrawable(new Texture("game_button_exit.png"));
        exitStyle.down = new TextureRegionDrawable(new Texture("game_button_exit.png"));
        exitStyle.font = new BitmapFont();
        buttonStyles.put("exit", exitStyle);
    }

    void createMenu() {
        menuStage = new Stage();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuStage.act(Gdx.graphics.getDeltaTime());


        TextButton playButton = new TextButton("", buttonStyles.get("play"));
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

        TextButton settingsButton = new TextButton("", buttonStyles.get("settings"));
        menuStage.addActor(playButton);
        settingsButton.setTransform(true);
        settingsButton.setWidth(100);
        settingsButton.setHeight(100);
        settingsButton.setPosition(Gdx.graphics.getWidth() - 2 * settingsButton.getWidth() - 100, 100);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(optionsStage);
                whatToRender = Options;
            }
        });
        menuStage.addActor(settingsButton);

        Label title = new Label("Minesweeper+", labelStyles.get("title"));
        title.setColor(0, 0, 0, 1);
        title.setFontScale(2, 2);
        title.setPosition((Gdx.graphics.getWidth() - 2 * title.getWidth())/2.0f, Gdx.graphics.getHeight() - 6 * title.getHeight());
        menuStage.addActor(title);

    }



    void setupLevel() {
        Gdx.input.setInputProcessor(gameMenuStage);
        try {
            Coordinates boardSize = new Coordinates(x, y, z);            //change board size here!
            int numberOfMines = bombsCount;            //change number of mines here!

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
    void createOptionsMenu() {
        optionsStage = new Stage();
        TextButton exitButton = new TextButton("", buttonStyles.get("exit"));
        exitButton.setTransform(true);
        exitButton.setWidth(60);
        exitButton.setHeight(60);
        exitButton.setPosition(Gdx.graphics.getWidth() - exitButton.getWidth() - 2, Gdx.graphics.getHeight() - exitButton.getHeight() * 1.1f);
        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(menuStage);
                whatToRender = MainMenu;
            }
        });
        optionsStage.addActor(exitButton);
    }
    void createGameMenu() {
        gameMenuStage = new Stage();

        TextButton upButton = new TextButton("", buttonStyles.get("up"));
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

        TextButton downButton = new TextButton("", buttonStyles.get("down"));
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


        TextButton exitButton = new TextButton("", buttonStyles.get("exit"));
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

        TextButton block = new TextButton("", buttonStyles.get("block"));
        block.setTransform(true);
        block.setWidth(60);
        block.setHeight(60);
        block.setPosition(upButton.getX(), upButton.getY() - block.getHeight());
        updateBlock = (n) -> {
            if(z > 1)
                block.setPosition(upButton.getX(),  upButton.getHeight()  + ((z - n - 1) * (upButton.getY() -  2 * upButton.getHeight()))/(z - 1));
            else
                block.setPosition(upButton.getX(), upButton.getHeight());
        };
        gameMenuStage.addActor(block);

        gameMenuStage.addActor(downButton);
        gameMenuStage.addActor(exitButton);
    }

    @Override
    public void show() {
        setupFonts();
        setupStyles();
        createMenu();
        createGameMenu();
        createOptionsMenu();

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
                if(whatToRender == MainMenu) {
                    menuStage.act(delta);
                    menuStage.draw();
                }
                else if (whatToRender == Options) {
                    optionsStage.act(delta);
                    optionsStage.draw();
                }
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

