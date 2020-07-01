package Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N; // num of items
    private Node first; // top of the stack / first node
    private Node last; // bottom of the stack / last node

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private void illegalArg(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    private void elementNotExist() {
        if (isEmpty())
            throw new NoSuchElementException();
    }
    // Deque (A double-ended queue): is a generalization of a
    // stack and a queue that supports adding and removing items from either the
    // front or the back of the data structure

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (N == 0);
    }

    // return the number of items on the deque
    public int size() {
        return N;
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
        N++;
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
        N++;
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
        N--;
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
        N--;
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
    }
}