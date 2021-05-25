/*
ID: wyli1231
LANG: JAVA
TASK: rect1
*/


import java.io.*;
import java.util.*;

public class rect1 {
	static final int LOW_LEFT_X = 0, LOW_LEFT_Y = 1, UP_RIGHT_X = 2, UP_RIGHT_Y = 3, COLOR = 4;
	static final int UP = 0, LEFT = 1, RIGHT = 2, LOW = 3, VERTICAL = 4, HORIZONTAL = 5, INSIDE = 6, OUTSIDE = 7;
	static int area(int[] r)
    {
        int width = r[UP_RIGHT_X] - r[LOW_LEFT_X];
        int height = r[UP_RIGHT_Y] - r[LOW_LEFT_Y];
        if (width < 0 || height < 0) return -1;
        return width * height;
    }
    static int[] overlap(int[] r1, int[] r2)
    {
        int lowLeftX = Math.max(r1[LOW_LEFT_X], r2[LOW_LEFT_X]);
        int lowLeftY = Math.max(r1[LOW_LEFT_Y], r2[LOW_LEFT_Y]);
        int upRightX = Math.min(r1[UP_RIGHT_X], r2[UP_RIGHT_X]);
        int upRightY = Math.min(r1[UP_RIGHT_Y], r2[UP_RIGHT_Y]);
        int[] ans = new int[]{lowLeftX, lowLeftY, upRightX, upRightY};
        if (area(ans) <= 0) return null;
        return ans;
    }
    static boolean isInside(int[] inside, int[] outside)
    {
        boolean lower = outside[LOW_LEFT_X] <= inside[LOW_LEFT_X] && outside[LOW_LEFT_Y] <= inside[LOW_LEFT_Y];
        boolean higher = outside[UP_RIGHT_X] >= inside[UP_RIGHT_X] && outside[UP_RIGHT_Y] >= inside[UP_RIGHT_Y];
        return lower && higher;
    }
	static int[] upRight(int[] r)
	{
		return new int[]{r[UP_RIGHT_X], r[UP_RIGHT_Y]};
	}
	static int[] lowLeft(int[] r)
	{
		return new int[]{r[LOW_LEFT_X], r[LOW_LEFT_Y]};
	}
	static int[] lowRight(int[] r)
	{
		return new int[]{r[UP_RIGHT_X], r[LOW_LEFT_Y]};
	}
	static int[] upLeft(int[] r)
	{
		return new int[]{r[LOW_LEFT_X], r[UP_RIGHT_Y]};
	}
	static boolean equal(int[] a, int[] b)
	{
		if (a.length != b.length) throw new IllegalArgumentException();
		for (int i = 0; i < a.length; i++)
			if (a[i] != b[i])
				return false;
		return true;
	}
	
