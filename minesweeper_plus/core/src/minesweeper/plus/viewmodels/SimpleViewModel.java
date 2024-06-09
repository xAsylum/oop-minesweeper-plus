package minesweeper.plus.viewmodels;

import minesweeper.plus.core.Coordinates;
import minesweeper.plus.services.Board;
import minesweeper.plus.core.SpotValues;

import java.util.*;

public class SimpleViewModel implements ViewModel{
    List<SpotValues> defaultValues;
    Board board; //field referenced by viewmodel
    public SimpleViewModel(Board b) {
        defaultValues = new ArrayList<>();
        defaultValues.add(SpotValues.MINE);
        defaultValues.add(SpotValues.FLAG);
        defaultValues.add(SpotValues.HIDDEN);
        board = b;
        int x = b.getSize().xValue, y = b.getSize().yValue, z = b.getSize().zValue;
        renderTable = new SpotValues[x][y][z];
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                for (int k = 0; k < z; k++) {
                    renderTable[i][j][k] = SpotValues.HIDDEN; //-2 means field unseen
                }
    }
    SpotValues[][][] renderTable;
    private void updateRenderTable(Coordinates c, SpotValues v) {
        renderTable[c.xValue][c.yValue][c.zValue] = v;
    }

    @Override
    public void leftClick(Coordinates c) {
        if(!c.bounded(getFieldSize()))
            return;

        Map<Coordinates, SpotValues> uncovered = board.getSpot(c).leftClick();
        for(Map.Entry<Coordinates, SpotValues> e : uncovered.entrySet()) {
            updateRenderTable(e.getKey(), e.getValue());
        }
    }
    @Override
    public void rightClick(Coordinates c) {
        if(!c.bounded(getFieldSize()))
            return;

        board.getSpot(c).rightClick();
        updateRenderTable(c, board.getSpot(c).getValue());
    }

    @Override
    public boolean dead() {
        return board.dead();
    }

    @Override
    public boolean won() {
        return board.won();
    }

    @Override
    public SpotValues renderAtCoords(Coordinates c) {
        return renderTable[c.xValue][c.yValue][c.zValue];
    } //returns what to render on specific coords

    @Override
    public Coordinates getFieldSize() {
        return board.getSize();
    }
}
