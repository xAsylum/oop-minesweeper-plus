package minesweeper.plus.viewmodels;

import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.MineException;
import minesweeper.plus.core.OutOfBoundsException;
import minesweeper.plus.services.Board;
import minesweeper.plus.services.NumberToSpotValue;
import minesweeper.plus.services.SpotValues;

import java.util.ArrayList;
import java.util.List;

public class SimpleViewModel implements ViewModel{
    boolean dead = false;
    int clicks = 0;
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
        if(defaultValues.contains(renderTable[c.xValue][c.yValue][c.zValue])) {
            clicks++;
        }
        //System.out.println(clicks + " " + board.getNoFields() + " " + board.getNoMines() + " " + won());
        renderTable[c.xValue][c.yValue][c.zValue] = v;
    }
    public void handleClick(Coordinates c) { //method invoked when a person clicks certain field
        int bombCount = -1;
        try {
            bombCount = board.clickThis(c);
        } catch (OutOfBoundsException e) {
            return;
        } catch (MineException e) {
            dead = true;
            System.out.println("You lost! Hit a mine at " + c);
        }
        if(bombCount >= 0)
            updateRenderTable(c, NumberToSpotValue.getSpotValue(bombCount));
        else updateRenderTable(c, SpotValues.MINE);
        if(bombCount == 0) {
            try {
                for (java.util.Map.Entry<Coordinates, Integer> x : board.instantiateClick(c)) {
                       updateRenderTable(x.getKey(), NumberToSpotValue.getSpotValue(x.getValue()));
                }
            }catch (Exception ignored) { }
        }
    }

    @Override
    public boolean dead() {
        return dead;
    }

    @Override
    public boolean won() {
        return board.initialized() && (board.getNoFields() == board.getNoMines() + clicks);
    }

    public SpotValues renderAtCoords(Coordinates c) {
        return renderTable[c.xValue][c.yValue][c.zValue];
    } //returns what to render on specific coords

    @Override
    public Coordinates getFieldSize() {
        return board.getSize();
    }
}
