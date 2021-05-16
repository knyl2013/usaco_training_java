/*
ID: wyli1231
LANG: JAVA
TASK: rectbarn
*/

import java.io.*;
import java.util.*;

public class rectbarn {
    static int largestRectBrute(int[] heights)
    {
        int n = heights.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int mini = heights[i];
            for (int j = i; j < n; j++) {
                mini = Math.min(mini, heights[j]);
                ans = Math.max(ans, mini * (j - i + 1));
            }
        }
        return ans;
    }
    static int largestRect(int[] heights)
    {
        int n = heights.length;
        int[] widths = new int[n];
        Arrays.fill(widths, 1);
        int ans = 0;
        Stack<Integer> stk = new Stack<>();

        for (int i = 0; i < n; i++) {
            int curHeight = heights[i];
            while (!stk.isEmpty() && heights[stk.peek()] >= curHeight) {
                widths[i] += widths[stk.pop()];
            }
            stk.push(i);
            ans = Math.max(ans, curHeight * widths[i]);
        }

        int cnt = 0;
        while (!stk.isEmpty()) {
            int i = stk.pop(), curHeight = heights[i], curWidth = widths[i];
            cnt += curWidth;
            ans = Math.max(ans, curHeight * cnt);
        }

        return ans;
    }
    static void display(boolean[][] grid)
    {
        int h = grid.length, w = grid[0].length;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.print(grid[i][j] ? 0 : 1);
            }
            System.out.println();
        }
    }
    static int[] randomArr(int n, int lo, int hi)
    {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) ((Math.random() * (hi - lo + 1)) + lo);
        }
        return ans;
    }
    static void solve()
    {
        int[] arr;
        while (true) {
            arr = randomArr(3, 1, 10);
            if (largestRect(arr) != largestRectBrute(arr)) {
                break;
            }
            System.out.println("ok");
        }
        System.out.println(Arrays.toString(arr));
        System.out.println(largestRect(arr) + " " + largestRectBrute(arr));
        // int[] heights = new int[]{3,4,5,7};
        // int[] heights = new int[]{7,5,4,3};
        // System.out.println(largestRectBrute(heights));

        int h = ni(), w = ni(), p = ni();
        boolean[][] grid = new boolean[h][w];
        for (int i = 0; i < p; i++) {
            int r = ni() - 1, c = ni() - 1;
            grid[r][c] = true;
        }
        // display(grid);
        int[] row = new int[w];
        int ans = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (grid[i][j]) {
                    row[j] = 0;
                }
                else {
                    row[j]++;
                }
            }
            ans = Math.max(ans, largestRectBrute(row));
        }
        out.printf("%d\n", ans);
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
    // static String taskName = "rectbarn";
    
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


