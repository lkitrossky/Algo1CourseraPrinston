/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Must be one argument - number, you gave " + args.length);
            System.out.println("45 < distinct.txt ");
            return;
        }
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rs = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rs.enqueue(s);
        }
        if (k > rs.size()) {
            System.out.println("k cannot be > n: " + k + " > " + rs.size());
            return;
        }
        for (int i = 0; i < k; i++)
            System.out.println(rs.dequeue());
    }
}
