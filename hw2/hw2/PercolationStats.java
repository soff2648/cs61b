package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private Percolation percolation;
    private double[] thresholds;
    private boolean ok;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            this.percolation = pf.make(N);
            while (!percolation.percolates()) {
                int x = StdRandom.uniform(0, N);
                int y = StdRandom.uniform(0, N);
                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                }
            }
            thresholds[i] = (double) percolation.numberOfOpenSites() / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }


    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(thresholds.length);
    }


    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(thresholds.length);
    }                                 // high endpoint of 95% confidence interval


}
