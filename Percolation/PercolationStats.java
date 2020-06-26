package Percolation;

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private static final double CONST = 1.96;
    private final int t;
    private final double[] a;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        t = trials;
        a = new double[t];
        int row = 0;
        int col = 0;
        Percolation grid;
        double p;

        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException();
        }
        // run experiment t times
        for (int i = 0; i < t; i++) {
            grid = new Percolation(n); // Initialize N components.
            while (!grid.percolates()) {
                row = StdRandom.uniform(n);
                col = StdRandom.uniform(n);
                grid.open(row, col);
            }
            p = grid.numberOfOpenSites() / (double) (n * n); // percolation threshold
            a[i] = p;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(a);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (t == 1)
            return Double.NaN;
        return StdStats.stddev(a);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double dtrials = t;
        return (mean() - (CONST * stddev() / Math.sqrt(dtrials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double dtrials = t;
        return (mean() + (CONST * stddev() / Math.sqrt(dtrials)));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats p = new PercolationStats(n, trials);

        System.out.println("mean = " + p.mean());
        System.out.println("stdev = " + p.stddev());
        System.out.println("95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
    }

}
