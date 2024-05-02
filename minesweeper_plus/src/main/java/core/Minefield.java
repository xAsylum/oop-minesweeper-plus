package core;

//creates and stores the minefield and answers questions about it
public interface Minefield {

    public Minefield newField(int width, int height, int depth, int noMines);

    public int clickThis(int x, int y, int z) throws MineException, OutOfBoundsException;
                //returns #mines in proximity of this field
                //OR MineException if mine OR OutOfBounds if out of bounds

}