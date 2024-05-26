package minesweeper.plus.core;

import java.util.*;

public class SimpleMinefield implements Minefield {
    private final int width, height, depth, noMines;
    private final Proximity proxy;
    private final boolean[][][] table;

    public SimpleMinefield(Coordinates size, int noMines, Coordinates firstGuess) throws OutOfBoundsException {
        this.width = size.xValue;
        this.height = size.yValue;
        this.depth = size.zValue;
        this.noMines = noMines;
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
    public int getNoFields() {
        return width * height * depth;
    }

    @Override
    public int getNoMines() {
        return noMines;
    }


    //intantiateClick - method to get connected component of the board, bounded by nonzero fields
    @Override
    public Set<Map.Entry<Coordinates, Integer>> instantiateClick(Coordinates guess) throws OutOfBoundsException, MineException {
        Set<Map.Entry<Coordinates, Integer>> result = new HashSet<>();
        int r0 = clickThis(guess);
        Map.Entry<Coordinates, Integer> e0 = new AbstractMap.SimpleEntry<>(guess, r0);
        result.add(e0);
        if(r0 == 0) { //proceed futher only if blankspace
            boolean[][][] visited = new boolean[getSize().xValue][getSize().yValue][getSize().zValue];
            Queue<Map.Entry<Coordinates, Integer>> bfs = new ArrayDeque<>(); //setup bfs
            bfs.add(e0);
            while(!bfs.isEmpty()) {
                Map.Entry<Coordinates, Integer> front = bfs.remove();
                result.add(front); //add to connected component
                visited[front.getKey().xValue][front.getKey().yValue][front.getKey().zValue] = true;
                if (front.getValue() == 0) { //only go further if blank space
                    for (Coordinates c : proxy.neighbourhood(front.getKey())) {
                        {
                            if(!c.bounded(getSize())) {
                                continue;
                            }
                            if (!visited[c.xValue][c.yValue][c.zValue]) {
                                visited[c.xValue][c.yValue][c.zValue] = true;
                                try {
                                    int v = clickThis(c); //get value - guarenteed not to be a bomb because front.getValue() == 0
                                    AbstractMap.SimpleEntry obj = new AbstractMap.SimpleEntry<>(c, v);
                                    if (!bfs.contains(obj)) bfs.add(obj);
                                } catch (MineException | OutOfBoundsException ignored) { } //exception will never occur, language barrier

                            }
                        }
                    }
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