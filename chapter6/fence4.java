/*
ID: wyli1231
LANG: JAVA
TASK: fence4
*/

import java.io.*;
import java.util.*;

public class fence4 {
	static boolean debug = false;
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
    static class Line implements Comparable<Line> {
        double x1, y1, x2, y2;
		int idx;
        public Line(double x1, double y1, double x2, double y2)
        {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
		public Line(double x1, double y1, double x2, double y2, int idx)
		{
			this(x1, y1, x2, y2);
			this.idx = idx;
		}
		@Override
		public int compareTo(Line other) 
		{
			return this.idx - other.idx;
		}
		boolean isOnLine(Point endPoint1, Point endPoint2, Point checkPoint) {
			return ((checkPoint.y - endPoint1.y)) * ((endPoint2.x - endPoint1.x))
				== ((endPoint2.y - endPoint1.y)) *((checkPoint.x - endPoint1.x));
		}
        // tell if point is on this line
        boolean onLine(Point p)
        {
            Point from = new Point(x1, y1);
            Point to = new Point(x2, y2);
			// return isOnLine(from, to, p);
            double dist = Point.distance(from, to);
            double a = Point.distance(from, p);
            double b = Point.distance(p, to);
			if (debug)
				System.out.println(a + " " + b + " " + dist + " " + (a+b));
            return Math.abs((a + b) - dist) < 0.00001;
			
			// Point A = new Point(x1, y1);
			// Point C = new Point(x2, y2);
			
			// if AC is vertical
			// if (A.x == C.x) return B.x == C.x;
			// if AC is horizontal
			// if (A.y == C.y) return B.y == C.y;
			// match the gradients
			// return (A.x - C.x)*(A.y - C.y) == (C.x - B.x)*(C.y - B.y);
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
        void rotatePoint(double angle) {
          double s = Math.sin(angle);
          double c = Math.cos(angle);
          double px = x2;
          double py = y2;
          
          // translate point back to origin:
          px -= x1;
          py -= y1;

          // rotate point
          double xnew = px * c - py * s;
          double ynew = px * s + py * c;
          
          // translate point back:
          px = xnew + x1;
          py = ynew + y1;

          x2 = px;
          y2 = py;
        }
        public String toString()
        {
            return "(" + x1 + ", " + y1 + "), (" + x2 + ", " + y2 + ")" + ", dist: "  + Point.distance(new Point(x1, y1), new Point(x2, y2)); 
        }
    }
    
    // tell the distance of the hit point from the ray to the boundary
    // return -1 if ray does not hit the boundary
    static double hit(Line ray, Line boundary)
    {
        // Point intersectPt = Line.getIntersectPoint(ray, boundary);
		// if (debug) System.out.println("intersectPt: " + intersectPt);
        // if (intersectPt == null || !boundary.onLine(intersectPt)) return -1;
		// boolean dirx = (ray.x2 - ray.x1) > 0;
		// boolean diry = (ray.y2 - ray.y1) > 0;
		// boolean hitx = (intersectPt.x - ray.x1) > 0;
		// boolean hity = (intersectPt.y - ray.y1) > 0;
		// if (dirx != hitx || diry != hity) return -1	;
        // Point ori = new Point(ray.x1, ray.y1);
        // return Point.distance(ori, intersectPt);
		
		double x1 = boundary.x1;
		double x2 = boundary.x2;
		double x3 = ray.x1;
		double x4 = ray.x2;
		
		double y1 = boundary.y1;
		double y2 = boundary.y2;
		double y3 = ray.y1;
		double y4 = ray.y2;
		
		double den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (den == 0) {
			return -1;
		}

		double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
		double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den;
		if (t > 0 && t < 1 && u > 0) {
			Point pt = new Point();
			pt.x = x1 + t * (x2 - x1);
			pt.y = y1 + t * (y2 - y1);
			return Point.distance(pt, new Point(ray.x1, ray.y1));
		} else {
			return -1;
		}
	
    }
    static void solve()
    {
        int n = ni();
        double ox = nd(), oy = nd();
        Line observer = new Line(ox, oy, ox+0.1, oy);
        Line[] boundaries = new Line[n];
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
        // System.out.println(Arrays.toString(boundaries));
        int split = 360;
        double tot = 2 * Math.PI;
        List<Line> ans = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (int i = 0; i < split; i++) {
			if (i == 0 || i == 90 || i == 180 || i == 270) {
				observer.rotatePoint(tot / split);
				continue;
			}
			if (i == 200) {
				debug = true;
			} else {
				debug = false;
			}
			debug = false;
            Line best = null;
			Point pt = null;
            double bestDist = -1;
            for (Line b : boundaries) {
                double d = hit(observer, b);
				// System.out.println(d);
				if (debug) {
					// if (b.toString().startsWith("(5.0, 7.0), (3.0, 5.0)")) {
						// System.out.println("===========");
						// System.out.println(b + " " + d);
						// System.out.println(observer + " || " + b + " || " + d);
						// Point temp = Line.getIntersectPoint(observer, b);
						// System.out.println(temp);
						// System.out.println(b.onLine(temp));
						// System.out.println("===========");
					// }
				}
                if (d == -1) continue;
                if (best == null || d < bestDist) {
					// pt = Line.getIntersectPoint(observer, b);
                    best = b;
                    bestDist = d;
					
					if (debug) {
						System.out.println(best + " " + bestDist);
					}
                }
            }
            if (best != null) {
                String key = best.toString();
                if (!seen.contains(key)) {
                    // System.out.println("angle: " + i + ", line: " + best + ", intersection: " + pt);
                    ans.add(best);
                    seen.add(key);
                }
            }
            observer.rotatePoint(tot / split);
        }
		if (ans.isEmpty()) {
			out.print("NOFENCE\n");
			return;
		}
		Collections.sort(ans);
        out.printf("%d\n", ans.size());
        for (Line line : ans) {
            out.printf("%d %d %d %d\n", (int)line.x1, (int)line.y1, (int)line.x2, (int)line.y2);
        }

        // Line l1 = new Line(0, 0, 0, 15);
        // Line l2 = new Line(1, 0, 0.9, 1);
        // int split = 360;
        // double tot = 2 * Math.PI;
        // System.out.println(l2);
        // for (int i = 0; i < split; i++) {
        //     l2.rotatePoint(tot / 360);
        //     System.out.println(l2);
        // }
        // for (int i = 0; i < 720; i++) {
        //     l2.rotateLineClockWise(1);
        //     System.out.println(l2);
        // }

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


