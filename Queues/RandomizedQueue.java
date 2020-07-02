package Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;

    // A randomized queue is similar to a stack or queue, except that the item
    // removed is chosen uniformly at random among items in the data structure.

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        a = (Item[]) new Object[2]; // generic casting
    }

    // resize the underlying array holding the elements
    private void resize(int cap) {
        Item[] copy = (Item[]) new Object[cap];
        for (int i = 0; i < n; i++) {
            copy[i] = a[i];
        }
        a = copy;
    }

    // corner case
    private void elementNotExist() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) // corner case
            throw new IllegalArgumentException();

        if (n == a.length) // resize array if necessary
            resize(a.length * 2);
        // item is added to the element on pointer, pointer is incremented
        a[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        int rand = StdRandom.uniform(n);
        Item i = a[rand];
        // item from tail replaces item being dequeued
        a[rand] = a[n - 1];
        a[--n] = null;

        if (n > 0 && n == a.length / 4) // resize array if necessary
            resize(a.length / 2);
        return i;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return a[StdRandom.uniform(n)];
    }
    // Each iterator must return the items in uniformly random order. The order of
    // two or more iterators to the same randomized queue must be mutually
    // independent; each iterator must maintain its own random order.

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int i = n;

        public boolean hasNext() {
            return (i > 0);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) // corner case
                throw new NoSuchElementException();
            return a[--i];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }
}