/*
ID: wyli1231
LANG: JAVA
TASK: fc
*/

import java.io.*;
import java.util.*;

public class fc {
    static double dist(Point a, Point b)
    {
        double x = (a.x - b.x), y = (a.y - b.y);
        return Math.sqrt(x*x + y*y);
    }
    static void solve()
    {
        int n = ni();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(nd(), nd());
        }
        if (n < 3) {out.print("0\n"); return;}
        List<Point> hulls = ConvexHull.get(points);
        double ans = dist(hulls.get(0), hulls.get(hulls.size() - 1));
        for (int i = 1; i < hulls.size(); i++) {
            ans += dist(hulls.get(i), hulls.get(i - 1));
        }
        String toReturn = String.format("%.3f", ans);
        out.printf("%s\n", toReturn.substring(0, toReturn.length() - 1));
    }

  // Java program to find convex hull of a set of points. Refer 
// https://www.geeksforgeeks.org/orientation-3-ordered-points/
// for explanation of orientation()
  
static class Point
{
    double x, y;
    Point(double x, double y){
        this.x=x;
        this.y=y;
    }
}
  
static class ConvexHull {
      
    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are colinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    public static int orientation(Point p, Point q, Point r)
    {
        double val = (q.y - p.y) * (r.x - q.x) -
                  (q.x - p.x) * (r.y - q.y);
       
        if (val == 0) return 0;  // collinear
        return (val > 0)? 1: 2; // clock or counterclock wise
    }
      
    // Prints convex hull of a set of n points.
    public static List<Point> get(Point points[])
    {
    int n = points.length;
        // Initialize Result
        List<Point> hull = new ArrayList<Point>();

        // There must be at least 3 points
        if (n < 3) return hull;
       
        // Find the leftmost point
        int l = 0;
        for (int i = 1; i < n; i++)
            if (points[i].x < points[l].x)
                l = i;
       
        // Start from leftmost point, keep moving 
        // counterclockwise until reach the start point
        // again. This loop runs O(h) times where h is
        // number of points in result or output.
        int p = l, q;
        do
        {
            // Add current point to result
            hull.add(points[p]);
       
            // Search for a point 'q' such that 
            // orientation(p, q, x) is counterclockwise 
            // for all points 'x'. The idea is to keep 
            // track of last visited most counterclock-
            // wise point in q. If any point 'i' is more 
            // counterclock-wise than q, then update q.
            q = (p + 1) % n;
              
            for (int i = 0; i < n; i++)
            {
               // If i is more counterclockwise than 
               // current q, then update q
               if (orientation(points[p], points[i], points[q]) == 2)
                   q = i;
            }
       
            // Now q is the most counterclockwise with
            // respect to p. Set p as q for next iteration, 
            // so that q is added to result 'hull'
            p = q;
       
        } while (p != l);  // While we don't come to first 
                           // point
       
        return hull;
    }
      
} // This code is contributed by Arnav Kr. Mandal.

    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
    // static String taskName = "fc";
    
    public static void main(String[] args) throws Exception
    {
        long S = System.currentTimeMillis();
        if (taskName != null) {
            File initialFile = new File(taskName + ".in");
            is = new FileInputStream(initialFile);
            out = new PrintWriter(taskName + ".out");
        }
        else {
            is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
            out = new PrintWriter(System.out);
        }
        
        solve();
        out.flush();
        long G = System.currentTimeMillis();
        tr(G-S+"ms");
    }
    
    private static boolean eof()
    {
        if(lenbuf == -1)return true;
        int lptr = ptrbuf;
        while(lptr < lenbuf)if(!isSpaceChar(inbuf[lptr++]))return false;
        
        try {
            is.mark(1000);
            while(true){
                int b = is.read();
                if(b == -1){
                    is.reset();
                    return true;
                }else if(!isSpaceChar(b)){
                    is.reset();
                    return false;
                }
            }
        } catch (IOException e) {
            return true;
        }
    }
    
    private static byte[] inbuf = new byte[1024];
    static int lenbuf = 0, ptrbuf = 0;
    
    private static int readByte()
    {
        if(lenbuf == -1)throw new InputMismatchException();
        if(ptrbuf >= lenbuf){
            ptrbuf = 0;
            try { lenbuf = is.read(inbuf); } catch (IOException e) { throw new InputMismatchException(); }
            if(lenbuf <= 0)return -1;
        }
        return inbuf[ptrbuf++];
    }
    
    private static boolean isSpaceChar(int c) { return !(c >= 33 && c <= 126); }
    private static int skip() { int b; while((b = readByte()) != -1 && isSpaceChar(b)); return b; }
    
    private static double nd() { return Double.parseDouble(ns()); }
    private static char nc() { return (char)skip(); }
    
    private static String ns()
    {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while(!(isSpaceChar(b))){ // when nextLine, (isSpaceChar(b) && b != ' ')
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }
    
    private static char[] ns(int n)
    {
        char[] buf = new char[n];
        int b = skip(), p = 0;
        while(p < n && !(isSpaceChar(b))){
            buf[p++] = (char)b;
            b = readByte();
        }
        return n == p ? buf : Arrays.copyOf(buf, p);
    }
    
    private static char[][] nm(int n, int m)
    {
        char[][] map = new char[n][];
        for(int i = 0;i < n;i++)map[i] = ns(m);
        return map;
    }
    
    private static int[] na(int n)
    {
        int[] a = new int[n];
        for(int i = 0;i < n;i++)a[i] = ni();
        return a;
    }
    
    private static int ni()
    {
        int num = 0, b;
        boolean minus = false;
        while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
        if(b == '-'){
            minus = true;
            b = readByte();
        }
        
        while(true){
            if(b >= '0' && b <= '9'){
                num = num * 10 + (b - '0');
            }else{
                return minus ? -num : num;
            }
            b = readByte();
        }
    }
    
    private static long nl()
    {
        long num = 0;
        int b;
        boolean minus = false;
        while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
        if(b == '-'){
            minus = true;
            b = readByte();
        }
        
        while(true){
            if(b >= '0' && b <= '9'){
                num = num * 10 + (b - '0');
            }else{
                return minus ? -num : num;
            }
            b = readByte();
        }
    }
    
    private static void tr(Object... o) { if(INPUT.length() != 0)System.out.println(Arrays.deepToString(o)); }
}


