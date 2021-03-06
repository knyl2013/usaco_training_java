/*
ID: wyli1231
LANG: JAVA
TASK: fence4
*/

import java.io.*;
import java.util.*;

public class fence4 {
    static class Point {
        int x, y;
        public Point() {}
        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
		public static boolean equal(Point a, Point b)
		{
			return a.x == b.x && a.y == b.y;
		}
    }
    static class Line implements Comparable<Line> {
		Point a, b;
		int idx;
		public Line(int x1, int y1, int x2, int y2)
		{
			this(x1, y1, x2, y2, -1);
		}
		public Line(int x1, int y1, int x2, int y2, int idx)
		{
			this.a = new Point(x1, y1);
			this.b = new Point(x2, y2);
			this.idx = idx;
		}
		@Override
		public int compareTo(Line other) 
		{
			return this.idx - other.idx;
		}
    }
	
	static Point observer;
	static Line[] boundaries;
	static int n;
	
	static int[] getDir(Point a, Point b)
    {
        int dx = a.x - b.x;
        int dy = a.y - b.y;
        if (dx < 0) dx = -1;
        if (dx > 0) dx = 1;
        if (dy < 0) dy = -1;
        if (dy > 0) dy = 1;
        return new int[]{dx, dy};
    }

    static double hit(Line ray, Line boundary)
    {
        double x1 = boundary.a.x;
        double x2 = boundary.b.x;
        double x3 = ray.a.x;
        double x4 = ray.b.x;
        
        double y1 = boundary.a.y;
        double y2 = boundary.b.y;
        double y3 = ray.a.y;
        double y4 = ray.b.y;
        
        double den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (den == 0) {
            return -1;
        }

        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
        double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den;
        if (t >= 0 && t <= 1 && u >= 0) {
            return 1;
        } else {
            return -1;
        }
    
    }
    static double length(Line l)
    {
        int dx = l.a.x - l.b.x;
        int dy = l.a.y - l.b.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
	static boolean sameLine(Line a, Line b)
	{
		int arise = a.a.y - a.b.y;
		int arun = a.a.x - a.b.x;
		int brise = b.a.y - b.b.y;
		int brun = b.a.x - b.b.x;
		// Same line if (arise / arun == brise / brun);
		return arise * brun == brise * arun;
	}
	// Return true if (pt-observer) line has no intersection in the middle
	static boolean isGood(Point pt, Line line, int idx)
	{
		boolean parallelX = line.a.x == observer.x && line.b.x == observer.x;
		boolean parallelY = line.a.y == observer.y && line.b.y == observer.y;
		if (parallelX || parallelY) return false;
		Point otherEnd = line.a == pt ? line.b : line.a;
		Line ptObserver = new Line(observer.x, observer.y, pt.x, pt.y);
        Line otherEndObserver = new Line(observer.x, observer.y, otherEnd.x, otherEnd.y);
        int[] ptObserverDir = getDir(pt, observer);
		for (int i = 0; i < n; i++) {
			if (idx == i) continue;
			Line cur = boundaries[i];
			boolean endPtIntersect = (Point.equal(pt, cur.a) || Point.equal(pt, cur.b));
            if (endPtIntersect) {
				if (sameLine(cur, line)) continue;
                Point endPtOther = Point.equal(pt, cur.a) ? cur.b : cur.a;
                Line endPtOtherObserver = new Line(endPtOther.x, endPtOther.y, observer.x, observer.y);
                int[] ptCurDir = getDir(pt, endPtOther);
                int[] otherEndCurDir = getDir(otherEnd, endPtOther);
                int[] curObserverDir = getDir(endPtOther, observer);
                if (Arrays.equals(ptObserverDir, ptCurDir) && Arrays.equals(curObserverDir, ptObserverDir)) {
                    return false;
                }
                boolean opposite = (ptObserverDir[0] + otherEndCurDir[0] == 0) && (ptObserverDir[1] + otherEndCurDir[1] == 0);
				if (opposite) continue;
				opposite = (ptObserverDir[0] + ptCurDir[0] == 0) && (ptObserverDir[1] + ptCurDir[1] == 0);
				if (opposite) continue;
				if (sameLine(cur, endPtOtherObserver)) continue;
                if (hit(cur, otherEndObserver) != -1) {
                    return false;
                }
			}
			else if (hasIntersection(ptObserver, cur)) {
				return false;
			}
		}
		return true;
	}
    
    static void solve()
    {
        n = ni();
        int ox = ni(), oy = ni();
        observer = new Point(ox, oy);
        boundaries = new Line[n];
        int firstX = ni(), firstY = ni();
        int prevX = firstX, prevY = firstY;
        int bIdx = 0;
        for (int i = 0; i < n-1; i++) {
            int curX = ni(), curY = ni();
            boundaries[bIdx] = new Line(prevX, prevY, curX, curY, bIdx);
			if (i == n-2)
				boundaries[bIdx].idx++;
            prevX = curX;
            prevY = curY;
			bIdx++;
        }
        boundaries[bIdx] = new Line(firstX, firstY, prevX, prevY, bIdx-1);
        List<Line> ans = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			Line line = boundaries[i];
			if (isGood(line.a, line, i) || isGood(line.b, line, i)) {
				ans.add(line);
			}
		}
		if (ans.isEmpty()) {
			out.print("NOFENCE\n");
			return;
		}
		Collections.sort(ans);
        out.printf("%d\n", ans.size());
        for (Line line : ans) {
            out.printf("%d %d %d %d\n", line.a.x, line.a.y, line.b.x, line.b.y);
        }
    }


	/*Intersection lib from g4g starts*/
	static int orientation(Point p, Point q, Point r)
	{
		// See https://www.geeksforgeeks.org/orientation-3-ordered-points/
		// for details of below formula.
		int val = (q.y - p.y) * (r.x - q.x) -
				(q.x - p.x) * (r.y - q.y);
	  
		if (val == 0) return 0; // colinear
	  
		return (val > 0)? 1: 2; // clock or counterclock wise
	}
	  static boolean onSegment(Point p, Point q, Point r)
	{
		if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
			q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
		return true;
	  
		return false;
	}
	// The main function that returns true if line segment l1-'p1q1'
	// and l2-'p2q2' intersect.
	static boolean hasIntersection(Line l1, Line l2)
	{
		Point p1 = l1.a, q1 = l1.b, p2 = l2.a, q2 = l2.b;
		// Find the four orientations needed for general and
		// special cases
		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);
	  
		// General case
		if (o1 != o2 && o3 != o4)
			return true;
	  
		// Special Cases
		// p1, q1 and p2 are colinear and p2 lies on segment p1q1
		if (o1 == 0 && onSegment(p1, p2, q1)) return true;
	  
		// p1, q1 and q2 are colinear and q2 lies on segment p1q1
		if (o2 == 0 && onSegment(p1, q2, q1)) return true;
	  
		// p2, q2 and p1 are colinear and p1 lies on segment p2q2
		if (o3 == 0 && onSegment(p2, p1, q2)) return true;
	  
		// p2, q2 and q1 are colinear and q1 lies on segment p2q2
		if (o4 == 0 && onSegment(p2, q1, q2)) return true;
	  
		return false; // Doesn't fall in any of the above cases
	}
	/*Intersection lib from g4g ends*/


    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
	static String taskName = "fence4";
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


