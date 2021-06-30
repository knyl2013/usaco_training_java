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
        public String toString()
        {
            return "(" + x + ", " + y + ")";
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
        public String toString()
        {
            return a.x + " " + a.y + " " + b.x + " " + b.y;
        }
    }
	
	static Point observer;
	static Line[] boundaries;
	static int n;
	
	static int[] getDir(Point a, Point b)
	{
		return new int[]{a.x-b.x>0?1:0, a.y-b.y>0?1:0};
	}
	
	// Return true if l1 and l2 has any intersection
	static boolean hasIntersection(Line l1, Line l2)
    {
		double x1 = l1.a.x;
		double x2 = l1.b.x;
		double x3 = l2.a.x;
		double x4 = l2.b.x;
		
		double y1 = l1.a.y;
		double y2 = l1.b.y;
		double y3 = l2.a.y;
		double y4 = l2.b.y;
		
		double den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (den == 0) {
			return false;
		}

		double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
		double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den;
		return (t > 0 && t <= 1 && u > 0 && u <= 1);
    }
	
	// Return true if (pt-observer) line has no intersection in the middle
	static boolean isGood(Point pt, Line line, int idx)
	{
		Point otherEnd = line.a == pt ? line.b : line.a;
		Line ptObserver = new Line(observer.x, observer.y, pt.x, pt.y);
		// boolean debug = line.toString().equals("1 8 2 5");
		for (int i = 0; i < n; i++) {
			if (idx == i) continue;
			Line cur = boundaries[i];
			boolean endPtIntersect = (Point.equal(pt, cur.a) || Point.equal(pt, cur.b));
			if (endPtIntersect) {
				if (hasIntersection(new Line(observer.x, observer.y, otherEnd.x, otherEnd.y), cur)) {
					return false;
				}
				Point endPtOther = Point.equal(pt, cur.a) ? cur.b : cur.a;
				if (hasIntersection(new Line(observer.x, observer.y, endPtOther.x, endPtOther.y), cur)) {
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
		
		
		
		// System.out.println(hasIntersection(new Line(2,5,5,5), new Line(3,5,4,9)));
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
	// static String taskName = "fence4";
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


