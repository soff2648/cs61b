package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF djsPercolation;
    private WeightedQuickUnionUF djsFull;
    private int openSites;
    private boolean[] isOpened;
    private int N;

    public Percolation(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException();
        }
        N = number;

        djsPercolation = new WeightedQuickUnionUF(N * N + 2); // 101
        djsFull = new WeightedQuickUnionUF(N * N + 1); // 100
        isOpened = new boolean[N * N + 1];

    }

    public void open(int i, int j) {
        if (i >= N || j >= N || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (!isOpen(i, j)) {
            openSites += 1;
        }
        int index = xyTo1D(i, j);
        if (i == 0) {
            djsPercolation.union(0, index);
            djsFull.union(0, index);
        }

        if (i == N - 1) {
            djsPercolation.union(N * N + 1, index);
        }

        isOpened[index] = true;
        unionNeighbours(i, j);

    }

    public boolean isOpen(int i, int j) {
        if (i >= N || j >= N || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException();
        }

        return isOpened[xyTo1D(i, j)];
    }

    public boolean isFull(int i, int j) {
        if (i >= N || j >= N || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException();
        }

        return djsFull.connected(0, xyTo1D(i, j));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {

        return checkBottom();
    }

    private int xyTo1D(int i, int j) {
        return i * N + j + 1;
    }


    private void unionNeighbours(int i, int j) {
        if (i > 0) {
            if (isOpen(i - 1, j)) {
                //System.out.println(i + " " + j + " connected with " + (i - 1) + " " + j);
                djsPercolation.union(xyTo1D(i, j), xyTo1D(i - 1, j));
                djsFull.union(xyTo1D(i, j), xyTo1D(i - 1, j));
            }
        }
        if (i < N - 1) {
            if (isOpen(i + 1, j)) {
                //System.out.println(i + " " + j + " connected with " + (i + 1) + " " + j);
                djsPercolation.union(xyTo1D(i, j), xyTo1D(i + 1, j));
                djsFull.union(xyTo1D(i, j), xyTo1D(i + 1, j));
            }
        }

        if (j > 0) {
            if (isOpen(i, j - 1)) {
                //System.out.println(i + " " + j + " connected with " + i + " " + (j - 1));
                djsPercolation.union(xyTo1D(i, j), xyTo1D(i, j - 1));
                djsFull.union(xyTo1D(i, j), xyTo1D(i, j - 1));
            }
        }
        if (j < N - 1) {
            if (isOpen(i, j + 1)) {
                //System.out.println(i + " " + j + " connected with " + i + " " + (j + 1));
                djsPercolation.union(xyTo1D(i, j), xyTo1D(i, j + 1));
                djsFull.union(xyTo1D(i, j), xyTo1D(i, j + 1));
            }
        }
    }

    private boolean checkBottom() {
        return djsPercolation.connected(0, N * N + 1);
    }

    public static void main(String[] args) {

    }






}
