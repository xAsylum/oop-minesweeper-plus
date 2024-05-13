package minesweeper.plus.viewmodels;

import minesweeper.plus.core.Coordinates;
import minesweeper.plus.core.MineException;
import minesweeper.plus.core.OutOfBoundsException;
import minesweeper.plus.services.Board;

public class SimpleViewModel implements ViewModel{
    Board board; //field referenced by viewmodel
    public SimpleViewModel(Board b) {
        board = b;
        int x = b.getSize().xValue, y = b.getSize().yValue, z = b.getSize().zValue;
        renderTable = new Integer[x][y][z];
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                for (int k = 0; k < z; k++) {
                    renderTable[i][j][k] = -2; //-2 means field unseen
                }
    }
Integer[][][] renderTable;
    public void handleClick(Coordinates c) { //method invoked when a person clicks certain field
        int bombCount = -1;
        try {
            bombCount = board.clickThis(c);
        } catch (OutOfBoundsException e) {
            return;
        } catch (MineException e) {System.out.println("You lost! Hit a mine at " + c); }
        renderTable[c.xValue][c.yValue][c.zValue] = bombCount;
        if(bombCount == 0) {
            try {
                for (java.util.Map.Entry<Coordinates, Integer> x : board.instantiateClick(c)) {
                    renderTable[x.getKey().xValue][x.getKey().yValue][x.getKey().zValue] = x.getValue();
                }
            }catch (Exception ignored) { }
        }
    }
    public int renderAtCoords(Coordinates c) {
        return renderTable[c.xValue][c.yValue][c.zValue];
    } //returns what to render on specific coords

    @Override
    public Coordinates getFieldSize() {
        return board.getSize();
    }
}
