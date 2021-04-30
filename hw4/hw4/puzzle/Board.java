package hw4.puzzle;

import java.util.HashSet;
import java.util.Set;

public class Board implements WorldState {

    private int dimension;
    private int[][] state;
    private int holeIndexI;
    private int holeIndexJ;
    private int[][] goal;
    private int hammingScore;


    public Board(int[][] tiles) {
        dimension = tiles.length;
        state = new int[dimension][dimension];
        goal = new int[dimension][dimension];


        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                state[i][j] = tiles[i][j];
                goal[i][j] = 1 + i * dimension + j;
                if ((i == dimension - 1) && (j == dimension - 1)) {
                    goal[i][j] = 0;
                }
                if (state[i][j] != goal[i][j]) {
                    hammingScore += 1;
                }
                if (state[i][j] == 0) {
                    holeIndexI = i;
                    holeIndexJ = j;
                }
            }
        }
    }



    public int tileAt(int i, int j) {
        if (indexValid(i) && indexValid(j)) {
            return state[i][j];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean indexValid(int i) {
        return i>= 0 && i < dimension;
    }

    public int size() {
        return dimension;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Set<WorldState> neighbs = new HashSet<>();

        if (indexValid(holeIndexI - 1) && indexValid(holeIndexJ)) {
            neighbs.add(new Board(copyAndSwap(state, holeIndexI - 1, holeIndexJ)));
        }

        if (indexValid(holeIndexI + 1) && indexValid(holeIndexJ)) {
            neighbs.add(new Board(copyAndSwap(state, holeIndexI + 1, holeIndexJ)));
        }

        if (indexValid(holeIndexI) && indexValid(holeIndexJ - 1)) {
            neighbs.add(new Board(copyAndSwap(state, holeIndexI, holeIndexJ - 1)));
        }

        if (indexValid(holeIndexI) && indexValid(holeIndexJ + 1)) {
            neighbs.add(new Board(copyAndSwap(state, holeIndexI, holeIndexJ + 1)));
        }

        return neighbs;
    }

    private int[][] copyAndSwap(int[][] tiles, int newI, int newJ) {
        int[][] newTiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                newTiles[i][j] = state[i][j];
            }
        }
        int temp = newTiles[newI][newJ];
        newTiles[newI][newJ] = 0;
        newTiles[holeIndexI][holeIndexJ] = temp;

        return newTiles;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhatten();
    }

    private int hamming() {

        return hammingScore;
    }

    private int manhatten() {
        int score = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (state[i][j] == 0) {
                    score += Math.abs(dimension - 1 - i) + Math.abs((dimension - 1 - j));
                } else {
                    int expectedI = (state[i][j] - 1) / dimension;
                    int expectedJ = (state[i][j] - 1) % dimension;
                    score += Math.abs(expectedI - i) + Math.abs((expectedJ - j));
                }
            }
        }
        return score;
    }
    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }

        Board other = (Board) y;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (state[i][j] != other.state[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }




    /** Returns the string representation of the board. 
      * Uncomment this method. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
