/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final Point[] points;
    private final ArrayList<LineSegment> lineSegments;
    private boolean done;

    public BruteCollinearPoints(Point[] points) {
        done = false;
        if (points == null || points.length == 0)
            throw new IllegalArgumentException("No points");
        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException("Null instead of a point");
        }
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            this.points[i] = points[i];
        }
        Arrays.sort(this.points, 0, points.length);
        for (int i = 1; i < this.points.length; i++) {
            if (this.points[i - 1].compareTo(this.points[i]) == 0) {
                throw new IllegalArgumentException("Duplicate points");
            }
        }
        lineSegments = new ArrayList<LineSegment>();
        numberOfSegments();
    }   // finds all line segments containing 4 points

    public int numberOfSegments() {
        if (done)
            return lineSegments.size();
        for (int i1 = 0; i1 < points.length; i1++) {
            for (int i2 = i1 + 1; i2 < points.length; i2++) {
                double slope12 = points[i1].slopeTo(points[i2]);
                for (int i3 = i2 + 1; i3 < points.length; i3++) {
                    double slope13 = points[i1].slopeTo(points[i3]);
                    for (int i4 = i3 + 1; i4 < points.length; i4++) {
                        double slope14 = points[i1].slopeTo(points[i4]);
                        if (slope12 == slope13 && slope12 == slope14) {
                            Point[] alp = new Point[4];
                            alp[0] = points[i1];
                            alp[1] = points[i2];
                            alp[2] = points[i3];
                            alp[3] = points[i4];
                            Arrays.sort(alp, 0, 4);
                            LineSegment ls = new LineSegment(alp[0], alp[3]);
                            lineSegments.add(ls);
                        }
                    }
                }
            }
        }
        done = true;
        return lineSegments.size();
    }       // the number of line segments

    public LineSegment[] segments() {
        if (!done)
            numberOfSegments();
        LineSegment[] res = new LineSegment[lineSegments.toArray().length];

        Iterator<LineSegment> iterator = lineSegments.iterator();
        for (int i = 0; i < res.length; i++) {
            res[i] = iterator.next();
        }
        return res;
    }               // the line segments

    public static void main(String[] args) {
        System.out.println("Start");
        try {
            // read the n points from a file
            In in = new In(args[0]);
            int n = in.readInt();
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                int x = in.readInt();
                int y = in.readInt();
                points[i] = new Point(x, y);
            }

            // draw the points
            StdDraw.enableDoubleBuffering();
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            int i = 0;
            double rad = StdDraw.getPenRadius();
            Color color = StdDraw.getPenColor();
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point p : points) {
                p.draw();
                StdOut.println(i + ":" + p);
                i++;
            }
            StdDraw.show();

            // print and draw the line segments
            BruteCollinearPoints collinear = new BruteCollinearPoints(points);
            StdDraw.setPenRadius(rad);
            StdDraw.setPenColor(color);
            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
            StdDraw.show();
        }
        catch (IllegalArgumentException e) {
            System.out.println("Exception: " + e.toString());
        }
    }
}
