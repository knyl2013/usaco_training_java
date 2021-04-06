/*
ID: wyli1231
LANG: JAVA
TASK: stamps
*/

import java.io.*;
import java.util.*;

public class stamps {
    static final int MAX = 10000;
    static void solve()
    {
        int k = ni(), n = ni();
        int[] inputs = na(n);
        // dp[val] := fewest stamps to reach val
        int[] dp = new int[k * MAX + 1];
        for (int i : inputs) dp[i] = 1;
        for (int i = 1; ; i++) {
            if (dp[i] == 1) continue;
            int mini = Integer.MAX_VALUE;
            for (int inp : inputs) {
                int previous = i - inp;
                if (previous >= 0 && dp[previous] + 1 <= k) {
                    mini = Math.min(mini, dp[previous] + 1);
                }
            }
            if (mini == Integer.MAX_VALUE) {
                out.printf("%d\n", i - 1);
                break;
            }
            dp[i] = mini;
        }

    }
    // static boolean[][] memo = new boolean[500001][200];
    // static boolean[] stamp = new boolean[10001];
    // static int[] inputs;
    // static boolean f(int x, int limit)
    // {
    //     if (x < 0 || limit <= 0) return false;
    //     if (stamp[x]) return true;
    //     for (int i : inputs) {
    //         if (f(x - i, limit - 1)) return true;
    //     }
    //     return false;
    // }
    // static void solve()
    // {
    //     int n = ni(), k = ni();
    //     stamp[0] = true;
    //     inputs = new int[k];
    //     for (int i = 0; i < k; i++) {
    //         int val = ni();
    //         stamp[val] = true;
    //         inputs[i] = val;
    //     }
    //     int i = 1;
    //     while (f(i, n)) {
    //         i++;
    //     }
    //     out.printf("%d\n", i - 1);
        
    // }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "stamps";
    
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


