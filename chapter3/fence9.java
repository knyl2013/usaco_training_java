/*
ID: wyli1231
LANG: JAVA
TASK: fence9
*/



import java.io.*;
import java.util.*;

public class fence9 {
    static class Point {
        int x, y;
        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        public static int crossProduct(Point a, Point b)
        {
            return (a.x * b.y) - (a.y * b.x);
        }
        public static Point subtract(Point a, Point b)
        {
            return new Point(a.x-b.x, a.y-b.y);
        }
        public String toString()
        {
            return "(" + x + "," + y + ")";
        }
    }
    static class Line {
        Point a, b;
        double length;
        public Line(Point a, Point b)
        {
            this.a = a;
            this.b = b;
            this.length = distance(a, b);
        }
        private double distance(Point from, Point to)
        {
            int x = (from.x - to.x) * (from.x - to.x);
            int y = (from.y - to.y) * (from.y - to.y);
            return Math.sqrt(x + y);
        }
        public int getSide(Point c)
        {
            return Point.crossProduct(Point.subtract(b, a), Point.subtract(c, a));
        }
        public String toString()
        {
            return "[" + a + " to " + b + "]";
        }
    }
    static Line a, b, c;
    static Point center;
    static boolean outside(Point pt)
    {
        if (a.getSide(pt) == 0 || b.getSide(pt) == 0 || c.getSide(pt) == 0) return true;
        boolean ba = a.getSide(pt) > 0, bb = b.getSide(pt) > 0, bc = c.getSide(pt) > 0;
        return !(ba == bb && bb == bc);
    }
    static void solve()
    {
        int n = ni(), m = ni(), p = ni();
        int ans = 0;
        int rightMost = Math.max(n, p);
        Point bottomLeft = new Point(0, 0);
        Point bottomRight = new Point(p, 0);
        Point top = new Point(n, m);
        a = new Line(bottomLeft, top);
        b = new Line(top, bottomRight);
        c = new Line(bottomRight, bottomLeft);
        Point prevValidLeft = null, prevValidRight = null;
        for (int y = 1; y <= m; y++) {
            if (prevValidLeft == null) { // this block should run exactly once
                for (int x = 1; x <= rightMost; x++) {
                    if (outside(new Point(x, y))) continue;
                    if (prevValidLeft == null) prevValidLeft = new Point(x, y);
                    prevValidRight = new Point(x, y);
                    ans++;
                }
            }
            else {
                Point left = new Point(prevValidLeft.x, y);
                Point right = new Point(prevValidRight.x, y);
                // left try to stay as much left as possible, unless it is outside of triangle
                while (outside(left) && left.x <= rightMost) {
                    left.x++;
                }
                if (left.x > rightMost) continue; // no lattice point at this row
                // if m > p, then right boundary will keep increasing
                // otherwise it will keep shrinking
                if (m > p) {
                    right.x = Math.max(right.x, left.x);
                    // right try to move as much right as possible, unless x + 1 is outside
                    while (!outside(new Point(right.x + 1, y))) {
                        right.x++;
                    }
                }
                else {
                    // right try to stay as much right as possible, unless current position is out
                    while (outside(right)) {
                        right.x--;
                    }
                }
                if (right.x >= left.x)
                    ans += right.x - left.x + 1;
                prevValidLeft = left;
                prevValidRight = right;
            }
        }
        out.printf("%d\n", ans);
    }


    

    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "fence9";
    
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


