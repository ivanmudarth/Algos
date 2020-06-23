import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // Throw an IllegalArgumentException if any argument to open(), isOpen(), or
    // isFull() is outside its prescribed range. Throw an IllegalArgumentException
    // in the constructor if n ≤ 0.
    private int[][] id;
    private static int[][] status;
    private static int size;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        size = n;
        id = new int[size][size];
        status = new int[size][size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                id[r][c] = c;
                // 0 is blocked, 1 is open
                status[r][c] = 0;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public static void open(int row, int col) {
        status[row][col] = 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (status[row][col] == 1)
            return true;
        else
            return false;
    }

    // is the site (row, col) full?
    public static boolean isFull(int row, int col) {
        return true;
        // A full site is an open site that can be connected to an open site in the top
        // row via a chain of neighboring (left, right, up, down) open sites

    }

    // returns the number of open sites
    public static int numberOfOpenSites() {
        int sites = 0;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (status[r][c] == 1)
                    sites++;
            }
        }
        return sites;
    }

    // does the system percolate?
    public static boolean percolates() {
        for (int c = 0; c < size; c++) {
            if (isFull(size, c))
                return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        // initialize grid object (with N value)
        double p = numberOfOpenSites() / size;
        // loop the open function until percolates function returns true
        // open function will be called with random row and col values
        while (!percolates()) {
            open(StdRandom.uniform(size), StdRandom.uniform(size));
        }
        // print numberOfOpenSites value
        // print the numberOfOpenSites value as a fraction of N
        System.out.println("Number of open sites: " + numberOfOpenSites());
        System.out.println("Site vacancy probability (p): " + p); // value should be around 0.593
    }
}