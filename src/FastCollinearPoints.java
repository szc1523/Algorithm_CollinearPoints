import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

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
        
        //initialize array list
        ArrayList<LineSegment> seglist = new ArrayList<LineSegment>();
        ArrayList<Point>       plist = new ArrayList<Point>(); 
        for (int i = 0; i < points.length; i++)
            plist.add(points[i]);
        
        //mistake1 : define a double[] slope! and sort
        //can't use this new slope to index point
        //cause sort change the order of slope
        
        //mistake2 : iterate of points, cause this will make 
        //redundant line segments
        while (plist.size() > 3) {
            Collections.sort(plist, plist.get(0).slopeOrder());
            double[] slopes = new double[plist.size() - 1];
            for (int i = 0; i < plist.size() - 1; i++) {
                slopes[i] = plist.get(0).slopeTo(plist.get(i + 1));
            }
            //detect equal slopes
            int i = 0;
            //its length - 2 !!!!! i wrote length - 3 
            //slope is smaller than points
            while (i < slopes.length - 2) {
                if (slopes[i] == slopes[i + 1] && slopes[i] == slopes[i + 2]) {
                    int j = 0;
                    while (i + 2 + j + 1 < slopes.length && 
                            slopes[i] == slopes[i + 2 + j + 1]) {
                        j++;
                    }
                    LineSegment l = new LineSegment(plist.get(0), 
                            plist.get(i + j + 3));
                    seglist.add(l);
                    l.draw();    
                    //start from a new unconnected point
                    //there is already an i++ in the for loop
                    i = i + 2 + j;   
                }                
                i++;
            }         
            plist.remove(0);
            Collections.sort(plist);
        }
        plist = null;
        
        
        count = seglist.size();
        lines = seglist.toArray(new LineSegment[count]); 
    }

    public int numberOfSegments() {
        return count;
    }
    
    public LineSegment[] segments() {
        //return lines make internal pointer dereferenced by clients
        LineSegment[] lines2 = lines.clone();
        return lines2;
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
        for (LineSegment l: lines) {
            l.draw();  
            StdOut.println(l.toString());
        }
        StdOut.println(lines.length);
    }

}
