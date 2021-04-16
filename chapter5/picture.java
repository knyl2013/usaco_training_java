/*
ID: wyli1231
LANG: JAVA
TASK: picture
*/

import java.io.*;
import java.util.*;

public class picture {
	static class Line {
		int[] from;
		int[] to;
		boolean isVertical;
		int length;
		public Line(int[] from, int[] to)
		{
			this.from = from;
			this.to = to;
			this.isVertical = from[0] == to[0];
			if (this.isVertical)
				length = from[1] - to[1];
			else
				length = from[0] - to[0];
		}
		public boolean overlap(Line other)
		{
			if (this.isVertical != other.isVertical) return false;
			int start, end, ostart, oend;
			if (this.isVertical) {
				start = from[1];
				end = to[1];
				ostart = other.from[1];
				oend = other.to[1];
			}
			else {
				start = from[0];
				end = to[0];
				ostart = other.from[1];
				oend = other.to[1];
			}
			return Math.max(start, ostart) - Math.min(end, oend) > 0;
		}
	}
	static class Rectangle {
		int[] r;
		public Rectangle(int[] r)
		{
			this.r = r;
		}
		public static int peri(Rectangle rec)
		{
			int[] r = rec.r;
			int width = r[2] - r[0], height = r[3] - r[1];
			if (width < 0 || height < 0) return -1;
			return width * 2 + height * 2;
		}
		public static Rectangle overlap(Rectangle rec1, Rectangle rec2)
		{
			int[] r1 = rec1.r, r2 = rec2.r;
			int lowLeftX = Math.max(r1[0], r2[0]);
			int lowLeftY = Math.max(r1[1], r2[1]);
			int topRightX = Math.min(r1[2], r2[2]);
			int topRightY = Math.min(r1[3], r2[3]);
			return new Rectangle(new int[]{lowLeftX, lowLeftY, topRightX, topRightY});
		}
		public static boolean equal(Rectangle r1, Rectangle r2)
		{
			for (int i = 0; i < 4; i++) if (r1.r[i] != r2.r[i]) return false;
			return true;
		}
		public static boolean inside(Rectangle outer, Rectangle inner)
		{
			return Rectangle.equal(Rectangle.overlap(outer, inner), inner);
		}
		public static int[] topLeft(Rectangle r)
		{
			return new int[]{r.r[0], r.r[3]};
		}
		public static int[] topRight(Rectangle r)
		{
			return new int[]{r.r[2], r.r[3]};
		}
		public static int[] bottomLeft(Rectangle r)
		{
			return new int[]{r.r[0], r.r[1]};
		}
		public static int[] bottomRight(Rectangle r)
		{
			return new int[]{r.r[2], r.r[1]};
		}
		public static Line[] lines(Rectangle r)
		{
			return new Line[] {
				new Line(Rectangle.topLeft(r), Rectangle.topRight(r)),
				new Line(Rectangle.topLeft(r), Rectangle.bottomLeft(r)),
				new Line(Rectangle.bottomLeft(r), Rectangle.bottomRight(r)),
				new Line(Rectangle.topRight(r), Rectangle.bottomRight(r))
			};
		}
		public String toString()
		{
			return Arrays.toString(r);
		}
	}
	static List<Rectangle> getOverlaps(List<Rectangle> rects)
	{
		List<Rectangle> ans = new ArrayList<>();

		for (int i = 0; i < rects.size(); i++) {
			for (int j = i + 1; j < rects.size(); j++) {
				Rectangle o = Rectangle.overlap(rects.get(i), rects.get(j));
				int op = Rectangle.peri(o);
				boolean noOverlap = op <= 0;
				if (noOverlap) continue;
				ans.add(o);
			}
		}

		return ans;
	}
	static int calcPeri(List<Rectangle> rects)
	{
		if (rects.isEmpty()) return 0;

		int n = rects.size();
		boolean[] deleted = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (deleted[i]) continue;
			for (int j = j + 1; j < n; j++) {
				if (Rectangle.equal(rects.get(i), rects.get(j)) || Rectangle.inside(rects.get(i), rects.get(j)))
					deleted[j] = true;
			}
		}
		List<Rectangle> filtered = new ArrayList<>();
		for (int i = 0; i < n; i++) if (!deleted[i]) filtered.add(rects.get(i));
		rects = filtered;

		int ans = 0;
		for (int i = 0; i < rects.size(); i++) {
			// {start, end}
			List<int[]> tops = new ArrayList<>(), bottoms = new ArrayList<>(), lefts = new ArrayList<>(), rights = new ArrayList<>();
			Rectangle cur = rects.get(i);
			for (int j = 0; j < rects.size(); j++) {
				Rectangle o = Rectangle.overlap(cur, rects.get(j));
				int op = Rectangle.peri(o);
				boolean noOverlap = op <= 0;
				if (noOverlap) continue;

				tops.add(new int[]{o.r[]});
			}

		}
		return ans;
	}
	// static int calcPeri(List<Rectangle> rects)
	// {
		// if (rects.isEmpty()) return 0;
		// int ans = 0;
		// for (int i = 0; i < rects.size(); i++) {
			// List<Rectangle> overlaps = new ArrayList<>();
			// int cur = Rectangle.peri(rects.get(i));
			// for (int j = i + 1; j < rects.size(); j++) {
				// Rectangle o = Rectangle.overlap(rects.get(i), rects.get(j));
				// int op = Rectangle.peri(o);
				// boolean noOverlap = op <= 0;
				// if (noOverlap) continue;
				// overlaps.add(o);
				// cur -= op;
			// }
			// cur += calcPeri(getOverlaps(overlaps));
			// ans += cur;
		// }
		// return ans;
	// }
    static void solve()
    {
        List<Rectangle> rects = new ArrayList<>();
		int n = ni();
		// int ans = 0;
		for (int i = 0; i < n; i++) {
			int x1 = ni(), y1 = ni(), x2 = ni(), y2 = ni();
			rects.add(new Rectangle(new int[]{x1, y1, x2, y2}));
			// Rectangle cur = new Rectangle(new int[]{x1, y1, x2, y2});
			// int before = ans;
			// ans += Rectangle.peri(cur);
			// List<Rectangle> overlaps = new ArrayList<>();
			// for (Rectangle prev : rects) {
				// Rectangle o = Rectangle.overlap(prev, cur);
				// if (Rectangle.peri(o) <= 0) continue;
				// overlaps.add(o);
			// }

			// int add = Rectangle.peri(cur);
			// int minus = calcPeri(overlaps);
			// ans -= calcPeri(overlaps);
			// System.out.println("overlaps: " + overlaps);
			// System.out.println("before: " + before + ", add: " + add + ", minus: " + minus);
			// rects.add(cur);
		}
		int ans = calcPeri(rects);
		out.printf("%d\n", ans);

		// List<Rectangle> tests = new ArrayList<>();
		// tests.add(new Rectangle(new int[]{0, 0, 1, 1}));
		// tests.add(new Rectangle(new int[]{0, 0, 2, 2}));
		// tests.add(new Rectangle(new int[]{0, 0, 3, 3}));
		// System.out.println("=================");
		// System.out.println("test ans: " + calcPeri(tests));
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
	// static String taskName = "picture";

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
