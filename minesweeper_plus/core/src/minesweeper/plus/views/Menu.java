package minesweeper.plus.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.OutOfBoundsException;
import minesweeper.plus.services.SimpleBoard;
import minesweeper.plus.viewmodels.SimpleViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import static minesweeper.plus.views.Menu.MenuToRender.*;

import minesweeper.plus.stats.*;

public class Menu extends ScreenAdapter {

    enum MenuToRender {
        MainMenu,
        Options,
        Statistics
    }

    MenuToRender whatToRender = MainMenu;
    final Statistics stats = new Statistics();
    View view;
    SimpleBoard board;
    int level=0;
    private BitmapFont font;
    private Stage menuStage;
    private Stage gameMenuStage;
    private Stage optionsStage;
    private Stage statisticsStage;
    boolean printLevel = false;
    boolean createdLevel = false;
    private Consumer<Integer> updateBlock;
    private Consumer<Integer> updateStatistics;
    Map<String, TextButtonStyle> buttonStyles;
    Map<String, LabelStyle> labelStyles;
    private Integer boardX = 10, boardY = 10, boardZ = 2, boardBombsCount = 21;
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

        TextButtonStyle statsButtonStyle = new TextButtonStyle();
        statsButtonStyle.up = new TextureRegionDrawable(new Texture("menu_button_statistics.png"));
        statsButtonStyle.down = new TextureRegionDrawable(new Texture("menu_button_statistics.png"));
        statsButtonStyle.font = font;
        buttonStyles.put("statistics", statsButtonStyle);

        TextButtonStyle deleteButtonStyle = new TextButtonStyle();
        deleteButtonStyle.up = new TextureRegionDrawable(new Texture("menu_button_delete.png"));
        deleteButtonStyle.down = new TextureRegionDrawable(new Texture("menu_button_delete.png"));
        deleteButtonStyle.font = font;
        buttonStyles.put("delete", deleteButtonStyle);


        TextButtonStyle saveButtonStyle = new TextButtonStyle();
        saveButtonStyle.up = new TextureRegionDrawable(new Texture("menu_button_save.png"));
        saveButtonStyle.down = new TextureRegionDrawable(new Texture("menu_button_save.png"));
        saveButtonStyle.font = font;
        buttonStyles.put("save", saveButtonStyle);

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
        buttonDecrStyle.up = new TextureRegionDrawable(new Texture("menu_button_decr.png"));
        buttonDecrStyle.down = new TextureRegionDrawable(new Texture("menu_button_decr.png"));
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
        blockStyle.font = font;
        buttonStyles.put("block", blockStyle);

