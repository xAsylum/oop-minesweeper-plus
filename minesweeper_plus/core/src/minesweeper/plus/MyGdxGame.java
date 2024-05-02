package minesweeper.plus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.abs;

public class MyGdxGame extends ApplicationAdapter {		//to be deleted, only as template
	SpriteBatch batch;
	Texture img;
	int x, y;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("micek.jpeg");
		x=0; y=0;
	}

	Random random = new Random();


	@Override
	public void render () {
		ScreenUtils.clear(1,1,1,1);
		batch.begin();

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			x+=50;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			x-=50;
		}
        int cos;

		batch.draw(img, (int)x,(int)y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
