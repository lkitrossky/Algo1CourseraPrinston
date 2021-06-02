It is Algo1 course week 3, Assignment: Collinear
https://www.coursera.org/learn/algorithms-part1/
https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php

The goal is to explain sorting and its applications.
The task is simple: 
from a set of points on surface find all straight segments, which have >= 4 points
The results muist not have duplicates and every segment is represented by a pair of extreme points.
There are input files like input6.txt: number of points N and series on x y.
The course provides some code for Point, LineSegment. First of ll drawing.
IntelliJ is rathe powerful IDE.
The first task was to finish Point.
The second BruteCollinearPoints: pass over all combinations of 4 points ( ~ N^4) and check. First points must be sorted by x,y
The most difficlut is the third task FastCollinearPoints.
The idea is pass over all points, then sort others by slope to the first. Then to pass over all slopes seeking segments.
Troubles: how to use two sortings?
There is compareTo which must appear fro defaul sorting due to
public class Point implements Comparable<Point>
and also slopeOrder in Point which was difficult to write, reminds pointer to function in C++:
 public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point point1, Point point2) {
     ...	return Double.compare(...);	
            }
        };
    }
The next trouble: how to prevent duplicates?
The solutuion was: from every points pass over all others, taking segments.
Every found segment sort again by x,y compareTo, then chack that the main point is low left to the foudn segment.
If true, keep the point and the last point.
The debug was difficult, also Java is still not some familiar.
https://www.coursera.org/learn/algorithms-part1/programming/prXiW/collinear-points/submission 100/100






