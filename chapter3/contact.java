/*
ID: wyli1231
LANG: JAVA
TASK: contact
*/

import java.io.*;
import java.util.*;

public class contact {
    // dp[len][bit] := no. of times bit appeared in this len
    static int[][] dp = new int[13][4096];
    static void solve()
    {
        int a = ni(), b = ni(), n = ni();
        StringBuilder sb = new StringBuilder();
        // read string until empty
        try {
            while (true) {
                sb.append(ns());
            }
        } catch (Exception e) {};
        char[] s = sb.toString().toCharArray();
        // assume last is the last bit of the substring
        for (int last = 0; last < s.length; last++) {
            int val = s[last] == '1' ? 1 : 0;
            dp[1][val]++;
            for (int next = last - 1, len = 2; next >= 0 && len <= b; next--, len++) {
                int cur = s[next] == '1' ? 1 : 0;
                val = val | (cur << (len-1));
                dp[len][val]++;
            }
        }
        List<List<int[]>> bucket = new ArrayList<>();
        for (int i = 0; i <= s.length; i++) bucket.add(new ArrayList<>());
        for (int len = a; len <= b; len++) {
            for (int bit = 0; bit < 4096; bit++) {
                if (dp[len][bit] == 0) continue;
                int freq = dp[len][bit];
                bucket.get(freq).add(new int[]{bit, len});
            }
        }
        for (int i = bucket.size() - 1, cnt = 0; i >= 0 && cnt < n; i--) {
            if (bucket.get(i).isEmpty()) continue;
            cnt++;
            out.printf("%d\n", i);
            List<int[]> lst = bucket.get(i);
            for (int j = 0, numCnt = 1; j < lst.size(); j++) {
                boolean newLine = j == lst.size() - 1 || numCnt == 6;
                if (numCnt == 6) {
                    numCnt = 1;
                } else {
                    numCnt++;
                }
                int bit = lst.get(j)[0], len = lst.get(j)[1];
                StringBuilder t = new StringBuilder();
                while (len-- > 0) {
                    int cur = bit & 1;
                    t.append(cur);
                    if (bit > 0) bit = bit >> 1;
                }
                out.printf("%s", t.reverse().toString());
                out.printf(newLine ? "\n" : " ");
            }
        }
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "contact";
    
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


