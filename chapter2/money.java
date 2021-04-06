/*
ID: wyli1231
LANG: JAVA
TASK: money
*/

import java.io.*;
import java.util.*;

public class money {

    static void solve() {
        int v = ni(), n = ni();
        long[][] dp = new long[n+1][v];
        int[] coins = na(v);
        for (int amt = 0; amt <= n; amt++) {
            for (int last = 0; last < v; last++) {
                if (amt == 0) { dp[amt][last] = 1;} // only 1 way to make 0, empty set
                long ans = 0;
                for (int times = 0; times*coins[last] <= amt; times++) {
                    int nextAmt = amt - times*coins[last];
                    if (last - 1 < 0) {
                        if (nextAmt == 0) ans++;
                    }
                    else {
                        ans += dp[nextAmt][last - 1];
                    }
                }
                dp[amt][last] = ans;
            }
        }
        out.printf("%d\n", dp[n][v-1]);
    }


    // static int[] coins;
    // static long[][] memo;
    // // f(amount, last) := no. of ways to reach 'amount' if the last coin can be used is 'last'
    // static long f(int amt, int last)
    // {
    //     if (amt == 0) return 1; // only 1 way to make 0, empty set
    //     if (last == -1) return 0; // no way to make any amount except 0 with empty set
    //     if (memo[amt][last] != -1) return memo[amt][last];
    //     long ans = 0;
    //     for (int times = 0; times*coins[last] <= amt; times++) {
    //         ans += f(amt-times*coins[last], last - 1);
    //     }
    //     memo[amt][last] = ans;
    //     return ans;
    // }


    // static void solve()
    // {
    //     int v = ni(), n = ni();
    //     memo = new long[n+1][v];
    //     for (int i = 0; i < memo.length; i++)
    //         Arrays.fill(memo[i], -1);
    //     coins = na(v);
    //     long ans = f(n, v - 1);
    //     out.printf("%d\n", ans);
    // }

    
    // static void solve()
    // {
    //     int v = ni(), n = ni();
    //     int[] coins = na(v);
    //     long[][] dp = new long[n + 1][v];
    //     dp[0][0] = 1;
    //     for (int i = 1; i <= n; i++) {
    //         for (int c = 0; c < v; c++) {
    //             for (int ci = 0; ci <= c; ci++) {
    //                 int prev
    //             }
    //             for (int coin : coins) {
    //                 int prev = i - coin;
    //                 if (prev >= 0) {
    //                     dp[i] += dp[prev];
    //                 }
    //             }
    //         }
    //     }
    //     System.out.println(Arrays.toString(dp));
    //     out.printf("%d\n", dp[n]);
    // }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "money";
    
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


