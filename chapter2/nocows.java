/*
ID: wyli1231
LANG: JAVA
TASK: nocows
*/

import java.io.*;
import java.util.*;

public class nocows {
    static final int MOD = 9901;
    static int[][][] memo;
    static int add(int a, int b)
    {
        int c = a + b;
        if (c >= MOD) c -= MOD;
        return c;
    }
    static int mul(int a, int b)
    {
        return a * b % MOD;
    }
    // no. of possibilities if there are 'cnt' remaining nodes and 
    // we must achieve 'exact' 'level' remaining levels or at most
    static int f(int cnt, int level, int exact)
    {
        if (level <= 10 && cnt >= (1 << level)) return 0; // base case when too many nodes to put
        if (exact == 1 && cnt < level * 2 - 1) return 0; // base case when cannot fulfil 'level'
        if ((exact == 0 || level == 1) && cnt == 1) return 1; // base case when done
        if (memo[cnt][level][exact] != -1) return memo[cnt][level][exact]; // try every possible node count for left subtree
        int ans = 0;
        if (exact == 0) { // at most
            for (int left = 1, right = (cnt - 2); left <= (cnt - 2); left+=2, right-=2) {
                int leftF = f(left, level - 1, 0);
                int rightF = f(right, level - 1, 0);
                ans = add(ans, mul(leftF, rightF));
            }
        }
        else { 
            for (int left = 1, right = (cnt - 2); left <= (cnt - 2); left+=2, right-=2) {
                int leftExact = f(left, level - 1, 1);
                int rightExact = f(right, level - 1, 1);
                int leftAtMost = f(left, level - 2, 0);
                int rightAtMost = f(right, level - 2, 0);
                int choice1 = mul(leftExact, rightAtMost);
                int choice2 = mul(rightExact, leftAtMost);
                int choice3 = mul(leftExact, rightExact);
                ans = add(ans, choice1);
                ans = add(ans, choice2);
                ans = add(ans, choice3);
            }            
        }
        memo[cnt][level][exact] = ans;
        return ans;
    }


    static void solve()
    {
        int n = ni(), k = ni();
        memo = new int[n + 1][k + 1][2];
        for (int i = 0; i < memo.length; i++)
            for (int j = 0; j < memo[i].length; j++)
                Arrays.fill(memo[i][j], -1);
        long ans = f(n, k, 1);
        out.printf("%d\n", ans % MOD);
    }



    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "nocows";
    
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


