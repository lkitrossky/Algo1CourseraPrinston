/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] results;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolator = new Percolation(n);
            while (!percolator.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                while (percolator.isOpen(row, col)) {
                    row = StdRandom.uniform(n) + 1;
                    col = StdRandom.uniform(n) + 1;
                }
                percolator.open(row, col);
            }
            results[i] = 1.0 * percolator.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double s = stddev();
        double m = mean();
        double res = m - CONFIDENCE_95 * Math.sqrt(s) / Math.sqrt(results.length);
        return res;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double s = stddev();
        double m = mean();
        double res = m + CONFIDENCE_95 * Math.sqrt(s) / Math.sqrt(results.length);
        return res;
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2)
            return;
        int gridSize = Integer.parseInt(args[0]);
        int howManyExperiments = Integer.parseInt(args[1]);
        PercolationStats perc = new PercolationStats(gridSize, howManyExperiments);
        System.out.print("mean                    = " + perc.mean() + "\n");
        System.out.print("stddev                  = " + perc.stddev() + "\n");
        System.out.print(
                "95% confidence interval = [" + perc.confidenceLo() + ", "
                        + perc.confidenceHi() + "]\n");
    }
}