        TextButtonStyle blockLongStyle = new TextButtonStyle();
        blockLongStyle.up = new TextureRegionDrawable(new Texture("menu_button_long.png"));
        blockLongStyle.down = new TextureRegionDrawable(new Texture("menu_button_long.png"));
        blockLongStyle.font = font;
        buttonStyles.put("block_long", blockLongStyle);

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
        playButton.setPosition(100, 100);
        playButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                createdLevel = false;
                printLevel = true;
            }
        } );

        TextButton settingsButton = new TextButton("", buttonStyles.get("settings"));
        menuStage.addActor(playButton);
        settingsButton.setTransform(true);
        settingsButton.setWidth(100);
        settingsButton.setHeight(100);
        settingsButton.setPosition(Gdx.graphics.getWidth() - 2 * settingsButton.getWidth(), 100);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(optionsStage);
                whatToRender = Options;
            }
        });
        menuStage.addActor(settingsButton);

        TextButton statisticsButton = new TextButton("", buttonStyles.get("statistics"));
        menuStage.addActor(statisticsButton);
        statisticsButton.setTransform(true);
        statisticsButton.setWidth(100);
        statisticsButton.setHeight(100);
        statisticsButton.setPosition((Gdx.graphics.getWidth() - settingsButton.getWidth())/2, 100);
        statisticsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(statisticsStage);
                updateStatistics.accept(0);
                whatToRender = Statistics;
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
            Coordinates boardSize = new Coordinates(boardX, boardY, boardZ);            //change board size here!
            int numberOfMines = boardBombsCount;            //change number of mines here!

            board = new SimpleBoard(boardSize, numberOfMines);
            view = new SimpleView(new SimpleViewModel(board));
            createdLevel = true;
            level = 0;
            updateBlock.accept(0);
        } catch (OutOfBoundsException e) {
            //System.out.println("Wrong starting conditions");
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

        TextButton incrX = new TextButton("", buttonStyles.get("inc"));
        TextButton incrY = new TextButton("", buttonStyles.get("inc"));
        TextButton incrZ = new TextButton("", buttonStyles.get("inc"));
        TextButton incrBombs = new TextButton("", buttonStyles.get("inc"));
        TextButton decrX = new TextButton("", buttonStyles.get("decr"));
        TextButton decrY = new TextButton("", buttonStyles.get("decr"));
        TextButton decrZ = new TextButton("", buttonStyles.get("decr"));
        TextButton decrBombs = new TextButton("", buttonStyles.get("decr"));
        TextButton xLabel = new TextButton("Width", buttonStyles.get("block_long"));
        TextButton yLabel = new TextButton("Height", buttonStyles.get("block_long"));
        TextButton zLabel = new TextButton("Depth", buttonStyles.get("block_long"));
        TextButton bombLabel = new TextButton("No. Bombs", buttonStyles.get("block_long"));
        TextButton xCount = new TextButton("", buttonStyles.get("block"));
        TextButton yCount = new TextButton("", buttonStyles.get("block"));
        TextButton zCount = new TextButton("", buttonStyles.get("block"));
        TextButton bombCount = new TextButton("", buttonStyles.get("block"));
        TextButton[] nice = {incrBombs, incrZ, incrY, incrX , decrBombs, decrZ, decrY, decrX, bombLabel, zLabel, yLabel, xLabel, bombCount, zCount, yCount, xCount};
        Integer[] nice2 = {boardBombsCount, boardZ, boardY, boardX};
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            nice[finalI + 12].setText(String.valueOf(nice2[finalI]));
            nice[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if( finalI== 0) {
                        if(boardBombsCount < 63999)
                            boardBombsCount++;
                        nice[finalI + 12].setText(String.valueOf(boardBombsCount));
                    }
                    else if( finalI== 1) {
                        if(boardZ < 40)
                            boardZ++;
                        nice[finalI + 12].setText(String.valueOf(boardZ));
                    }
                    else if( finalI== 2) {
                        if(boardY < 40)
                            boardY++;
                        nice[finalI + 12].setText(String.valueOf(boardY));
                    }
                    else {
                        if(boardX < 40)
                            boardX++;
                        nice[finalI + 12].setText(String.valueOf(boardX));
                    }
                }
            });
            nice[i+4].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if( finalI== 0) {
                        if(boardBombsCount > 1)
                            boardBombsCount--;
                        nice[finalI + 12].setText(String.valueOf(boardBombsCount));
                    }
                    else if( finalI== 1) {
                        if (boardZ > 1)
                            boardZ--;
                        nice[finalI + 12].setText(String.valueOf(boardZ));
                    }
                    else if( finalI== 2) {
                        if(boardY > 1)
                            boardY--;
                        nice[finalI + 12].setText(String.valueOf(boardY));
                    }
                    else {
                        if(boardX > 1)
                            boardX--;
                        nice[finalI + 12].setText(String.valueOf(boardX));
                    }
                }
            });
            nice[i].setWidth(60);
            nice[i].setHeight(60);
            nice[i+4].setWidth(60);
            nice[i+4].setHeight(60);
            nice[i].setPosition(8*nice[i].getWidth(), 2*(i + 0.25f)*nice[i].getHeight());
            nice[i+4].setPosition(7*nice[i].getWidth(), 2*(i + 0.25f)*nice[i+4].getHeight());
            nice[i+8].setWidth(240);
            nice[i+8].setHeight(60);
            nice[i+8].setPosition(nice[i].getWidth(), 2*(i + 0.25f)*nice[i+4].getHeight());
            nice[i+8].setColor(0.45f, 0.45f, 0.45f, 1f);
            nice[i+12].setColor(0.45f, 0.45f, 0.45f, 1f);
            nice[i+12].setWidth(60);
            nice[i+12].setHeight(60);
            nice[i+12].setPosition(6*nice[i].getWidth(), 2*(i + 0.25f)*nice[i+4].getHeight());
            optionsStage.addActor(nice[i]);
            optionsStage.addActor(nice[i+4]);
            optionsStage.addActor(nice[i+8]);
            optionsStage.addActor(nice[i+12]);
        }
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
                if(board.won()) {
                    //System.out.println("won");
                    stats.won(new StatsIn(new Coordinates(boardX, boardY, boardZ), boardBombsCount));
                }
                if(!board.alive()) {
                    //System.out.println("lost");
                    stats.lost(new StatsIn(new Coordinates(boardX, boardY, boardZ), boardBombsCount));
                }
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
            if(boardZ > 1)
                block.setPosition(upButton.getX(),  upButton.getHeight()  + ((boardZ - n - 1) * (upButton.getY() -  2 * upButton.getHeight()))/(boardZ - 1));
            else
                block.setPosition(upButton.getX(), upButton.getHeight());
        };
        gameMenuStage.addActor(block);

        gameMenuStage.addActor(downButton);
        gameMenuStage.addActor(exitButton);
    }
    int getWins() {
        return stats.getWins(new StatsIn(new Coordinates(boardX, boardY, boardZ), boardBombsCount));
    }
    int getLooses() {
        return stats.getLooses(new StatsIn(new Coordinates(boardX, boardY, boardZ), boardBombsCount));
    }
    int getTotal() {
        return stats.getTotal(new StatsIn(new Coordinates(boardX, boardY, boardZ), boardBombsCount));
    }
    void createStatisticsMenu() {
        statisticsStage = new Stage();

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
        statisticsStage.addActor(exitButton);

        TextButton saveButton = new TextButton("", buttonStyles.get("save"));
        saveButton.setTransform(true);
        saveButton.setWidth(60);
        saveButton.setHeight(60);
        saveButton.setPosition(Gdx.graphics.getWidth() - 2 * exitButton.getWidth() - 2, Gdx.graphics.getHeight() - exitButton.getHeight() * 1.1f);
        saveButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stats.save();
            }
        });
        statisticsStage.addActor(saveButton);

        TextButton deleteButton = new TextButton("", buttonStyles.get("delete"));
        deleteButton.setTransform(true);
        deleteButton.setWidth(60);
        deleteButton.setHeight(60);
        deleteButton.setPosition(Gdx.graphics.getWidth() - 3 * exitButton.getWidth() - 2, Gdx.graphics.getHeight() - exitButton.getHeight() * 1.1f);
        deleteButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stats.delete();
                updateStatistics.accept(0);
            }
        });
        statisticsStage.addActor(deleteButton);

        TextButton winsLabel = new TextButton("Games won", buttonStyles.get("block_long"));
        TextButton losesLabel = new TextButton("Games lost", buttonStyles.get("block_long"));
        TextButton totalLabel = new TextButton("Total", buttonStyles.get("block_long"));

        TextButton wins = new TextButton(String.valueOf(getWins()), buttonStyles.get("block"));
        TextButton loses = new TextButton(String.valueOf(getLooses()), buttonStyles.get("block"));
        TextButton total = new TextButton(String.valueOf(getTotal()), buttonStyles.get("block"));

        TextButton[] nice = {totalLabel, losesLabel, winsLabel, total, loses, wins};
        for (int i = 0; i < 3; i++ ) {
            nice[i].setWidth(240);
            nice[i].setHeight(60);
            nice[i].setPosition(0.5f * nice[i].getWidth(), 2 * (i + 0.5f) * nice[i].getHeight());
            nice[i].setColor(0.45f, 0.45f, 0.45f, 1f);

            nice[i + 3].setWidth(60);
            nice[i + 3].setHeight(60);
            nice[i + 3].setPosition(1.75f * nice[i].getWidth(), 2 * (i + 0.5f) * nice[i].getHeight());
            nice[i + 3].setColor(0.45f, 0.45f, 0.45f, 1f);

            statisticsStage.addActor(nice[i]);

            statisticsStage.addActor(nice[i + 3]);
        }
        updateStatistics = integer -> {
            wins.setText(String.valueOf(getWins()));
            loses.setText(String.valueOf(getLooses()));
            total.setText(String.valueOf(getTotal()));
            //System.out.println("update");
        };
        updateStatistics.accept(0);
    }


    @Override
    public void show() {
        setupFonts();
        setupStyles();
        createMenu();
        createGameMenu();
        createOptionsMenu();
        createStatisticsMenu();
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
                if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
                    ++level; if(level >= board.getSize().zValue){level=board.getSize().zValue-1;}
                    updateBlock.accept(level);
                }
                if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                    --level; if(level<0){level=0;}
                    updateBlock.accept(level);
                }
                gameMenuStage.act(delta);
                gameMenuStage.draw();
            } else {
                ScreenUtils.clear(0.35f, 0.35f, 0.35f, 1);

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
                else if (whatToRender == Statistics) {
                    statisticsStage.act(delta);
                    statisticsStage.draw();
                }
            }
        } catch (Exception ignored) {}
    }

    @Override
    public void resize (int width, int height) {
        menuStage.getViewport().update(width, height, true);
        gameMenuStage.getViewport().update(width, height, true);
        optionsStage.getViewport().update(width, height, true);
        statisticsStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {}
}

