package minesweeper.plus.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.ScreenUtils;
import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.MineException;
import minesweeper.plus.core.Minefield;
import minesweeper.plus.core.OutOfBoundsException;
import minesweeper.plus.services.Board;
import minesweeper.plus.viewmodels.ViewModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleView implements View{
    List<Texture> textures = new ArrayList<>();

    public int PROC_CNT=0;
    int left_last=-2;
    int right_last=-2;

    Batch batch = new SpriteBatch(); // screen buffer
    BitmapFont font = new BitmapFont(); // temporary, draw font
    ViewModel model;

    public SimpleView(ViewModel viewModel){ // texture list
        textures.add(new Texture("00_hidden.png"));
        textures.add(new Texture("01_null.png"));
        textures.add(new Texture("01_title_1.png"));
        textures.add(new Texture("03_bomb.png"));
        textures.add(new Texture("01_num_01.png"));
        textures.add(new Texture("01_num_02.png"));
        textures.add(new Texture("01_num_03.png"));
        textures.add(new Texture("01_num_04.png"));
        textures.add(new Texture("01_num_05.png"));
        textures.add(new Texture("01_num_06.png"));
        textures.add(new Texture("01_num_07.png"));
        textures.add(new Texture("01_num_08.png"));
        textures.add(new Texture("01_num_09.png"));
        textures.add(new Texture("01_num_10.png"));
        textures.add(new Texture("04_emoji1.png"));
        textures.add(new Texture("04_emoji2.png"));
        textures.add(new Texture("04_emoji3.png"));
        textures.add(new Texture("04_emoji4.png"));
        model = viewModel;
        font.setColor(Color.GOLD);
        font.getData().setScale(2.5f);
    }
    @Override
    public void draw(int level) throws Exception { // gets field and draws on screen
        ScreenUtils.clear(0.35f,0.35f,0.35f,1);
        batch.begin();

        ++PROC_CNT;
        float gp = 15.0f/100;
        float pp = 10.0f/100;


        int width = (int)(Gdx.graphics.getWidth());
        int height = (int)(Gdx.graphics.getHeight());
        float sw = ((float)width*(1.0f-pp))/model.getFieldSize().xValue;
        float sh = ((float)height*(1.0f-gp))/model.getFieldSize().yValue;

        float emoji_pos_x=(width-textures.get(14).getWidth())/2.0f;
        float emoji_pos_y=height-textures.get(14).getHeight();
        boolean input = Gdx.input.isTouched();
        // iterate for every mine
        for(int y=0; y<model.getFieldSize().yValue; ++y){
            for(int x=0; x<model.getFieldSize().xValue; ++x){
                Integer bombs = model.renderAtCoords(new Coordinates(x, y, level));
                int txt_nr = 1;
                if(bombs==-2) txt_nr = 0;
                if(bombs == -1) txt_nr = 3;
                else if(bombs == 0) txt_nr = 1;
                else if(bombs > 0) txt_nr = 3 + bombs;
                batch.draw(textures.get(txt_nr), x*sw, y*sh,
                sw, sh, 0, 0,
                textures.get(txt_nr).getWidth(), textures.get(txt_nr).getHeight(), false, false);
            }
        }

        int emoji_text=0;

        if(input){emoji_text=3;}

        batch.draw(textures.get(14+emoji_text), emoji_pos_x, emoji_pos_y);


        // reassigned for every window size changes
        batch.getProjectionMatrix().setToOrtho2D(0, 0, (int)(width), (int)(height));
        if(input) {
            int x = (int)(Gdx.input.getX()/sw);
            int y = (int)((height - Gdx.input.getY())/sh);
            model.handleClick(new Coordinates(x, y, level));
        }
        batch.end();
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){left_last=PROC_CNT;}
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){right_last=PROC_CNT;}
    }

    boolean left_click_begin(){
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT) && PROC_CNT - left_last > 1;
    }
    boolean left_clicked(){
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }
    boolean left_click_released(){
        return !Gdx.input.isButtonPressed(Input.Buttons.LEFT) && PROC_CNT - left_last == 1;
    }
    boolean right_click_begin(){
        return Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && PROC_CNT - right_last > 1;
    }
    boolean right_clicked(){
        return Gdx.input.isButtonPressed(Input.Buttons.RIGHT);
    }
    boolean right_click_released(){
        return !Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && PROC_CNT - right_last == 1;
    }

}
