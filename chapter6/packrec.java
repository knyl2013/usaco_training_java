/*
ID: wyli1231
LANG: JAVA
TASK: packrec
*/


import java.io.*;
import java.util.*;

public class packrec {
	static int inf = (int) 1e9;
	static int mini = inf;
	static List<int[]> miniRects = new ArrayList<>();
	static boolean[][] appeared = new boolean[201][201];
	static void add(int width, int height)
	{
		int p = Math.min(width, height), q = Math.max(width, height);
		if (p * q > mini) return;
		if (p * q < mini) miniRects = new ArrayList<>();
		mini = p * q;
		if (!appeared[p][q]) {
			appeared[p][q] = true;
			miniRects.add(new int[]{p, q});
		}
	}
	static void shape1(int[] a, int[] b, int[] c, int[] d)
	{
		int width = a[0] + b[0] + c[0] + d[0];
		int height = Math.max(Math.max(a[1], b[1]), Math.max(c[1], d[1]));
		add(width, height);
	}
	static void shape2(int[] a, int[] b, int[] c, int[] d)
	{
		int width = Math.max(a[0]+b[0]+c[0], d[0]);
		int height = Math.max(a[1], Math.max(b[1], c[1])) + d[1];
		add(width, height);
	}
	static void shape3(int[] a, int[] b, int[] c, int[] d)
	{
		int width = Math.max(a[0]+b[0]+c[0], c[0]+d[0]);
		int height = Math.max(Math.max(a[1], b[1])+d[1], c[1]);
		add(width, height);
	}
	static int[] swap(int[] r)
	{
		return new int[]{r[1], r[0]};
	}
	static void shapes(int[] a, int[] b, int[] c, int[] d)
	{
		int k = 1 << 4;
		for (int bit = 0; bit < k; bit++) {
			int[] pa = (bit & 1) == 1 ? a : swap(a);
			int[] pb = ((bit>>1) & 1) == 1 ? b : swap(b);
			int[] pc = ((bit>>2) & 1) == 1 ? c : swap(c);
			int[] pd = ((bit>>3) & 1) == 1 ? d : swap(d);
			shape1(pa, pb, pc, pd); 
			shape2(pa, pb, pc, pd); 
			shape3(pa, pb, pc, pd);
		}
	}
	static void solve()
    {
        int[][] rects = new int[4][];
		for (int i = 0; i < 4; i++) {
			rects[i] = new int[]{ni(), ni()};
		}
		for (int i1 = 0; i1 < 4; i1++) {
			for (int i2 = 0; i2 < 4; i2++) {
				for (int i3 = 0; i3 < 4; i3++) {
					for (int i4 = 0; i4 < 4; i4++) {
						Set<Integer> s = new HashSet<>(Arrays.asList(i1,i2,i3,i4));
						if (s.size() < 4) continue;
						shapes(rects[i1], rects[i2], rects[i3], rects[i4]);
						// System.out.println(i1 + " " + i2 + " " + i3 + " " + i4);
					}
				}
			}
		}
		out.printf("%d\n", mini);
		Collections.sort(miniRects, (x, y) -> x[0] - y[0]);
		for (int[] r : miniRects) {
			out.printf("%d %d\n", r[0], r[1]);
		}
    }
	




    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
	// static String taskName = "packrec";
    
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