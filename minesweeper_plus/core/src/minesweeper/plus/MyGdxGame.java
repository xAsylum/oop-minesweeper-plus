package minesweeper.plus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import minesweeper.plus.core.Coordinates;
import minesweeper.plus.services.SimpleBoard;
import minesweeper.plus.viewmodels.SimpleViewModel;
import minesweeper.plus.views.SimpleView;
import minesweeper.plus.views.View;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.System.exit;

public class MyGdxGame extends ApplicationAdapter {		//to be deleted, only as template


	View view;
	SimpleBoard board;
	int level=0;
	
	@Override
	public void create () {
		try {
			board = new SimpleBoard(new Coordinates(10, 10, 2), 20);
			view = new SimpleView(new SimpleViewModel(board));
		}
		catch (Exception e){
			System.out.println("MyGdx");
			exit(1);
		}
	}


	@Override
	public void render () {

		try {
			if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){++level; if(level >= board.getSize().zValue){level=board.getSize().zValue-1;}}
			if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){--level; if(level<0){level=0;}}
			view.draw(level);
			//System.out.println(level);
		}
		catch (Exception ignored ){ }
	}
	
	@Override
	public void dispose () {
		;
	}
}
