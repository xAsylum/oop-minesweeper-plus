package minesweeper.plus.viewmodels;

import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.SpotValues;

public interface ViewModel {

    void leftClick(Coordinates c);
    void rightClick(Coordinates c);
    boolean dead();
    boolean won();
    SpotValues renderAtCoords(Coordinates c);
    Coordinates getFieldSize();
}
