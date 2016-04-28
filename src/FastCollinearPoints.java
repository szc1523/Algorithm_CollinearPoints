import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeBU;
import edu.princeton.cs.algs4.StdDraw;

/* 
 * by Sun Zhicheng 
 * for coursera course Algorithms 
 * week 3 Assignment: Collinear Point
 */


public class FastCollinearPoints {
    private Point[] points;
    private LineSegment[] lines;
    private int count;
    
    public FastCollinearPoints(Point[] inPoints) {
        //initialize points
        if (inPoints == null)
            throw new NullPointerException("input pointer is null");
        points = inPoints.clone();
        //sort array to detect repeated point.
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            //must use points[i].compare!
            //can't use point[i] = point[i + 1]!!!
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException("input contains "
                        + "a repeated point");
        }
        
        //detect line segment
        ArrayList<LineSegment> seglist = new ArrayList<LineSegment>();
        //must length - 3
        for (int p = 0; p < points.length - 3; p++) {
            //mistake : define a double[] slope! and sort
            //can't use this new slope to index point
            //cause sort change the order of slope

            
            //make slopes Double to use merge sort
            //for the stability of merge sort
            Double[] slopes = new Double[points.length - p - 1];   
            for (int i = p + 1; i < points.length; i++) {
                slopes[i - p - 1] = points[p].slopeTo(points[i]);
            }          
            //MergeBU.sort(slopes);
            Arrays.sort(slopes);
            for (int i = 0; i < slopes.length - 3; i++) {
                if (slopes[i].equals(slopes[i + 1]) && 
                        slopes[i].equals(slopes[i + 2])) {
                    int j = 0;
                    while (i + 2 + j + 1 < slopes.length && 
                            slopes[i].equals(slopes[i + 2 + j + 1])) {
                        j++;
                    }
                    LineSegment l = new LineSegment(points[p], 
                            points[p + i + 3 + j]);
                    seglist.add(l);
                    l.draw();                    
                    i = i + 2 + j + 1;  //start from a new unconnected point
                }
            }            
        }   
        lines = seglist.toArray(new LineSegment[count]); 
        count = seglist.size();

    }

    public int numberOfSegments() {
        return count;
    }
    
    public LineSegment[] segments() {
        return lines;
    }
    
    public static void main(String[] args) {
        //initialize points array
        In in = new In(args[0]);
        int num = in.readInt();
        Point[] points = new Point[num];
        for (int i = 0; i < num; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        //draw points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32786);
        StdDraw.setYscale(0, 32786);
        for (Point p : points)
            p.draw();
        StdDraw.show();
        
        //detect line segment
        FastCollinearPoints fast = new FastCollinearPoints(points);
        LineSegment[] lines = fast.segments();
        for (LineSegment l: lines)
            l.draw();  
    }

}
