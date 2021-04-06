/*
ID: wyli1231
LANG: JAVA
TASK: zerosum
*/


import java.io.*;
import java.util.*;

public class zerosum {
    static char[] cArr;
    static List<String> ans;
    static boolean ok()
    {
        int ans = 0;
        int val = 0;
        int sign = 1;
        for (char c : cArr) {
            if (c == ' ') continue;
            if (c == '+' || c == '-') {
                ans += val * sign;
                val = 0;
                sign = c == '+' ? 1 : -1;
            }
            else {
                int d = c - '0';
                val = val * 10 + d;
            }
        }
        ans += val * sign;
        return ans == 0;
    }
    static void dfs(int idx)
    {
        if (idx >= cArr.length) {
            if (ok()) {
                ans.add(new String(cArr));
            }
            return;
        }
        cArr[idx] = '+';
        dfs(idx + 2);
        cArr[idx] = '-';
        dfs(idx + 2);
        cArr[idx] = ' ';
        dfs(idx + 2);
    }
    static void solve()
    {
        int n = ni();
        cArr = new char[n*2-1];
        ans = new ArrayList<>();
        for (int i = 0, dig = 1; i < cArr.length; i+=2, dig++) {
            cArr[i] = (char) (dig + '0');
        }
        dfs(1);
        Collections.sort(ans);
        for (String s : ans) {
            out.printf("%s\n", s);
        }
    }


    // static Stack<Character> stk;
    // // stack becomes zero
    // static boolean ok()
    // {
    //     int ans = 0;
    //     int dig = 1;
    //     for (char c : stk) {
    //         if (c == ' ') {
    //             dig *= 10;
    //         }
    //     }
    // }
    // static void backtrack(int remainLen)
    // {
    //     if (remainLen == 0) {
    //         if (ok()) {
    //             int dig = 1;
    //             out.printf("%d", dig);
    //             for (char ch : stk) {
    //                 out.printf("%c%d", ch, dig+1);
    //             }
    //             out.printf("\n");
    //         }
    //     }
    // }
    // static void solve()
    // {
    //     int n = ni();
    //     stk = new Stack<Integer>();
    //     // brute-force all n-1 symbols for +,-,' '
    //     backtrack(n-1);
    // }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "zerosum";
    
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


