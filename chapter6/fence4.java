/*
ID: wyli1231
LANG: JAVA
TASK: fence4
*/

import java.io.*;
import java.util.*;

public class fence4 {
    static class Point {
        double x, y;
        public Point() {}
        public Point(double x, double y)
        {
            this.x = x;
            this.y = y;
        }
        // get distance of two points
        static double distance(Point p1, Point p2)
        {
            double dx = p1.x - p2.x;
            double dy = p1.y - p2.y;
            return Math.sqrt(dx*dx + dy*dy);
        }
        public String toString()
        {
            return "(" + x + ", " + y + ")";
        }
    }
    static class Line {
        double x1, y1, x2, y2;
        public Line(double x1, double y1, double x2, double y2)
        {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        // tell if point is on this line
        boolean onLine(Point p)
        {
            Point from = new Point(x1, y1);
            Point to = new Point(x2, y2);
            double dist = Point.distance(from, to);
            double a = Point.distance(from, p);
            double b = Point.distance(p, to);
            return a + b == dist;
        }
        // get intersect point of two lines, null if no intersect
        static Point getIntersectPoint(Line l1, Line l2)
        {
            double x1 = l1.x1, x2 = l1.x2, x3 = l2.x1, x4 = l2.x2;
            double y1 = l1.y1, y2 = l1.y2, y3 = l2.y1, y4 = l2.y2;
            double D = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
            if (D == 0) return null;
            double numX = (((x1 * y2) - (y1 * x2)) * (x3 - x4)) - ((x1 - x2) * ((x3 * y4) - (y3 * x4)));
            double numY = (((x1 * y2) - (y1 * x2))) * (y3 - y4) - ((y1 - y2) * ((x3 * y4) - (y3 * x4)));
            return new Point(numX / D, numY / D);
        }
        // rotate the line by degree (from is the center)
        void rotateLineClockWise(int angle) 
        {
            double xRot = x1 + Math.cos(Math.toRadians(angle)) * (x2 - x1) - Math.sin(Math.toRadians(angle)) * (y2 - y1);
            double yRot = y1 + Math.sin(Math.toRadians(angle)) * (y2 - x1) + Math.cos(Math.toRadians(angle)) * (y2 - y1);
            x2 = xRot;
            y2 = yRot;
        }
        public String toString()
        {
            return "(" + x1 + ", " + y1 + "), (" + x2 + ", " + y2 + ")"; 
        }
    }
    
    // tell the distance of the hit point from the ray to the boundary
    // return -1 if ray does not hit the boundary
    static double hit(Line ray, Line boundary)
    {
        Point intersectPt = Line.getIntersectPoint(ray, boundary);
        if (!boundary.onLine(intersectPt)) return -1;
        Point ori = new Point(ray.x1, ray.y1);
        System.out.println(ori);
        System.out.println(intersectPt);
        return Point.distance(ori, intersectPt);
    }
    static void solve()
    {
        Line l1 = new Line(0, 0, 0, 15);
        Line l2 = new Line(1, 0, 0.9, 1);

        System.out.println(l2);
        for (int i = 0; i < 360; i++) {
            l2.rotateLineClockWise(1);
            System.out.println(l2);
        }

        // Point intersect = Line.getIntersectPoint(l1, l2);
        // System.out.println(intersect);
        // System.out.println(l1.onLine(intersect));
        // System.out.println(l2.onLine(intersect));

        // System.out.println(hit(l1, l2));
        // System.out.println(hit(l2, l1));
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
    static boolean logTime = !true;
    
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
    
    private static void tr(Object... o) { if(logTime)System.out.println(Arrays.deepToString(o)); }
}


