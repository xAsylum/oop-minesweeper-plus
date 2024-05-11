package minesweeper.plus.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.ScreenUtils;
import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.Minefield;
import minesweeper.plus.services.Board;

import java.util.ArrayList;
import java.util.List;

public class SimpleView implements View{



    List<Texture> textures = new ArrayList<>();

    Batch batch = new SpriteBatch(); // screen buffer

    public SimpleView(){ // texture list
        textures.add(new Texture("00_hidden.png"));
        textures.add(new Texture("01_null.png"));
        textures.add(new Texture("01_title_1.png"));
        textures.add(new Texture("03_bomb.png"));
    }



    @Override
    public void draw(Minefield field, int level) throws Exception { // gets field and draws on screen
        ScreenUtils.clear(1,1,1,1);
        batch.begin();

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        float sw = ((float)width)/field.getSize().xValue;
        float sh = ((float)height)/field.getSize().yValue;


        // iterate for every mine
        for(int y=0; y<field.getSize().yValue; ++y){
            for(int x=0; x<field.getSize().xValue; ++x){

                int txt_nr;
                int bombs = field.getBombsNumber(new Coordinates(x,y,level));
                if(bombs==-1){txt_nr = 3;}
                else{txt_nr=1;}

                batch.draw(textures.get(txt_nr), x*sw, y*sh,
                sw, sh, 0, 0,
                textures.get(2).getWidth(), textures.get(2).getHeight(), false, false);
            }
        }

        // reassigned for every window size changes
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        batch.end();
    }

}
