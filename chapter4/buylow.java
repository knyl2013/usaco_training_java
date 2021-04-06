/*
ID: wyli1231
LANG: JAVA
TASK: buylow
*/

import java.io.*;
import java.util.*;

public class buylow {
    static class BigNum {
        static String add(String a, String b)
        {
            StringBuilder sb = new StringBuilder();
            int i = a.length() - 1, j = b.length() - 1;
            int carry = 0;
            while (i >= 0 || j >= 0 || carry > 0) {
                int av = i >= 0 ? a.charAt(i--) - '0' : 0;
                int bv = j >= 0 ? b.charAt(j--) - '0' : 0;
                int val = av + bv + carry;
                carry = val / 10;
                val %= 10;
                sb.append(val);
            }
            return sb.reverse().toString();
        }
    }
    
    static void solve()
    {
        int n = ni();
        int[] a = na(n);
        int[] dp = new int[n]; // dp[i] := longest decreasing subsequence ending at i
        String[] counts = new String[n];
        for (int i = 0; i < n; i++) counts[i] = "0";
        int ans = 0;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (a[j] > a[i])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            ans = Math.max(ans, dp[i]);
        }
        
        Map<Integer, Integer> rightmost = new HashMap<>();
        for (int i = 0; i < n; i++) {
            rightmost.put(a[i], i);
        }
        for (int i = 0; i < n; i++) {
            if (dp[i] == 1) {
                counts[i] = "1";
                continue;
            }   
            Map<Integer, Integer> current = new HashMap<>();
            for (int j = 0; j < i; j++) {
                if (a[j] > a[i] && dp[j] == dp[i] - 1) {
                    current.put(a[j], j);
                }
            }
            for (int key : current.keySet()) {
                counts[i] = BigNum.add(counts[i], counts[current.get(key)]);
            }
        }
        String cnt = "0";
        for (int i = 0; i < n; i++) {
            if (dp[i] != ans || i != rightmost.get(a[i])) continue;
            cnt = BigNum.add(cnt, counts[i]);
        }
        out.printf("%d %s\n", ans, cnt);
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "buylow";
    
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


