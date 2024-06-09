package minesweeper.plus.core;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SimpleMinefield implements Minefield {
    private final int width, height, depth;
    private final Proximity proxy;
    private final boolean[][][] table;

    public SimpleMinefield(Coordinates size, int noMines, Coordinates firstGuess) throws OutOfBoundsException {
        this.width = size.xValue;
        this.height = size.yValue;
        this.depth = size.zValue;
        if(!firstGuess.bounded(getSize()))
            throw new OutOfBoundsException();
        table = new boolean[width][height][depth];
        proxy = new N10Proximity();         //hard-coded for now

        int bound = width*height*depth-1;
        if(noMines > bound || noMines < 0)
            throw new OutOfBoundsException();
        if(noMines < bound/2)  {            //less than half are mines
            for(int i=0; i<width; i++) {
                for(int j=0; j<height; j++) {
                    for(int k=0; k<depth; k++) {
                        table[i][j][k] = false;
                    }
                }
            }
            table[firstGuess.xValue][firstGuess.yValue][firstGuess.zValue] = true;
            int count = 0;
            Random random = new Random();
            while(count < noMines) {
                int number = random.nextInt(bound);
                int w = number%width;
                number = number/width;
                int h = number%height;
                number = number/height;
                int d = number;
                if(!table[w][h][d]) {
                    table[w][h][d] = true;
                    count++;
                }
            }
            table[firstGuess.xValue][firstGuess.yValue][firstGuess.zValue] = false;
        }
        else {          //more than half are mines
            for(int i=0; i<width; i++) {
                for(int j=0; j<height; j++) {
                    for(int k=0; k<depth; k++) {
                        table[i][j][k] = true;
                    }
                }
            }
            table[firstGuess.xValue][firstGuess.yValue][firstGuess.zValue] = false;
            int count = bound - noMines;
            Random random = new Random();
            while(count > 0) {
                int number = random.nextInt(bound);
                int w = number%width;
                number = number/width;
                int h = number%height;
                number = number/height;
                int d = number;
                if(table[w][h][d]) {
                    table[w][h][d] = false;
                    count--;
                }
            }
        }
    }

    @Override
    public Coordinates getSize() {
        return new Coordinates(width, height, depth);
    }

    @Override
    public SpotValues clickThis(Coordinates guess) throws OutOfBoundsException {
        if(!guess.bounded(getSize()))
            throw new OutOfBoundsException();
        if(table[guess.xValue][guess.yValue][guess.zValue])
            return SpotValues.MINE;
        int result = 0;
        Set<Coordinates> neighbourhood = getNeighbourhood(guess);
        for(Coordinates c : neighbourhood) {
            if(table[c.xValue][c.yValue][c.zValue])         //if mine there
                result++;
        }
        return NumberToSpotValue.getSpotValue(result);
    }

    @Override
    public Set<Coordinates> getNeighbourhood(Coordinates guess) throws OutOfBoundsException {
        if(!guess.bounded(getSize()))
            throw new OutOfBoundsException();
        Set<Coordinates> around = proxy.neighbourhood(guess);
        Set<Coordinates> result = new HashSet<>();          //will not contain
        for(Coordinates c : around) {
            if(c.bounded(getSize()))
                result.add(c);
        }
        return result;
    }

}