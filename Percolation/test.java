package Percolation;

public class test {
    public static void main(String[] args) {
        int N = 3;
        int id[][] = new int[N][N];

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                id[r][c] = c; // set all sites to blocked
                System.out.print(id[r][c] + " ");
            }
            System.out.println();
        }
    }
}