/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Add first got null");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst != null)
            oldFirst.previous = first;
        first.previous = null;
        size++;
        if (1 == size)
            last = first;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Add last got null");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;
        if (oldLast != null)
            oldLast.next = last;
        size++;
        if (1 == size)
            first = last;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (null == first || 0 == size)
            throw new NoSuchElementException("Cannot remove first from empty");
        Item formerFirstItem = first.item;
        first = first.next;
        if (first != null)
            first.previous = null;
        size--;
        if (0 == size) {
            first = null;
            last = null;
        }
        return formerFirstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (null == last || 0 == size)
            throw new NoSuchElementException("Cannot remove last from empty");
        Item formerLastItem = last.item;
        last = last.previous;
        if (last != null)
            last.next = null;
        size--;
        if (0 == size) {
            first = null;
            last = null;
        }
        return formerLastItem;
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No next");
            Item it = current.item;
            current = current.next;
            return it;
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove");
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private void print() {
        if (isEmpty()) {
            System.out.println("Empty");
            return;
        }
        Iterator<Item> it = iterator();
        while (it.hasNext()) {
            System.out.print(it.next().toString() + ", ");
        }
        System.out.println("");
    }

    // unit testing ( required )
    public static void main(String[] args) {
        try {
            Deque<String> d = new Deque<String>();
            d.print();
            d.addFirst("The second");
            d.print();
            d.addFirst("The first");
            d.print();
            d.addLast("The third");
            d.print();
            Iterator<String> it = d.iterator();
            System.out.println(it.next());
            System.out.println(it.next());
            System.out.println(it.next());
            System.out.println(it.next());  // exception
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
