package Percolation;

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int N;
    private int t;
    private double a[];

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        N = n;
        t = trials;
        a = new double[t];
        int row = 0;
        int col = 0;
        Percolation grid;
        double p;

        if (N <= 0 || t <= 0) {
            throw new IllegalArgumentException();
        }
        // run experiment t times
        for (int i = 0; i < t; i++) {
            grid = new Percolation(N); // Initialize N components.
            while (!grid.percolates()) {
                row = StdRandom.uniform(n - 1);
                col = StdRandom.uniform(n - 1);
                grid.open(row, col);
            }
            p = grid.numberOfOpenSites() / (N * N); // percolation threshold
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
        return (stddev() - (1.96 / Math.sqrt(dtrials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double dtrials = t;
        return (stddev() + (1.96 / Math.sqrt(dtrials)));
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
