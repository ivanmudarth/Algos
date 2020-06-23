package Percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // Throw an IllegalArgumentException if any argument to open(), isOpen(), or
    // isFull() is outside its prescribed range. Throw an IllegalArgumentException
    // in the constructor if n ≤ 0.
    private int[] id;
    private boolean[][] status;
    private int size;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        size = n;
        id = new int[size];
        status = new boolean[size][size];
        for (int r = 0; r < size; r++) {
            id[r] = r; // set all site id's to value of index
            for (int c = 0; c < size; c++) {
                status[r][c] = false; // set all sites to blocked
            }
        }
    }

    // converts row, col values to an int representing a site on UF data structure
    public int encode(int i, int j) {
        return (size * i) + j;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        status[row][col] = true;
        // call union to all neighbouring sites
        WeightedQuickUnionUF qu = new WeightedQuickUnionUF(size);
        if (status[row - 1][col] == true) // check site above
            qu.union(encode(row, col), encode((row - 1), col));
        if (status[row + 1][col] == true) // check site below
            qu.union(encode(row, col), encode((row + 1), col));
        if (status[row][col + 1] == true) // check site to the right
            qu.union(encode(row, col), encode(row, (col + 1)));
        if (status[row][col - 1] == true) // check site to the left
            qu.union(encode(row, col), encode(row, col - 1));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return status[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return true;
        // A full site is an open site that can be connected to an open site in the top
        // row via a chain of neighboring (left, right, up, down) open sites

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int sites = 0;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (status[r][c])
                    sites++;
            }
        }
        return sites;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int c = 0; c < size; c++) { // FIX: this is brute-force, add a virtual site
            if (isFull(size, c))
                return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        int N = StdIn.readInt(); // Read number of sites.
        Percolation grid = new Percolation(N); // Initialize N components.
        double p = grid.numberOfOpenSites() / (grid.size ^ 2);

        while (!grid.percolates()) {
            grid.open(StdRandom.uniform(grid.size), StdRandom.uniform(grid.size));
        }
        // test output
        System.out.println("Number of open sites: " + grid.numberOfOpenSites());
        System.out.println("Site vacancy probability (p): " + p); // value should be around 0.593
    }
}