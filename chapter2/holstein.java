/*
ID: wyli1231
LANG: JAVA
TASK: holstein
*/

import java.io.*;
import java.util.*;

public class holstein {
    static int v;
    // tell if a has the smallest bit on than b
    static boolean isSmaller(int a, int b)
    {
        int cntA = Integer.bitCount(a);
        int cntB = Integer.bitCount(b);
        if (cntA != cntB) return cntA < cntB;
        for (int i = 0; i < v; i++) {
            boolean aOn = ((a >> i) & 1) > 0;
            boolean bOn = ((b >> i) & 1) > 0;
            if (aOn && !bOn) return true;
            if (bOn && !aOn) return false;
        }
        throw new RuntimeException("a,b should not be equal");
    }
    static void solve()
    {
        v = ni();
        int[] req = na(v);
        int n = ni();
        int[][] foods = new int[n][];
        int[] current = new int[v];
        int ans = -1;
        for (int i = 0; i < n; i++)
            foods[i] = na(v);
        
        // Brute force all possible sets of requirements
        // i-bit := 1 means taking foods[i], 0 means otherwise
        int m = 1 << n;
        for (int bit = 1; bit < m; bit++) {
            for (int i = 0; i < n; i++) {
                boolean bitOn = ((bit >> i) & 1) > 0;
                if (!bitOn) continue;
                for (int j = 0; j < v; j++) {
                    current[j] += foods[i][j];
                }
            }
            boolean allGood = true;
            // restore current and do checking at the same time
            for (int i = 0; i < v; i++) {
                if (current[i] < req[i]) allGood = false;
                current[i] = 0;
            }
            if (allGood && (ans == -1 || isSmaller(bit, ans))) {
                ans = bit;
            }
        }

        int cnt = Integer.bitCount(ans);
        out.printf("%d ", cnt);
        for (int i = 0, curCnt = 0; i < n; i++) {
            boolean bitOn = ((ans >> i) & 1) > 0;
            if (!bitOn) continue;
            curCnt++;
            if (curCnt == cnt) {
                out.printf("%d\n", (i+1));
            }
            else {
                out.printf("%d ", (i+1));
            }
        }

    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "holstein";
    
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


