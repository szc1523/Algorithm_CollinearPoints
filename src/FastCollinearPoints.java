/* 
 * by Sun Zhicheng 
 * for coursera course Algorithms 
 * week 3 Assignment: Collinear Point
 */

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final Point[] pts;
    private final LineSegment[] lines;
    private int count;
    
    public FastCollinearPoints(Point[] inPoints) {
        
        //initialize points
        if (inPoints == null)
            throw new NullPointerException("input pointer is null");
        pts = inPoints.clone();
        //sort array to detect repeated point.
        Arrays.sort(pts);
        for (int i = 0; i < pts.length - 1; i++) {
            //must use points[i].compare!
            //can't use point[i] = point[i + 1]!!!
            if (pts[i].compareTo(pts[i + 1]) == 0)
                throw new IllegalArgumentException("input contains "
                        + "a repeated point");
        }
        
        //initialize array list for line segment and point save
        ArrayList<LineSegment> seglist = new ArrayList<LineSegment>();
            
        //mistake 1 : define a double[] slope! and sort
        //can't use this new slope to index point
        //cause sort change the order of slope
        
        //mistake 2 : use ArrayList to delete points after each iteration
        //cause this will also make redundant line segments
        int p = 0;
        while (p < pts.length - 3) {            
            Arrays.sort(pts, pts[p].slopeOrder());
            double[] slopes = new double[pts.length];
            for (int i = 0; i < pts.length; i++)
                slopes[i] = pts[0].slopeTo(pts[i]);
            
            //detect equal slopes
            // use i = 3 and check slopes[i] == slopes[i - 1] is much easier!!!
            int i = 0; 
            //its length - 2 !!!!! i wrote length - 3 
            while (i < pts.length - 2) {
                if (slopes[i] == slopes[i + 1] 
                        && slopes[i] == slopes[i + 2]) {
                    int j = 0;
                    while (i + 2 + j + 1 < pts.length && slopes[i] 
                            == slopes[i + 2 + j + 1]) {
                        j++;
                    }
                    if (isDuplicate(i, j)) {
                        i = i + 2 + j;
                        continue;
                    }                        
                    
                    LineSegment l = new LineSegment(pts[0], 
                            pts[i + j + 2]);
                    seglist.add(l);
                    //l.draw();    
                    
                    //start from a new unconnected point
                    //there is already an i++ in the for loop
                    i = i + 2 + j;   
                }                
                i++;
            }   
            //it is needed for correct iteration of p
            Arrays.sort(pts);
            p++;
        }
        count = seglist.size();
        lines = seglist.toArray(new LineSegment[count]); 
    }

    public int numberOfSegments() {
        return count;
    }
    
    public LineSegment[] segments() {
        //return lines make internal pointer dereferenced by clients
        LineSegment[] lines2 = lines.clone();
        //use cast can prevent dereference????
        return (LineSegment[]) lines2;
    }
    
    private boolean isDuplicate(int i, int j) {
        for (int k = 0; k < j + 2; k++) {
            //its > = instead of ==1. to avoid dependence on ==1
            if (pts[0].compareTo(pts[i + k]) > 0) {
                return true;
            }
        }
        return false;
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
