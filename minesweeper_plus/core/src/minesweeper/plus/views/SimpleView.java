package minesweeper.plus.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.ScreenUtils;
import minesweeper.plus.core.Minefield;
import minesweeper.plus.services.Board;

import java.util.ArrayList;
import java.util.List;

public class SimpleView implements View{


   Minefield field;

    List<Texture> textures = new ArrayList<>();

    Batch batch = new SpriteBatch();

    public SimpleView(){
        textures.add(new Texture("00_hidden.png"));
        textures.add(new Texture("01_null.png"));
        textures.add(new Texture("01_title_1.png"));
    }



    @Override
    public void draw(Minefield field, int level) throws Exception {
        ScreenUtils.clear(1,1,1,1);
        batch.begin();

        this.field = field;
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        float sw = ((float)width)/field.getSize().xValue;
        float sh = ((float)height)/field.getSize().yValue;

        for(int y=0; y<field.getSize().yValue; ++y){
            for(int x=0; x<field.getSize().xValue; ++x){
                batch.draw(textures.get(2), x*sw, y*sh,
                sw, sh, 0, 0,
                textures.get(2).getWidth(), textures.get(2).getHeight(), false, false);
            }
        }
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        batch.end();
    }

}
