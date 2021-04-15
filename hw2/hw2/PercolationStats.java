package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform T independent experiments on an N-by-N grid
    private Percolation percolation;
    private double[] thresholds;
    private boolean ok;

    public PercolationStats(int N, int T, PercolationFactory pf) {
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



    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }
    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    // sample standard deviation of percolation threshold
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(thresholds.length);
    }
    // low endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(thresholds.length);
    }                                 // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(20, 2000, new PercolationFactory());
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLow());
        System.out.println(ps.confidenceHigh());
    }

}
