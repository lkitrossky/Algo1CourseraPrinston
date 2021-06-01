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
import java.util.Collections;
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

    public int numberOfSegments() {
        if (done)
            return lineSegments.size();
        int pointsAmount = points.length;
        if (pointsAmount < 4)
            return 0;
        // lineSegments is the recipient of results
        for (int i1 = 0; i1 < pointsAmount; i1++) { // all points can be left bottom start of ray
            Point[] otherPoints = new Point[pointsAmount - 1];
            int index = 0;
            for (int i2 = 0; i2 < pointsAmount; i2++) {
                if (i1 == i2)
                    continue;
                otherPoints[index++] = points[i2];
            } // otherPoints - all except i1
            Arrays.sort(otherPoints, points[i1].slopeOrder()); // except i1 sorted by slope
            // initiate slope movement
            int current = 0;
            double slope = points[i1]
                    .slopeTo(otherPoints[current]); // we will check all points on i1, i2 line
            ArrayList<Point> ap = new ArrayList<Point>();
            while (current < otherPoints.length) {
                double currentSlope = points[i1].slopeTo(otherPoints[current]);
                if (slope == currentSlope) {
                    ap.add(otherPoints[current]); // we are still in the same slope, cash point
                }
                else { // slope finished
                    if (ap.size() >= 3) { // in addition to i1 we have at least three
                        Collections.sort(ap); // sort to check > i1 and find final
                        if (ap.get(0).compareTo(points[i1])
                                > 0) { // i1 in the beginning, cash segment
                            lineSegments.add(new LineSegment(points[i1], ap.get(ap.size() - 1)));
                        }
                    }
                    // initiate new slope
                    ap = new ArrayList<Point>();
                    ap.add(otherPoints[current]);
                    slope = points[i1].slopeTo(otherPoints[current]);
                }
                current++; // in any case, move
            } // all points except i1 passed
            // possibly slope is still not collected
            if (ap.size() >= 3) { // in addition to i1 we have at least three
                Collections.sort(ap); // sort to check > i1 and find final
                if (ap.get(0).compareTo(points[i1]) > 0) { // i1 in the beginning, cash segment
                    lineSegments.add(new LineSegment(points[i1], ap.get(ap.size() - 1)));
                }
            }
        } // every point was i1
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
            StdOut.println("Found amount: " + collinear.numberOfSegments());
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
