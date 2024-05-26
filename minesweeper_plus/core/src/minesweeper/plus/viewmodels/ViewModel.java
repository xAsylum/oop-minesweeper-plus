package minesweeper.plus.viewmodels;

import minesweeper.plus.core.Coordinates;
import minesweeper.plus.services.SpotValues;

public interface ViewModel {
    SpotValues renderAtCoords(Coordinates c);
    Coordinates getFieldSize();
    void handleClick(Coordinates c);
    boolean dead();
    boolean won();
}
