/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        first = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot enqueue null");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        if (oldFirst != null) {
            oldFirst.previous = first;
        }
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (0 == size)
            throw new NoSuchElementException("Cannot dequeue from empty");
        int pos = StdRandom.uniform(0, size);
        Node removingNode = first;
        for (int i = 0; i < pos; i++)
            removingNode = removingNode.next;
        Item formerMiddleItem = removingNode.item;
        Node prevNode = removingNode.previous;
        Node nextNode = removingNode.next;
        if (prevNode != null)
            prevNode.next = nextNode;
        else
            first = nextNode;
        if (nextNode != null)
            nextNode.previous = prevNode;
        size--;
        if (0 == size()) {
            first = null;
        }
        return formerMiddleItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (0 == size)
            throw new NoSuchElementException("Cannot sample from empty");
        int pos = StdRandom.uniform(0, size);
        Node toSample = first;
        for (int i = 0; i < pos; i++)
            toSample = toSample.next;
        return toSample.item;
    }

    private class ListIterator implements Iterator<Item> {

        public boolean hasNext() {
            return !isEmpty();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No next");
            int pos = StdRandom.uniform(0, size);
            Node toSample = first;
            for (int i = 0; i < pos; i++)
                toSample = toSample.next;
            Item toReturn = toSample.item;


            Node prevNode = toSample.previous;
            Node nextNode = toSample.next;
            if (prevNode != null)
                prevNode.next = nextNode;
            else
                first = nextNode;
            if (nextNode != null)
                nextNode.previous = prevNode;
            size--;
            return toReturn;
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove");
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private void print() {
        if (isEmpty()) {
            System.out.println("Empty");
            return;
        }
        Node toPrint = first;
        while (toPrint != null) {
            System.out.print(toPrint.item.toString() + ", ");
            toPrint = toPrint.next;
        }
        System.out.println("");
    }

    // unit testing (required)
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 100; i++) {
                RandomizedQueue<String> queue = new RandomizedQueue<>();
                queue.enqueue("A");
                queue.enqueue("B");
                Iterator<String> it = queue.iterator();
                System.out.println(it.next() + it.next());
            }


            RandomizedQueue<Integer> queue = new RandomizedQueue<>();
            queue.enqueue(472);
            queue.sample();      // ==> 472
            queue.enqueue(306);
            queue.sample();      // ==> 472
            queue.sample();      // ==> 306
            queue.size();       // ==> 2
            Iterator<Integer> it3 = queue.iterator();    // ==> [306, 472]
            while (it3.hasNext())
                System.out.println("it3: " + it3.next());
            queue.enqueue(184);
            Iterator<Integer> it4 = queue.iterator();    // ==> [184]
            while (it4.hasNext())
                System.out.println("it4: " + it4.next());
            /*
            RandomizedQueue<Integer> queue = new RandomizedQueue<>();
            queue.enqueue(4);
            Iterator<Integer> it3 = queue.iterator();    // ==> [4]
            while (it3.hasNext())
                System.out.println("it3: " + it3.next());
            queue.enqueue(0);
            queue.enqueue(4);
            it3 = queue.iterator();    // ==> [4, 0]
            while (it3.hasNext())
                System.out.println("it3: " + it3.next());
            */
            RandomizedQueue<String> d = new RandomizedQueue<String>();
            d.enqueue("The first");
            d.enqueue("The second");
            d.enqueue("The third");
            d.enqueue("The fourth");
            d.enqueue("The fifth");
            d.print();
            for (int i = 0; i < 10; i++)
                System.out.println("Size: " + d.size() + " sample: " + d.sample());
            System.out.println("Size: " + d.size() + " removed: " + d.dequeue());
            d.print();
            Iterator<String> it = d.iterator();
            Iterator<String> it2 = d.iterator();
            System.out.println("it2: " + it2.next());
            System.out.println("it: " + it.next());
            System.out.println("it: " + it.next());
            System.out.println("it: " + it.next());
            d.print();
            System.out.println("it2: " + it2.next());
            System.out.println("it: " + it.next());
            System.out.println("it: " + it.next());  // exception
        }
        catch (IllegalArgumentException | NoSuchElementException | UnsupportedOperationException e) {
            System.out.println(e.toString());
        }
        finally {
            System.out.println("Finally block");
        }
        System.out.println("Finished");
    }
}
