/*
ID: wyli1231
LANG: JAVA
TASK: vans
*/

import java.io.*;
import java.util.*;

public class vans {
    static int cnt, n;
    static int[][] dirs = new int[][] {
        new int[]{1, 0},
        new int[]{-1, 0},
        new int[]{0, 1},
        new int[]{0, -1}
    };
    static boolean[][] visited;
    static void dfs(int x, int y, int vcnt)
    {
        if (x == 0 && y == 0 && visited[x][y]) {
            if (vcnt == n * 4) cnt++;
            return;
        }
        if (visited[x][y]) return;
        visited[x][y] = true;
        for (int[] dir : dirs) {
            int dx = x + dir[0], dy = y + dir[1];
            boolean out = dx < 0 || dy < 0 || dx >= 4 || dy >= n;
            if (out) continue;
            dfs(dx, dy, vcnt+1);
        }
        visited[x][y] = false;
    }
    static int brute()
    {
        visited = new boolean[4][n];
        cnt = 0;
        dfs(0, 0, 0);
        return cnt;
    }
    static int f(int x1, int y1, int x2, int y2)
    {
        if (x1 == 3 && y1 == n-1 && x2 == 3 && y2 == n-1) return 1;
        return -1;
    }
    static int h(int n)
    {
        if (n == 0) return 1;
        if (n == 1) return 0;
        if (n == 2) return 3;
        return (n - 1) * 2;
    }
    static int f(int n)
    {
        if (n <= 2) return h(n);
        int ans = 0;
        for (int i = 2; i <= n; i++) {
            ans += h(i) * f(n-i);
        }
        return ans;
    }
    static int caller()
    {
        int ans = 0;
        n -= 2;
        for (int left = 0; left <= n; left++) {
            for (int right = 0; right <= n; right++) {
                if (left+right > n) continue;
                ans += f(n-left-right) * 2;
                System.out.println(ans + " " + left + " " + right + " " + n);
            }
        }

        return ans;
    }
    static void solve()
    {
        n = ni();
        // out.printf("%d\n", brute());
        out.printf("%d\n", caller());
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


