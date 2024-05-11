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
	int level=0;
	
	@Override
	public void create () {
		try {
			view = new SimpleView();
			field = new SimpleMinefield(new Coordinates(20, 20, 10), 200, new Coordinates(1, 1, 0));
		}
		catch (Exception e){
			System.out.println("MyGdx");
			exit(1);
		}
	}


	@Override
	public void render () {

		try {
			if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){++level; if(level >= field.getSize().zValue){level=field.getSize().zValue-1;}}
			if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){--level; if(level<0){level=0;}}
			view.draw(field, level);
		}
		catch (Exception e){}
	}
	
	@Override
	public void dispose () {
		;
	}
}
