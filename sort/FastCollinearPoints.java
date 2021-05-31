/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class FastCollinearPoints {
    private final Point[] points;
    private final ArrayList<LineSegment> lineSegments;
    private boolean done;

    public FastCollinearPoints(Point[] points) {
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

    private static boolean isSorted(ArrayList<Point> al) {
        if (al.isEmpty())
            return false;
        Iterator<Point> iter = al.iterator();
        Point previous = iter.next();
        while (iter.hasNext()) {
            Point current = iter.next();
            if (previous.compareTo(current) > 0)
                return false;
            previous = current;
        }
        return true;
    }

    public int numberOfSegments() {
        if (done)
            return lineSegments.size();
        int pointsAmount = points.length;
        // lineSegments is the recipient of results
        for (int i1 = 0; i1 < pointsAmount; i1++) { // all points
            Point[] otherPoints = new Point[pointsAmount - 1];
            int index = 0;
            for (int i2 = 0; i2 < pointsAmount; i2++) {
                if (i1 == i2)
                    continue;
                otherPoints[index++] = points[i2];
            } // lineSegments - all except i1
            Arrays.sort(otherPoints, points[i1].slopeOrder()); // sorted by slope
            for (int i2 = 0; i2 < otherPoints.length - 1;
                 i2++) { // we seek i1, i2 amd two more, so -1 stops
                ArrayList<Point> ap
                        = new ArrayList<Point>(); // all points that have slope to i1->i2 in addition to i1, i2
                ap.add(points[i1]);
                ap.add(otherPoints[i2]);
                int i3 = i2 + 1;
                double slope = points[i1].slopeTo(otherPoints[i2]);
                while (i3 < otherPoints.length && slope == points[i1].slopeTo(otherPoints[i3])) {
                    ap.add(otherPoints[i3]);
                    i3++;
                }
                // we collected all points on line i1-i2
                // now ensure that i1 is the beginning of the segment, start+slope ensure uniqueness
                if (ap.size() >= 4 && isSorted(ap)) {
                    LineSegment ls = new LineSegment(ap.get(0), ap.get(ap.size() - 1));
                    lineSegments.add(ls);
                }
            }
        }
        done = true;
        return lineSegments.size();
    }

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
            for (Point p : points) {
                p.draw();
                StdOut.println(i + ":" + p);
                i++;
            }

            StdDraw.show();

            // print and draw the line segments
            FastCollinearPoints collinear = new FastCollinearPoints(points);
            StdOut.println("Found amount: " + collinear.segments().length);
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
