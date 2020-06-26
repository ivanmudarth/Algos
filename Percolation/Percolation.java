package Percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // avoid back wash problem
    // run tests
    private final WeightedQuickUnionUF qu;
    private boolean[][] status;
    private final int n;
    private int counter = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int a) {
        n = a;
        status = new boolean[n][n];
        qu = new WeightedQuickUnionUF(n * n + 2);

        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        for (int r = 0; r < n; r++) {
            // initialize source and sink (virtual) sites
            qu.union(encode(0, r), 0); // source
            qu.union(encode(n - 1, r), (n * n + 1)); // sink
        }
    }

    // converts row, col values to an int representing a site on UF data structure
    private int encode(int i, int j) {
        return (n * i + j + 1);
    }

    private void indexValidate(int row, int col) {
        if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        indexValidate(row, col);

        if (!isOpen(row, col)) {
            status[row][col] = true;
            counter++;
            // call union to all neighbouring sites
            if (row - 1 >= 0 && status[row - 1][col]) // check site above
                qu.union(encode(row, col), encode((row - 1), col));
            if (row + 1 < this.n && status[row + 1][col]) // check site below
                qu.union(encode(row, col), encode((row + 1), col));
            if (col - 1 >= 0 && status[row][col - 1]) // check site to the left
                qu.union(encode(row, col), encode(row, col - 1));
            if (col + 1 < this.n && status[row][col + 1]) // check site to the right
                qu.union(encode(row, col), encode(row, (col + 1)));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        indexValidate(row, col);

        if (status[row][col]) {
            return true;
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        indexValidate(row, col);

        if (status[row][col] && qu.find(encode(row, col)) == qu.find(0)) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return counter;
    }

    // does the system percolate?
    public boolean percolates() {
        return isFull(n - 1, n); // checks if sink is a full site
    }

    // test client
    public static void main(String[] args) {
        final int n = Integer.parseInt(args[0]); // Read number of sites.
        Percolation grid = new Percolation(n); // Initialize N components.
        double p = grid.numberOfOpenSites() / (double) (n * n);
        int row = 0;
        int col = 0;

        while (!grid.percolates()) {
            row = StdRandom.uniform(n);
            col = StdRandom.uniform(n);
            grid.open(row, col);
            System.out.println("Site opened at " + row + ", " + col);
        }
        // test output
        System.out.println("number of open sites: " + grid.numberOfOpenSites());
        if (grid.percolates()) {
            System.out.println("system percolates");
        } else {
            System.out.println("system does not percolate");
        }
        System.out.println("site vacancy probability (p): " + p); // value should be around 0.593
    }
}