    static int totArea(List<int[]> rects)
    {
        int ans = 0, n = rects.size();

        for (int i = 0; i < n; i++) {
            List<int[]> overlaps = new ArrayList<>();
            for (int j = i + 1; j < n; j++) {
                int[] o = overlap(rects.get(i), rects.get(j));
                if (o == null) continue;
                overlaps.add(o);
            }
            ans += area(rects.get(i)) - totArea(overlaps);
        }

        return ans;   
    }
	static int getStatus(int[] r1, int[] r2) // if ans = 0(UP), it means r1 is on the upper of r2
	{
		if (isInside(r1, r2)) return INSIDE; // r2 is the ouside rectangle, so r1 is the inside
		if (isInside(r2, r1)) return OUTSIDE; // r2 is the inside rectangle, so r1 is the outside
		int[] o = overlap(r1, r2);
		boolean up = o[UP_RIGHT_Y] == r2[UP_RIGHT_Y];
		boolean low = o[LOW_LEFT_Y] == r2[LOW_LEFT_Y];
		boolean left = o[LOW_LEFT_X] == r2[LOW_LEFT_X];
		boolean right = o[UP_RIGHT_X] == r2[UP_RIGHT_X];
		System.out.println(up + " " + low + " " + left + " " + right);
		if (up && low) return VERTICAL;
		if (left && right) return HORIZONTAL;
		if (left) return LEFT;
		if (right) return RIGHT;
		if (up) return UP;
		if (low) return LOW;
		
		// boolean up = r1[UP_RIGHT_Y] > r2[UP_RIGHT_Y], low = r1[LOW_LEFT_Y] < r2[LOW_LEFT_Y];
		// boolean left = r1[LOW_LEFT_X] < r2[LOW_LEFT_X], right = r1[UP_RIGHT_X] > r2[UP_RIGHT_X];
		// if (up && low && !left && !right) return VERTICAL;
		// if (left && right && !up && !low) return HORIZONTAL;
		// if (up) return UP;
		// if (left) return LEFT;
		// if (low) return LOW;
		// if (right) return RIGHT;
		// System.out.println(Arrays.toString(r1));
		// System.out.println(Arrays.toString(r2));
		throw new IllegalArgumentException();
	}
	static List<int[]> cut(int[] rect, int[] cutter)
	{
		int[] o = overlap(rect, cutter);
		if (o == null) return Arrays.asList(rect);
        List<int[]> ans = new ArrayList<>();
		int[][] rects = new int[5][4];
		int[] left = rects[0], right = rects[1], low = rects[2], up = rects[3], middle = rects[4];
		int code = getStatus(rect, cutter);
		System.out.println("code: " + code);
		if (code == INSIDE) return ans;
		if (code == HORIZONTAL) {
			left[LOW_LEFT_X] = rect[LOW_LEFT_X];
			left[LOW_LEFT_Y] = rect[LOW_LEFT_Y];
			left[UP_RIGHT_X] = o[LOW_LEFT_X];
			left[UP_RIGHT_Y] = o[UP_RIGHT_Y];
			right[LOW_LEFT_X] = o[UP_RIGHT_X];
			right[LOW_LEFT_Y] = rect[LOW_LEFT_Y];
			right[UP_RIGHT_X] = rect[UP_RIGHT_X];
			right[UP_RIGHT_Y] = rect[UP_RIGHT_Y];
		}
		else if (code == VERTICAL) {
			up[LOW_LEFT_X] = rect[LOW_LEFT_X];
			up[LOW_LEFT_Y] = o[UP_RIGHT_Y];
			up[UP_RIGHT_X] = rect[UP_RIGHT_X];
			up[UP_RIGHT_Y] = rect[UP_RIGHT_Y];
			low[LOW_LEFT_X] = rect[LOW_LEFT_X];
			low[LOW_LEFT_Y] = rect[LOW_LEFT_Y];
			low[UP_RIGHT_X] = o[UP_RIGHT_X];
			low[UP_RIGHT_Y] = o[LOW_LEFT_Y];
		}
		else if (code == UP) {
			System.out.println("UP");
            left[LOW_LEFT_X] = rect[LOW_LEFT_X];
            left[LOW_LEFT_Y] = rect[LOW_LEFT_Y];
            left[UP_RIGHT_X] = o[LOW_LEFT_X];
            left[UP_RIGHT_Y] = rect[UP_RIGHT_Y];
            middle[LOW_LEFT_X] = o[LOW_LEFT_X];
            middle[LOW_LEFT_Y] = o[UP_RIGHT_Y];
            middle[UP_RIGHT_X] = o[UP_RIGHT_X];
            middle[UP_RIGHT_Y] = rect[UP_RIGHT_Y];
            right[LOW_LEFT_X] = o[UP_RIGHT_X];
            right[LOW_LEFT_Y] = rect[LOW_LEFT_Y];
            right[UP_RIGHT_X] = rect[UP_RIGHT_X];
            right[UP_RIGHT_Y] = rect[UP_RIGHT_Y];
		}
		else if (code == LOW) {
			System.out.println("LOW");
			left[LOW_LEFT_X] = rect[LOW_LEFT_X];
			left[LOW_LEFT_Y] = rect[LOW_LEFT_Y];
			left[UP_RIGHT_X] = o[LOW_LEFT_X];
			left[UP_RIGHT_Y] = o[LOW_LEFT_Y];
			middle[LOW_LEFT_X] = o[LOW_LEFT_X];
			middle[LOW_LEFT_Y] = rect[LOW_LEFT_Y];
			middle[UP_RIGHT_X] = o[UP_RIGHT_X];
			middle[UP_RIGHT_Y] = o[LOW_LEFT_Y];
			right[LOW_LEFT_X] = o[UP_RIGHT_X];
			right[LOW_LEFT_Y] = rect[LOW_LEFT_Y];
			right[UP_RIGHT_X] = rect[UP_RIGHT_X];
			right[UP_RIGHT_Y] = rect[UP_RIGHT_Y];
		}
		else if (code == RIGHT) {
			up[LOW_LEFT_X] = rect[LOW_LEFT_X];
			up[LOW_LEFT_Y] = o[UP_RIGHT_Y];
			up[UP_RIGHT_X] = rect[UP_RIGHT_X];
			up[UP_RIGHT_Y] = rect[UP_RIGHT_Y];
			middle[LOW_LEFT_X] = o[UP_RIGHT_X];
			middle[LOW_LEFT_Y] = o[LOW_LEFT_Y];
			middle[UP_RIGHT_X] = rect[UP_RIGHT_X];
			middle[UP_RIGHT_Y] = o[UP_RIGHT_Y];
			low[LOW_LEFT_X] = rect[LOW_LEFT_X];
			low[LOW_LEFT_Y] = rect[LOW_LEFT_Y];
			low[UP_RIGHT_X] = rect[UP_RIGHT_X];
			low[UP_RIGHT_Y] = o[LOW_LEFT_Y];
		}
		else if (code == LEFT) {
			up[LOW_LEFT_X] = rect[LOW_LEFT_X];
			up[LOW_LEFT_Y] = o[UP_RIGHT_Y];
			up[UP_RIGHT_X] = rect[UP_RIGHT_X];
			up[UP_RIGHT_Y] = rect[UP_RIGHT_Y];
			middle[LOW_LEFT_X] = rect[LOW_LEFT_X];
			middle[LOW_LEFT_Y] = o[LOW_LEFT_Y];
			middle[UP_RIGHT_X] = o[LOW_LEFT_X];
			middle[UP_RIGHT_Y] = o[UP_RIGHT_Y];
			low[LOW_LEFT_X] = rect[LOW_LEFT_X];
			low[LOW_LEFT_Y] = rect[LOW_LEFT_Y];
			low[UP_RIGHT_X] = rect[UP_RIGHT_X];
			low[UP_RIGHT_Y] = o[LOW_LEFT_Y];
		}
		else if (code == OUTSIDE) {
			up[LOW_LEFT_X] = rect[LOW_LEFT_X];
			up[LOW_LEFT_Y] = o[UP_RIGHT_Y];
			up[UP_RIGHT_X] = rect[UP_RIGHT_X];
			up[UP_RIGHT_Y] = rect[UP_RIGHT_Y];
			left[LOW_LEFT_X] = rect[LOW_LEFT_X];
			left[LOW_LEFT_Y] = o[LOW_LEFT_Y];
			left[UP_RIGHT_X] = o[LOW_LEFT_X];
			left[UP_RIGHT_Y] = o[UP_RIGHT_Y];
			right[LOW_LEFT_X] = o[UP_RIGHT_X];
			right[LOW_LEFT_Y] = o[LOW_LEFT_Y];
			right[UP_RIGHT_X] = rect[UP_RIGHT_X];
			right[UP_RIGHT_Y] = o[UP_RIGHT_Y];
			low[LOW_LEFT_X] = rect[LOW_LEFT_X];
			low[LOW_LEFT_Y] = rect[LOW_LEFT_Y];
			low[UP_RIGHT_X] = rect[UP_RIGHT_X];
			low[UP_RIGHT_Y] = o[LOW_LEFT_Y];
		}
		else throw new IllegalArgumentException();
		
		for (int[] r : rects) {
			if (area(r) > 0) ans.add(r);
		}
		
		return ans;
	}
    static List<int[]> append(int[] r, List<int[]> prevs)
    {
        List<int[]> nexts = new ArrayList<>();
        nexts.add(r);
		
        for (int[] prev : prevs) {
			List<int[]> cuts = cut(prev, r);
			for (int[] c : cuts) {
				nexts.add(c);
			}
        }
        return nexts;

    }
	static int[] answer(int[][] rects, int n, int a, int b)
	{
		int[] colors = new int[2501];
        List<int[]> prevs = new ArrayList<>();
		for (int i = n-1; i >= 0; i--) {
            int cur = area(rects[i]);
            for (int[] prev : prevs) {
                int[] o = overlap(prev, rects[i]);
                if (o == null) continue;
                cur -= area(o);
            }
            colors[rects[i][COLOR]] += cur;
            prevs = append(rects[i], prevs);
			for (int[] prev : prevs) {
				System.out.println(Arrays.toString(prev));
			}
			System.out.println();
		
        }
        colors[1] = a*b;
        for (int i = 2; i <= 2500; i++) {
            colors[1] -= colors[i];
        }
		for (int[] prev : prevs) {
			System.out.println(Arrays.toString(prev));
		}
		System.out.println();
		return colors;
	}
	static int[] brute(int[][] rects, int n, int a, int b)
	{
		int[] colors = new int[2501];
		for (int i = n-1; i >= 0; i--) {
			List<int[]> overlaps = new ArrayList<>();
			for (int j = i+1; j < n; j++) {
				int[] prev = rects[j];
                int[] o = overlap(prev, rects[i]);
                if (o == null) continue;
				overlaps.add(o);
            }
            colors[rects[i][COLOR]] += area(rects[i]) - totArea(overlaps);
        }
        colors[1] = a*b;
        for (int i = 2; i <= 2500; i++) {
            colors[1] -= colors[i];
        }
		return colors;
	}
	static int[] randomArr(int n, int lo, int hi)
    {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) ((Math.random() * (hi - lo + 1)) + lo);
        }
        return ans;
    }
	static void testcase()
	{
		outer:
		while (true) {
			int n = randomArr(1, 1, 3)[0];
			// int a = randomArr(1, 1, 5)[0];
			// int b = randomArr(1, 1, 5)[0];
			int a = 50;
			int b = 50;
			int[][] rects = new int[n][5];
			for (int i = 0; i < n; i++) {
				rects[i][LOW_LEFT_X] = randomArr(1, 1, 5)[0];
				rects[i][LOW_LEFT_Y] = randomArr(1, 1, 5)[0];
				rects[i][UP_RIGHT_X] = randomArr(1, rects[i][LOW_LEFT_X], 5)[0];
				rects[i][UP_RIGHT_Y] = randomArr(1, rects[i][LOW_LEFT_Y], 5)[0];
				rects[i][COLOR] = randomArr(1, 1, 5)[0];
			}
			int[] expect = brute(rects, n, a, b);
			int[] actual = answer(rects, n, a, b);
			for (int i = 0; i < expect.length; i++) {
				if (expect[i] != actual[i]) {
					System.out.println(a + " " + b + " " + n);
					for (int[] r : rects) {
						for (int j = 0; j < r.length; j++) {
							System.out.print(r[j]);
							System.out.print(j==r.length-1?"\n":" ");
						}
					}
					System.out.println();
					System.out.print("expect: \n");
					display(expect);
					System.out.print("actual: \n");
					display(actual);
					break outer;
				}
			}
			System.out.println("ok");
		}
	}
	static void display(int[] colors)
	{
		for (int i = 1; i <= 2500; i++) {
            if (colors[i] == 0) continue;
            System.out.printf("%d %d\n", i, colors[i]);
        }
	}
    static void solve()
    {
		boolean debug = false;
		if (debug) {
			testcase();
			return;
		}
        int a = ni(), b = ni(), n = ni();
        int[][] rects = new int[n][5];
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < 5; j++)
                rects[i][j] = ni();
        
		int[] colors = answer(rects, n, a, b);
		for (int i = 1; i <= 2500; i++) {
            if (colors[i] == 0) continue;
            out.printf("%d %d\n", i, colors[i]);
        }
    }
	




    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
	// static String taskName = "rect1";
    
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