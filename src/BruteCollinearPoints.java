import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

/* 
 * by Sun Zhicheng 
 * for coursera course Algorithms 
 * week 3 Assignment: Collinear Point
 */
public class BruteCollinearPoints {
    // means can't change points but can change points[i]
    private final Point[] points; 
    private final LineSegment[] lines;
    private final int count;
    
    // put all the code in constructor to make final work
    public BruteCollinearPoints(Point[] points) {
        // initialize points
        //directly use .clone() method! instead of assignment !!
        this.points = points.clone();
        Arrays.sort(points);
        
        //detect line segment
        ArrayList<LineSegment> seglist = new ArrayList<LineSegment>();
        //mind i < length - 4
        for (int p = 0; p < points.length - 4; p++)
            for (int q = p + 1; q < points.length; q++)
                for (int r = q + 1; r < points.length; r++)
                    for (int s = r + 1; s < points.length; s++)
                        if (points[p].slopeTo(points[q]) == 
                        points[p].slopeTo(points[r])
                         && points[p].slopeTo(points[q]) == 
                         points[p].slopeTo(points[s])) {
                            seglist.add(new LineSegment(points[p], points[s]));
                         }
        
        count = seglist.size();
        lines = seglist.toArray(new LineSegment[count]); 
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
        
        //draw line segment
        BruteCollinearPoints brute = new BruteCollinearPoints(points);
        LineSegment[] lines = brute.segments();
        for (LineSegment l: lines)
            l.draw();       
        
    }

}
