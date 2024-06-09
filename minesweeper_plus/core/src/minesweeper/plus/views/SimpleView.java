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
import minesweeper.plus.core.SpotValues;
import minesweeper.plus.viewmodels.ViewModel;

public class SimpleView implements View{
    public int procCnt =0;
    int leftLast =-2;
    int rightLast =-2;
    SimpleSpotTextures getTexture;
    Batch batch = new SpriteBatch(); // screen buffer
    BitmapFont font = new BitmapFont(); // temporary, draw font
    ViewModel model;

    public SimpleView(ViewModel viewModel){ // texture list
        getTexture = new SimpleSpotTextures();

        model = viewModel;
        font.setColor(Color.GOLD);
        font.getData().setScale(2.5f);
    }

    public void drawGUI() {

    }

    @Override
    public void draw(int level) throws Exception { // gets field and draws on screen
        ScreenUtils.clear(0.35f, 0.35f, 0.35f, 1);
        batch.begin();

        ++procCnt;
        float gp = 15.0f / 100;
        float pp = 10.0f / 100;

        int width = (int) (Gdx.graphics.getWidth());
        int height = (int) (Gdx.graphics.getHeight());
        float sw = ((float) width * (1.0f - pp)) / model.getFieldSize().xValue;
        float sh = ((float) height * (1.0f - gp)) / model.getFieldSize().yValue;

        float emoji_pos_x = (width) / 2.5f;
        float emoji_pos_y = height;
        boolean inputLMB = left_click_released();
        boolean inputRMB = right_click_released();
        // iterate for every tile
        for (int y = 0; y < model.getFieldSize().yValue; ++y) {
            for (int x = 0; x < model.getFieldSize().xValue; ++x) {
                SpotValues value = model.renderAtCoords(new Coordinates(x, y, level));

                Texture tile = getTexture.getTexture(value);
                batch.draw(tile, x * sw, y * sh,
                        sw, sh, 0, 0,
                        tile.getWidth(), tile.getHeight(), false, false);
            }
        }

        Texture emoji = new Texture("04_emoji1.png");
        if (!model.dead()) {
            if (left_clicked()) {
                emoji = new Texture("04_emoji4.png");
            }
        } else {
            emoji = new Texture("04_emoji2.png");
        }
        if (model.won()) {
            emoji = new Texture("04_emoji3.png");
        }
        batch.draw(emoji, (emoji_pos_x) , (emoji_pos_y - (float) (emoji.getHeight() * 5 * height) /600), (float) ((emoji.getHeight() * 5) * width) /800, (float) ((emoji.getWidth() * 5) * height) /600);

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
            leftLast = procCnt;
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            rightLast = procCnt;
        }
    }

    boolean left_click_begin(){
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT) && procCnt - leftLast > 1;
    }
    boolean left_clicked(){
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }
    boolean left_click_released(){
        return !Gdx.input.isButtonPressed(Input.Buttons.LEFT) && procCnt - leftLast == 1;
    }
    boolean right_click_begin(){
        return Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && procCnt - rightLast > 1;
    }
    boolean right_clicked(){
        return Gdx.input.isButtonPressed(Input.Buttons.RIGHT);
    }
    boolean right_click_released(){
        return !Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && procCnt - rightLast == 1;
    }

}
