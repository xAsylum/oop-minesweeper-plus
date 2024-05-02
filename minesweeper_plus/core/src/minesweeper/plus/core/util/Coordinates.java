package minesweeper.plus.core.util;

import minesweeper.plus.core.OutOfBoundsException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Coordinates {
    int xValue, yValue, zValue;
    Coordinates(int x, int y, int z) throws OutOfBoundsException {
        if(x < 0 || y < 0 || z < 0) throw new OutOfBoundsException();
        xValue = x;
        yValue = y;
        zValue = z;

    }

    ArrayList<Integer> asList() {
        ArrayList<Integer> s = new ArrayList<Integer>();
        s.add(xValue);
        s.add(yValue);
        s.add(zValue);
        return s;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coordinates) {
            Coordinates s = (Coordinates)obj;
            return (s.xValue == xValue && s.yValue == yValue && s.zValue == zValue);
        }
        return false;
    }
}
