/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */
/*
Commands to compile and run:
C:\Users\levik\Documents\Learning\AlgoCourseCourseraPrinston\hello>javac -cp .;algs4.jar RandomWord.java
C:\Users\levik\Documents\Learning\AlgoCourseCourseraPrinston\hello>java -cp .;algs4.jar RandomWord < names.txt
Debug in IntelliJ: Debug->Edit configurations...: Redirect input from
* */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String finalres = "";
        double counter = 0;
        while (true) {
            String res = "";
            counter++;
            res = StdIn.readString();
            if (1.0 >= counter)
                finalres = res;
            else {
                if (StdRandom.bernoulli(1 / counter))
                    finalres = res;
            }
            if (StdIn.isEmpty())
                break;
        }
        System.out.println(finalres);
    }
}
