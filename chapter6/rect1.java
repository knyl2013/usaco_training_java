/*
ID: wyli1231
LANG: JAVA
TASK: rect1
*/


import java.io.*;
import java.util.*;

public class rect1 {
	static final int LOW_LEFT_X = 0, LOW_LEFT_Y = 1, UP_RIGHT_X = 2, UP_RIGHT_Y = 3, COLOR = 4;
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
    static boolean under(int[] inside, int[] outside)
    {
        boolean lower = outside[LOW_LEFT_X] <= inside[LOW_LEFT_X] && outside[LOW_LEFT_Y] <= inside[LOW_LEFT_Y];
        boolean higher = outside[UP_RIGHT_X] >= inside[UP_RIGHT_X] && outside[UP_RIGHT_Y] >= inside[UP_RIGHT_Y];
        return lower && higher;
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
    static List<int[]> append(int[] r, List<int[]> prevs)
    {
        List<int[]> nexts = new ArrayList<>();
        nexts.add(r);
        for (int[] prev : prevs) {
            int[] o = overlap(r, prev);
            if (o == null) {
                nexts.add(prev);
            }
            else if (!under(prev, o)) {
                
            }
        }
        return nexts;

    }
    static void solve()
    {
        int a = ni(), b = ni(), n = ni();
        int[][] rects = new int[n][5];
        int[] colors = new int[2501];
        List<int[]> prevs = new ArrayList<>();
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < 5; j++)
                rects[i][j] = ni();
        
        for (int i = n-1; i >= 0; i--) {
            int cur = area(rects[i]);
            for (int[] prev : prevs) {
                int[] o = overlap(prev, rects[i]);
                if (o == null) continue;
                cur -= area(o);
            }
            // colors[rects[i][COLOR]] += area(rects[i]) - totArea(overlaps);
            colors[rects[i][COLOR]] += area(rects[i]) - tot;
            prevs = append(rects[i], prevs);
        }
        colors[1] = a*b;
        for (int i = 2; i <= 2500; i++) {
            colors[1] -= colors[i];
        }

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