package Queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = StdIn.readInt();
        String s[] = StdIn.readAllStrings();
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int count = 0;

        for (int i = 0; i < s.length; i++) { // read each item in sequence of strings
            queue.enqueue(s[i]);
        }
        for (String a : queue) { // print k items, uniformly, at random
            count++;
            if (count == k)
                break;
            else
                StdOut.println(a);
        }
    }
}
