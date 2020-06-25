package Percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // avoid back wash problem
    // simplify initialization of id and status arrays
    // handle corner cases
    // run tests
    private int[] id;
    private boolean[][] status;
    private int N;
    private int counter = 0;
    private static WeightedQuickUnionUF qu;
    private int source;
    private int sink;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        N = n;
        id = new int[N ^ 2 + 2];
        status = new boolean[N][N];
        qu = new WeightedQuickUnionUF(N * N);

        for (int i = 0; i < (N * N + 2); i++) {
            id[i] = i; // set all site id's to value of index
        }
        for (int r = 0; r < N; r++) {
            // initialize source and sink (virtual) sites
            qu.union(encode(0, r), encode(0, r)); // source
            qu.union(encode(N - 1, r), encode(N - 1, r)); // sink

            for (int c = 0; c < N; c++) {
                status[r][c] = false; // set all sites to blocked
            }
        }
    }

    // converts row, col values to an int representing a site on UF data structure
    private int encode(int i, int j) {
        return (N * i + j + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            status[row][col] = true;
            counter++;
            // call union to all neighbouring sites
            if (status[row - 1][col] == true) // check site above
                qu.union(encode(row, col), encode((row - 1), col));
            if (status[row + 1][col] == true) // check site below
                qu.union(encode(row, col), encode((row + 1), col));
            if (status[row][col + 1] == true) // check site to the right
                qu.union(encode(row, col), encode(row, (col + 1)));
            if (status[row][col - 1] == true) // check site to the left
                qu.union(encode(row, col), encode(row, col - 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new IndexOutOfBoundsException();
        }
        return status[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new IndexOutOfBoundsException();
        }
        return qu.find(encode(row, col)) == qu.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return counter;
    }

    // does the system percolate?
    public boolean percolates() {
        return isFull(N - 1, N); // checks if sink is a full site
    }

    // test client
    public static void main(String[] args) {
        int N = StdIn.readInt(); // Read number of sites.
        Percolation grid = new Percolation(N); // Initialize N components.
        double p = grid.numberOfOpenSites() / (grid.N ^ 2);
        int row = 0;
        int col = 0;

        while (!grid.percolates()) {
            row = StdRandom.uniform(grid.N - 1);
            col = StdRandom.uniform(grid.N - 1);
            grid.open(row, col);
            System.out.println("Site opened at " + row + ", " + col);
        }
        // test output
        System.out.println("Number of open sites: " + grid.numberOfOpenSites());
        if (grid.percolates()) {
            System.out.println("system percolates");
        } else {
            System.out.println("system does not percolate");
        }
        System.out.println("Site vacancy probability (p): " + p); // value should be around 0.593
    }
}