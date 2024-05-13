package minesweeper.plus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.OutOfBoundsException;
import minesweeper.plus.services.SimpleBoard;
import minesweeper.plus.viewmodels.SimpleViewModel;
import minesweeper.plus.views.SimpleView;
import minesweeper.plus.views.View;

import static java.lang.System.exit;

public class MyGdxGame extends ApplicationAdapter {


	View view;
	SimpleBoard board;
	int level=0;
	
	@Override
	public void create () {
		try {

			Coordinates boardSize = new Coordinates(10, 10, 2);			//change board size here!

			int numberOfMines = 20;			//change number of mines here!

			board = new SimpleBoard(boardSize, numberOfMines);
			view = new SimpleView(new SimpleViewModel(board));
		}
		catch (OutOfBoundsException e){
			System.out.println("Wrong starting conditions");
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

	}
}
