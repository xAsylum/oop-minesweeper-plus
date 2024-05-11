package minesweeper.plus.views;

import minesweeper.plus.core.Minefield;
import minesweeper.plus.services.Board;

public interface View {

    void draw(Minefield field, int level) throws  Exception;
}
