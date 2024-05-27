package minesweeper.plus.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import minesweeper.plus.core.Coordinates;
import minesweeper.plus.services.SpotValues;
import minesweeper.plus.viewmodels.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class SimpleView implements View{
    Map<String, Texture> textures = new HashMap<>();

    public int PROC_CNT=0;
    int left_last=-2;
    int right_last=-2;
    SimpleSpotTextures getTexture;
    Batch batch = new SpriteBatch(); // screen buffer
    BitmapFont font = new BitmapFont(); // temporary, draw font
    ViewModel model;

    public SimpleView(ViewModel viewModel){ // texture list
        getTexture = new SimpleSpotTextures();
        textures.put("00_hidden.png", new Texture("00_hidden.png"));
        textures.put("01_num_00.png", new Texture("01_null.png"));
        textures.put("01_title_1.png", new Texture("01_title_1.png"));
        textures.put("03_bomb.png", new Texture("03_bomb.png"));
        textures.put("02_flag.png", new Texture("02_flag.png"));
        textures.put("01_num_01.png", new Texture("01_num_01.png"));
        textures.put("01_num_02.png", new Texture("01_num_02.png"));
        textures.put("01_num_03.png", new Texture("01_num_03.png"));
        textures.put("01_num_04.png", new Texture("01_num_04.png"));
        textures.put("01_num_05.png", new Texture("01_num_05.png"));
        textures.put("01_num_06.png", new Texture("01_num_06.png"));
        textures.put("01_num_07.png", new Texture("01_num_07.png"));
        textures.put("01_num_08.png", new Texture("01_num_08.png"));
        textures.put("01_num_09.png", new Texture("01_num_09.png"));
        textures.put("01_num_10.png", new Texture("01_num_10.png"));
        textures.put("04_emoji1.png", new Texture("04_emoji1.png"));
        textures.put("04_emoji2.png", new Texture("04_emoji2.png"));
        textures.put("04_emoji3.png", new Texture("04_emoji3.png"));
        textures.put("04_emoji4.png", new Texture("04_emoji4.png"));
        model = viewModel;
        font.setColor(Color.GOLD);
        font.getData().setScale(2.5f);
    }
    @Override
    public void draw(int level) throws Exception { // gets field and draws on screen
        ScreenUtils.clear(0.35f, 0.35f, 0.35f, 1);
        batch.begin();

        ++PROC_CNT;
        float gp = 15.0f / 100;
        float pp = 10.0f / 100;

        int width = (int) (Gdx.graphics.getWidth());
        int height = (int) (Gdx.graphics.getHeight());
        float sw = ((float) width * (1.0f - pp)) / model.getFieldSize().xValue;
        float sh = ((float) height * (1.0f - gp)) / model.getFieldSize().yValue;

        float emoji_pos_x = (width - textures.get("04_emoji1.png").getWidth()) / 2.0f;
        float emoji_pos_y = height - textures.get("04_emoji1.png").getHeight();
        boolean inputLMB = left_click_released();
        boolean inputRMB = right_click_released();
        // iterate for every mine
        for (int y = 0; y < model.getFieldSize().yValue; ++y) {
            for (int x = 0; x < model.getFieldSize().xValue; ++x) {
                SpotValues value = model.renderAtCoords(new Coordinates(x, y, level));

                String txt_nr = "04_emoji1.png";
                Texture t = textures.get(getTexture.getTexture(value));
                batch.draw(t, x * sw, y * sh,
                        sw, sh, 0, 0,
                        t.getWidth(), t.getHeight(), false, false);
            }
        }

        String emoji_text = "04_emoji1.png";
        if (!model.dead()) {
            if (left_clicked()) {
                emoji_text = "04_emoji4.png";
            }
        } else {
            emoji_text = "04_emoji3.png";
        }
        if (model.won()) {
            emoji_text = "04_emoji2.png";
        }
        batch.draw(textures.get(emoji_text), emoji_pos_x, emoji_pos_y);

        // reassigned for every window size changes
        batch.getProjectionMatrix().setToOrtho2D(0, 0, (int) (width), (int) (height));

        if (!model.dead() && !model.won()) {            //handling all clicks
            if (inputLMB || inputRMB) {
                int x = (int) (Gdx.input.getX() / sw);
                int y = (int) ((height - Gdx.input.getY()) / sh);
                if(inputLMB)
                    model.leftClick(new Coordinates(x, y, level));
                else if(inputRMB)
                    model.rightClick(new Coordinates(x, y, level));

            }
        }
        batch.end();
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            left_last = PROC_CNT;
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            right_last = PROC_CNT;
        }
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
