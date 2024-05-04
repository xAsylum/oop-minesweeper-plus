package minesweeper.plus.core;

import java.util.*;

public class SimpleMinefield implements Minefield {
    private final int width, height, depth, noMines;
    private final Proximity proxy;
    private final boolean[][][] table;

    public SimpleMinefield(int width, int height, int depth, int noMines, Coordinates firstGuess) throws OutOfBoundsException {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.noMines = noMines;
        if(!firstGuess.bounded(getSize()))
            throw new OutOfBoundsException();
        table = new boolean[width][height][depth];
        proxy = new N10Proximity();         //hard-coded for now

        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                for(int k=0; k<depth; k++) {
                    table[i][j][k] = false;
                }
            }
        }

        table[firstGuess.xValue][firstGuess.yValue][firstGuess.yValue] = true;          //set mine here, will be deleted later
        int count = 0;
        Random random = new Random();
        int bound = width*height*depth - 1;
        if(noMines > bound)
            throw new OutOfBoundsException();           //too many mines
        while (count < noMines) {
            int number = random.nextInt(bound);
            int w = number%width;
            number = (number-w)/width;
            int h = number%height;
            number = (number-h)/height;
            int d = number;
            if(!table[w][h][d]){
                table[w][h][d] = true;          //no mine -> add mine
                count++;
            }
        }           //placing the mines
        table[firstGuess.xValue][firstGuess.yValue][firstGuess.yValue] = false;
    }

    @Override
    public Coordinates getSize() {
        return new Coordinates(width, height, depth);
    }

    @Override
    public int clickThis(Coordinates guess) throws MineException, OutOfBoundsException {
        if(!guess.bounded(getSize()))
            throw new OutOfBoundsException();
        if(table[guess.xValue][guess.yValue][guess.zValue])
            throw new MineException();
        int result = 0;
        Set<Coordinates> neighbourhood = proxy.neighbourhood(guess);
        for(Coordinates c : neighbourhood) {
            if(c.bounded(getSize())) {          //if within table
                if(table[c.xValue][c.yValue][c.zValue])         //if mine there
                    result++;
            }
        }
        return result;
    }

    @Override
    public Set<Map.Entry<Coordinates, Integer>> getNeighbourhood(Coordinates guess) throws NotEmptyException, OutOfBoundsException {
        try {
            if (clickThis(guess) != 0)
                throw new NotEmptyException();
        } catch (MineException e) {
            throw new NotEmptyException();          //if mine then it's also not empty
        }
        Set<Coordinates> neighbourhood = proxy.neighbourhood(guess);
        Set<Map.Entry<Coordinates, Integer>> result = new HashSet<>();
        for(Coordinates c : neighbourhood) {
            try {
                int value = clickThis(c);
                result.add(new AbstractMap.SimpleEntry<>(c, value));
            } catch (MineException e) {/* it's just impossible here, has been checked earlier */}
            catch (OutOfBoundsException e) {/* then we just don't add an entry for these Coordinates */}
        }
        return result;
    }
}