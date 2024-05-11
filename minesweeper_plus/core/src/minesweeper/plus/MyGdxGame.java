package minesweeper.plus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.SimpleMinefield;
import minesweeper.plus.services.SimpleBoard;
import minesweeper.plus.views.SimpleView;
import minesweeper.plus.views.View;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.System.exit;

public class MyGdxGame extends ApplicationAdapter {		//to be deleted, only as template


	View view;
	SimpleMinefield field;
	
	@Override
	public void create () {
		try {
			view = new SimpleView();
			field = new SimpleMinefield(new Coordinates(20, 20, 20), 20, new Coordinates(1, 1, 1));
		}
		catch (Exception e){exit(1);}
	}

	Random random = new Random();


	@Override
	public void render () {
		try {
			view.draw(field, 0);
		}
		catch (Exception e){}
	}
	
	@Override
	public void dispose () {
		;
	}
}
