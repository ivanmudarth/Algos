import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // Throw an IllegalArgumentException if any argument to open(), isOpen(), or
    // isFull() is outside its prescribed range. Throw an IllegalArgumentException
    // in the constructor if n ≤ 0.
    private int[][] id;
    private int[][] status;
    private int count;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        count = n;
        id = new int[count][count];
        status = new int[count][count];
        for (int r = 0; r < count; r++) {
            for (int c = 0; c < count; c++) {
                id[r][c] = c;
                // 0 is blocked, 1 is open
                status[r][c] = 0;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
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
    public boolean isFull(int row, int col) {
        return true;
        // A full site is an open site that can be connected to an open site in the top
        // row via a chain of neighboring (left, right, up, down) open sites
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int sites = 0;
        for (int r = 0; r < count; r++) {
            for (int c = 0; c < count; c++) {
                if (status[r][c] == 1)
                    sites++;
            }
        }
        return sites;
    }

    // does the system percolate?
    public boolean percolates() {
        return true;

    }

    // test client (optional)
    public static void main(String[] args) {
        // initialize grid object (with N value)

        // loop the open function until percolates function returns true
        // open function will be called with random row and col values

        // print the numberOfOpenSites value
        // print the numberOfOpenSites value as a fraction of N
    }
}