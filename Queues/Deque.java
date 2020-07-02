package Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int n; // num of items
    private Node first; // top of the stack / first node
    private Node last; // bottom of the stack / last node

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // Deque (A double-ended queue): is a generalization of a
    // stack and a queue that supports adding and removing items from either the
    // front or the back of the data structure

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // corner case
    private void illegalArg(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    // corner case
    private void elementNotExist() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        illegalArg(item);

        Node oldFirst = first;
        first = new Node();
        if (isEmpty()) {
            last = oldFirst;
        } else {
            oldFirst.prev = first;
        }
        first.next = oldFirst;
        first.item = item;
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        illegalArg(item);

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
            last.prev = oldLast;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        elementNotExist();

        Item item = first.item;
        first = first.next;
        if (first == null) {
            last = first;
        } else {
            first.prev = null;
        }
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        elementNotExist();

        Item item = last.item;
        last = last.prev;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return (current.next != null);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current.next == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        Deque<Integer> queue = new Deque<Integer>();

        StdOut.println("test 1 - add to deque:");
        for (int i = 0; i < n; i++) { // add to front and back of deque
            queue.addFirst(n);
            queue.addLast(n);
        }
        for (int a : queue) {
            StdOut.println(a + " "); // expected output: 4 3 2 1 0 0 1 2 3 4
        }
        StdOut.println("size: " + queue.size()); // expected value: 10
        StdOut.println();

        StdOut.println("test 2 - empty deque then add to it:");
        for (int k = 0; k < n; k++) { // empty deque
            queue.removeFirst();
            queue.removeLast();
        }
        for (int a : queue) {
            StdOut.println(a + " "); // expected output: null
        }
        if (queue.isEmpty()) {
            StdOut.println("deque empty, size: " + queue.size()); // expected value: 0
            queue.addFirst(5);
            for (int a : queue) {
                StdOut.println(a + " "); // expected output: 5
            }
            StdOut.println("deque not empty, size: " + queue.size()); // expected value: 1
        }
    }
}