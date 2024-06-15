package minesweeper.plus.core;

public class Coordinates {
    public final int xValue, yValue, zValue;
    public Coordinates(int x, int y, int z) {
        xValue = x;
        yValue = y;
        zValue = z;
    }

    public boolean bounded(Coordinates outerBound) {
        return (xValue >= 0) && (xValue < outerBound.xValue) &&
                (yValue >= 0) && (yValue < outerBound.yValue) &&
                (zValue >= 0) && (zValue < outerBound.zValue);          //returns true if everything is fine
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coordinates) {
            Coordinates s = (Coordinates)obj;
            return (s.xValue == xValue && s.yValue == yValue && s.zValue == zValue);
        }
        return false;
    }
    @Override
    public String toString() {
        return xValue + "," + yValue + "," + zValue;
    }
}
