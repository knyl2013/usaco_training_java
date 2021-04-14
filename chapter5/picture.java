/*
ID: wyli1231
LANG: JAVA
TASK: picture
*/

import java.io.*;
import java.util.*;

public class picture {
	static class Rectangle {
		int[] r;
		List<Rectangle> overlaps;
		public Rectangle(int[] r)
		{
			this.r = r;
			overlaps = new ArrayList<>();
		}
		public static int area(Rectangle rec)
		{
			int[] r = rec.r;
			int width = r[2] - r[0], height = r[3] - r[1];
			if (width <= 0 || height <= 0) return -1;
			return width * height;
		}
		public static int peri(Rectangle rec)
		{
			int[] r = rec.r;
			int width = r[2] - r[0], height = r[3] - r[1];
			if (width <= 0 || height <= 0) return -1;
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
		public static boolean equal(Rectangle rec1, Rectangle rec2)
		{
			int[] r1 = rec1.r, r2 = rec2.r;
			for (int i = 0; i < 4; i++) if (r1[i] != r2[i]) return false;
			return true;
		}
	}

    static void solve()
    {
        List<Rectangle> rects = new ArrayList<>();
		int n = ni();
		int ans = 0;
		for (int i = 0; i < n; i++) {
			int x1 = ni(), y1 = ni(), x2 = ni(), y2 = ni();
			Rectangle cur = new Rectangle(new int[]{x1, y1, x2, y2});
			ans += Rectangle.peri(cur);
			rects.add(cur);
		}
		out.printf("%d\n", ans);
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
    
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

