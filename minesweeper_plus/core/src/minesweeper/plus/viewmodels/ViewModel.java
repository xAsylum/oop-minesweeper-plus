package minesweeper.plus.viewmodels;

import minesweeper.plus.core.Coordinates;

public interface ViewModel {
    int renderAtCoords(Coordinates c);
    Coordinates getFieldSize();
    void handleClick(Coordinates c);
}
