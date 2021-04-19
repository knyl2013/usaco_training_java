/*
ID: wyli1231
LANG: JAVA
TASK: picture
*/

import java.io.*;
import java.util.*;

public class picture {
	static class Line implements Comparable<Line> {
		int[] from;
		int[] to;
        int length;
		boolean isVertical;
		public Line(int[] from, int[] to)
		{
			this.from = from;
			this.to = to;
			this.isVertical = from[0] == to[0];
            this.length = this.isVertical ? to[1] - from[1] : to[0] - from[0];
		}
		public boolean overlap(Line other)
		{
			if (this.isVertical != other.isVertical) return false;
            int idx = this.isVertical ? 1 : 0;
            int fix = this.isVertical ? 0 : 1;
            if (other.from[fix] != from[fix]) return false;
			int start = from[idx], end = to[idx], ostart = other.from[idx], oend = other.to[idx];
            boolean inside1 = start <= ostart && end >= oend;
            boolean inside2 = ostart <= start && oend >= end;
            return inside1 || inside2;
		}
		public int compareTo(Line other)
		{
			int idx = this.isVertical ? 1 : 0;
			int start = this.from[idx], end = this.to[idx], ostart = other.from[idx], oend = other.to[idx];
			if (start != ostart) return start - ostart;
			return oend - end;
		}
        public String toString()
        {
            return "from: " + Arrays.toString(from) + ", to: " + Arrays.toString(to);
        }
        public static boolean equal(Line x, Line y)
        {
            for (int i = 0; i < 2; i++) 
                if (x.from[i] != y.from[i] || x.to[i] != y.to[i])
                    return false;
            return true;
        }
        private static int[] getPoint(int fix, int fixVal, int idx, int val)
        {
            int[] ans = new int[2];
            ans[fix] = fixVal;
            ans[idx] = val;
            return ans;
        }
        public static List<Line> nonOverlapLines(List<Line> lines)
        {
            if (lines.isEmpty()) return lines;

            List<Line> ans = new ArrayList<>();
            int idx = lines.get(0).isVertical ? 1 : 0;
            int fix = lines.get(0).isVertical ? 0 : 1;
            int fixVal = lines.get(0).from[fix];
            Collections.sort(lines);
            // System.out.println(lines);
            int i = 0;

            while (i < lines.size()) {
                Line cur = lines.get(i);
                int start = cur.from[idx];
                int end = cur.to[idx];
                while (i < lines.size() && lines.get(i).from[idx] <= end) {
                    end = Math.max(end, lines.get(i).to[idx]);
                    i++;
                }
                // System.out.println(cur);
                ans.add(new Line(getPoint(fix, fixVal, idx, start), getPoint(fix, fixVal, idx, end)));
            }
            List<Line> filtered = new ArrayList<>();
            for (Line l : ans) {
                if (l.from[idx] < l.to[idx])
                    filtered.add(l);
            }
            // System.out.println("nonOvers: " + ans);
            // return ans;
            return filtered;
        }
        public static List<Line> exclude(List<Line> lines, int[] start, int[] end, boolean isVertical)
        {
            int idx = isVertical ? 1 : 0;
            int fix = isVertical ? 0 : 1;
            if (start[idx] >= end[idx]) return new ArrayList<>();
            if (lines.isEmpty()) return Arrays.asList(new Line[]{new Line(start, end)});
            int fixVal = lines.get(0).from[fix];
            List<Line> bans = nonOverlapLines(lines);
            List<Line> ans = new ArrayList<>();
            int prev = start[idx];

            for (Line l : bans) {
                int curStart = l.from[idx], curEnd = l.to[idx];
                if (prev < curStart)
                    ans.add(new Line(getPoint(fix, fixVal, idx, prev), getPoint(fix, fixVal, idx, curStart)));
                prev = curEnd;
            }

            ans.add(new Line(getPoint(fix, fixVal, idx, prev), getPoint(fix, fixVal, idx, end[idx])));

            return ans;
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
        public static int area(Rectangle rec)
        {
            int[] r = rec.r;
            int width = r[2] - r[0], height = r[3] - r[1];
            if (width < 0 || height < 0) return -1;
            return width * height;
        }

		public static Rectangle overlap(Rectangle rec1, Rectangle rec2)
		{
			int[] r1 = rec1.r, r2 = rec2.r;
			int lowLeftX = Math.max(r1[0], r2[0]);
			int lowLeftY = Math.max(r1[1], r2[1]);
			int topRightX = Math.min(r1[2], r2[2]);
			int topRightY = Math.min(r1[3], r2[3]);
            int width = topRightX - lowLeftX, height = topRightY - lowLeftY;
            if (width < 0 || height < 0) return null;
			return new Rectangle(new int[]{lowLeftX, lowLeftY, topRightX, topRightY});
		}
		public static boolean equal(Rectangle r1, Rectangle r2)
		{
            if (r1 == null || r2 == null) return r1 == r2;
			for (int i = 0; i < 4; i++) if (r1.r[i] != r2.r[i]) return false;
			return true;
		}
		public static boolean inside(Rectangle outer, Rectangle inner)
		{
            if (outer == null || inner == null) return outer == inner;
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
		public static Line topLine(Rectangle r)
		{
			return new Line(Rectangle.topLeft(r), Rectangle.topRight(r));
		}
		public static Line bottomLine(Rectangle r)
		{
			return new Line(Rectangle.bottomLeft(r), Rectangle.bottomRight(r));
		}
		public static Line leftLine(Rectangle r)
		{
			return new Line(Rectangle.bottomLeft(r), Rectangle.topLeft(r));
		}
		public static Line rightLine(Rectangle r)
		{
			return new Line(Rectangle.bottomRight(r), Rectangle.topRight(r));
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
    static void putLine(Map<Integer, List<Line>> mp, int key, Line line)
    {
        if (!mp.containsKey(key)) mp.put(key, new ArrayList<>());
        mp.get(key).add(line);
    }
    static int calcTot(Map<Integer, List<Line>> mp)
    {
        int ans = 0;
        for (int key : mp.keySet()) {
            List<Line> lines = mp.get(key);
            for (Line line : Line.nonOverlapLines(lines)) {
                // System.out.println(line.length);
                ans += line.length;
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
			for (int j = 0; j < n; j++) {
                if (i == j || deleted[j]) continue;
				if (Rectangle.equal(rects.get(i), rects.get(j)) || Rectangle.inside(rects.get(i), rects.get(j)))
					deleted[j] = true;
			}
		}
        // if (1+1==2) return 0;
		List<Rectangle> filtered = new ArrayList<>();
		for (int i = 0; i < n; i++) if (!deleted[i]) filtered.add(rects.get(i));
		rects = filtered;
		// Line[] lines = new Line[4];
        Map<Integer, List<Line>> vertis = new HashMap<>(), horis = new HashMap<>();
        // Map<Integer, List<Line>> vertis = new TreeMap<>(), horis = new TreeMap<>();
		for (int i = 0; i < rects.size(); i++) {
			Rectangle ri = rects.get(i);
			Line top = Rectangle.topLine(ri), bottom = Rectangle.bottomLine(ri), left = Rectangle.leftLine(ri), right = Rectangle.rightLine(ri);
			List<Line> banTops = new ArrayList<>(), banBottoms = new ArrayList<>(), banLefts = new ArrayList<>(), banRights = new ArrayList<>();
			for (int j = 0; j < rects.size(); j++) {
                // if (1+1==2) continue;
                if (i == j) continue;
                Rectangle rj = rects.get(j);
				Rectangle o = Rectangle.overlap(ri, rj);
                if (o == null) continue;
                if (Rectangle.peri(o) <= 0) continue;
                // if (area <= 0) continue;
                Line jtop = Rectangle.topLine(rj), jbottom = Rectangle.bottomLine(rj), jleft = Rectangle.leftLine(rj), jright = Rectangle.rightLine(rj);
                Line otop = Rectangle.topLine(o), obottom = Rectangle.bottomLine(o), oleft = Rectangle.leftLine(o), oright = Rectangle.rightLine(o);
                if (otop.length == 0 || oleft.length == 0) {
                    // System.out.println("res");
                    if (otop.overlap(top)) banTops.add(otop);
                    if (obottom.overlap(bottom)) banBottoms.add(obottom);
                    if (oleft.overlap(left)) banLefts.add(oleft);
                    if (oright.overlap(right)) banRights.add(oright);
                }
                else {
                    // System.out.println("else");
                    boolean oi, oj, sameLine;

                    oi = otop.overlap(top);
                    oj = otop.overlap(jtop);
                    sameLine = oi && oj;
                    if (oi && !sameLine) banTops.add(otop);

                    oi = obottom.overlap(bottom);
                    oj = obottom.overlap(jbottom);
                    sameLine = oi && oj;
                    if (oi && !sameLine) banBottoms.add(obottom);

                    oi = oleft.overlap(left);
                    oj = oleft.overlap(jleft);
                    sameLine = oi && oj;
                    if (oi && !sameLine) banLefts.add(oleft);

                    oi = oright.overlap(right);
                    oj = oright.overlap(jright);
                    sameLine = oi && oj;
                    if (oi && !sameLine) banRights.add(oright);
                    // System.out.println("else");
                    // lines[0] = Rectangle.topLine(o); 
                    // lines[1] = Rectangle.bottomLine(o);
                    // lines[2] = Rectangle.leftLine(o);
                    // lines[3] = Rectangle.rightLine(o);
                    // for (Line line : lines) {
                    //     boolean oi, oj, sameLine;

                    //     oi = line.overlap(top);
                    //     oj = line.overlap(jtop);
                    //     sameLine = oi && oj;
                    //     if (oi && !sameLine) banTops.add(line);

                    //     oi = line.overlap(bottom);
                    //     oj = line.overlap(jbottom);
                    //     sameLine = oi && oj;
                    //     if (oi && !sameLine) banBottoms.add(line);

                    //     oi = line.overlap(left);
                    //     oj = line.overlap(jleft);
                    //     sameLine = oi && oj;
                    //     if (oi && !sameLine) banLefts.add(line);

                    //     oi = line.overlap(right);
                    //     oj = line.overlap(jright);
                    //     sameLine = oi && oj;
                    //     if (oi && !sameLine) banRights.add(line);
                    // }
                }
                
                // Rectangle o = cur;
				// int op = Rectangle.peri(o);
				// boolean noOverlap = op <= 0;
				// if (noOverlap) continue;
    //             boolean overlapLine = Rectangle.area(o) == 0;
    //             boolean alignTop = !overlapLine && top.overrlap(Rectangle.topLine(other));
    //             boolean alignBottom = !overlapLine && bottom.overrlap(Rectangle.bottomLine(other));
    //             boolean alignLeft = !overlapLine && top.overrlap(Rectangle.leftLine(other));
    //             boolean alignRight = !overlapLine && top.overrlap(Rectangle.rightLine(other));
    //             if (top.overlap(oTop)) banTops.add(oTop);
    //             if (bottom.overlap(oBottom)) banBottoms.add(oBottom);
    //             if (top.overlap(oBottom)) banTops.add(oBottom);
    //             if (bottom.overlap(oTop)) banBottoms.add(oTop);

    //             if (left.overlap(oLeft)) banLefts.add(oLeft);
    //             if (right.overlap(oRight)) banRights.add(oRight);
    //             if (left.overlap(oRight)) banLefts.add(oRight);
    //             if (right.overlap(oLeft)) banRights.add(oLeft);
                // boolean extendHeight = left.overlap(Rectangle.leftLine(other)) && right.overlap(Rectangle.rightLine(other));
                // boolean extendWidth = top.overlap(Rectangle.topLine(other)) && bottom.overlap(Rectangle.bottomLine(other));
                // System.out.println("test");
				// Line oTop = Rectangle.topLine(o), oBottom = Rectangle.bottomLine(o), oLeft = Rectangle.leftLine(o), oRight = Rectangle.rightLine(o);
                
				// boolean extendHeight = !overlapLine && left.overlap(oLeft) && right.overlap(oRight);
				// boolean extendWidth = !overlapLine && top.overlap(oTop) && bottom.overlap(oBottom);
                
                // boolean overlapLine = Line.equal(oTop, oBottom) || Line.equal(oLeft, oRight);
                // boolean overlapLine = false;
                // System.out.println("overlap: " + o + " " + extendHeight + " " + extendWidth);
                // if ((overlapLine || extendWidth) && top.overlap(oTop)) banTops.add(oTop);
                // if (extendWidth && bottom.overlap(oBottom)) banBottoms.add(oBottom);
                // if (extendWidth && top.overlap(oBottom)) banTops.add(oBottom);
                // if (extendWidth && bottom.overlap(oTop)) banBottoms.add(oTop);

                // if (extendHeight && left.overlap(oLeft)) banLefts.add(oLeft);
                // if (extendHeight && right.overlap(oRight)) banRights.add(oRight);
                // if (extendHeight && left.overlap(oRight)) banLefts.add(oRight);
                // if (extendHeight && right.overlap(oLeft)) banRights.add(oLeft);
			}
            // System.out.println(top.length);
            // System.out.println(bottom.length);
            // System.out.println(left.length);
            // System.out.println(right.length);
            // System.out.println(banTops);
            // System.out.println(banBottoms);
            // System.out.println(banLefts);
            // System.out.println(banRights);
            for (Line line : Line.exclude(banTops, top.from, top.to, false)) 
                putLine(horis, top.from[1], line);
            for (Line line : Line.exclude(banBottoms, bottom.from, bottom.to, false)) 
                putLine(horis, bottom.from[1], line);
            for (Line line : Line.exclude(banLefts, left.from, left.to, true))
                putLine(vertis, left.from[0], line);
            for (Line line : Line.exclude(banRights, right.from, right.to, true))
                putLine(vertis, right.from[0], line);
		}
        int ans = 0;
        // System.out.println("====hori====");
        // for (Integer key : horis.keySet()) {
        //     System.out.println(key + ": " + horis.get(key));
        //     System.out.println("length: " + tot(Line.nonOverlapLines(horis.get(key))));
        //     System.out.println();
        // }
        // System.out.println("====vertis====");
        // for (Integer key : vertis.keySet()) {
        //     System.out.println(key + ": " + vertis.get(key));
        //     System.out.println("length: " + tot(Line.nonOverlapLines(vertis.get(key))));
        //     System.out.println();
        // }
        // System.out.println("===end===");
        ans += calcTot(horis);
        // System.out.println(ans);
        ans += calcTot(vertis);
        return ans;
	}
    static int tot(List<Line> lines){
        int ans = 0;
        for (Line l : lines) ans += l.length;
        return ans;
    }

    static void solve()
    {
        List<Rectangle> rects = new ArrayList<>();
		int n = ni();
		for (int i = 0; i < n; i++) {
			int x1 = ni(), y1 = ni(), x2 = ni(), y2 = ni();
			rects.add(new Rectangle(new int[]{x1, y1, x2, y2}));
		}
		int ans = calcPeri(rects);
        // int ans = 0;
		out.printf("%d\n", ans);
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
	static String taskName = "picture";

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
        System.out.println(G-S+"ms");
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
