/*
ID: wyli1231
LANG: JAVA
TASK: picture
*/

import java.io.*;
import java.util.*;

public class picture {
	static class Line implements Comparable<Line> {
		int start, end, cmp;
		boolean isOpen;
		public Line(int start, int end, int cmp, boolean isOpen) 
		{
			this.start = start;
			this.end = end;
			this.cmp = cmp;
			this.isOpen = isOpen;
		}
		public int compareTo(Line other)
		{
			if (this.cmp != other.cmp)
				return this.cmp - other.cmp;
			return (this.isOpen ? 0 : 1) - (other.isOpen ? 0 : 1);
		}
	}
	static int calc(TreeMap<Integer, Integer> mp)
	{
		int ans = 0;
		int run = 0;
		int start = -1;
		for (int key : mp.keySet()) {
			int cur = mp.get(key);
			if (cur == 0) continue;
			run += cur;
			if (cur > 0) {
				if (start == -1)
					start = key;
			}
			else {
				if (run == 0) {
					ans += key - start;
					start = -1;
				}
			}
		}
		return ans;
	}
	static int f(List<Line> lines)
	{
		Collections.sort(lines);
		TreeMap<Integer, Integer> mp = new TreeMap<>();
		int ans = 0, before = 0;
		for (Line line : lines) {
			if (line.isOpen) {
				mp.put(line.start, mp.getOrDefault(line.start, 0) + 1);
				mp.put(line.end, mp.getOrDefault(line.end, 0) - 1);
				int after = calc(mp);
				ans += after - before;
				before = after;
			}
			else { // line is close
				mp.put(line.start, mp.getOrDefault(line.start, 0) - 1);
				mp.put(line.end, mp.getOrDefault(line.end, 0) + 1);
				int next = calc(mp);
				ans += before - next;
				before = next;
			}
			if (mp.get(line.end) == 0) mp.remove(line.end);
			if (mp.get(line.start) == 0) mp.remove(line.start);
		}
		return ans;
	}
    static void solve()
    {
		int n = ni();
        int[][] rects = new int[n][4];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < 4; j++)
				rects[i][j] = ni();
		List<Line> horizontals = new ArrayList<>(), verticals = new ArrayList<>();
		for (int[] r : rects) {
			Line bottomHori = new Line(r[0], r[2], r[1], true);
			Line topHori = new Line(r[0], r[2], r[3], false);
			Line leftVerti = new Line(r[1], r[3], r[0], true);
			Line rightVerti = new Line(r[1], r[3], r[2], false);
			horizontals.add(topHori);
			horizontals.add(bottomHori);
			verticals.add(leftVerti);
			verticals.add(rightVerti);
		}
		out.printf("%d\n", f(horizontals) + f(verticals));
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
        // tr(G-S+"ms");
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

