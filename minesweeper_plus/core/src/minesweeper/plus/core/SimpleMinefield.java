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


    //intantiateClick - method to get connected component of the board, bounded by nonzero fields
    public Set<Map.Entry<Coordinates, Integer>> instantiateClick(Coordinates guess) throws OutOfBoundsException, MineException {
        Set<Map.Entry<Coordinates, Integer>> result = new HashSet<>();
        int r0 = clickThis(guess);
        Map.Entry<Coordinates, Integer> e0 = new AbstractMap.SimpleEntry<>(guess, r0);
        result.add(e0);
        if(r0 == 0) { //proceed futher only if blankspace
            Set<Coordinates> visited = new HashSet<>();
            Queue<Map.Entry<Coordinates, Integer>> bfs = new ArrayDeque<>(); //setup bfs
            bfs.add(e0);
            while(!bfs.isEmpty()) {
                Map.Entry<Coordinates, Integer> front = bfs.remove();
                visited.add(front.getKey()); //mark as visited
                result.add(front); //add to connected component
                if (front.getValue() == 0) //only go further if blank space
                    for (Coordinates c : proxy.neighbourhood(front.getKey())) {
                        if (!visited.contains(c))
                            try {
                                int v = clickThis(c); //get value - guarenteed not to be a bomb because front.getValue() == 0
                                bfs.add(new AbstractMap.SimpleEntry<>(c, v));
                            } catch (MineException ignored) { } //exception will never occur, language barrier
                    }
            }
        }
        return  result;
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