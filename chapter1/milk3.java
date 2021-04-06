/*
ID: wyli1231
LANG: JAVA
TASK: milk3
*/

import java.io.*;
import java.util.*;

public class milk3 {
    static int nax = 21;
    static boolean[][][] seen = new boolean[nax][nax][nax];
    static boolean[] exists = new boolean[nax];
    static int total;
    static int A = 0, B = 1, C = 2;
    static int[] limits = new int[3]; // limits[0] = A, limits[1] = B, limits[2] = C
    
    static void fill(int[] states, int from, int to)
    {
        int maxCanPour = Math.min(states[from], limits[to] - states[to]);
        states[to] += maxCanPour;
        states[from] -= maxCanPour;
    }
    static void dfs(int ai, int bi, int ci)
    {
        if (seen[ai][bi][ci]) return;
        // System.out.println(ai + " " + bi + " " + ci);
        seen[ai][bi][ci] = true;
        if (ai == 0 && !exists[ci]) {
            exists[ci] = true;
            total++;
        }
        
        int[] states = new int[] {ai, bi, ci};
        // pour milk 'from' to 'to'
        for (int from = A; from <= C; from++) {
            for (int to = A; to <= C; to++) {
                if (from == to) continue;
                fill(states, from, to);
                dfs(states[A], states[B], states[C]);
                // restore
                states[A] = ai; states[B] = bi; states[C] = ci;
            }
        }
    }
    static void solve()
    {
        limits = na(3);
        total = 0;
        dfs(0, 0, limits[2]);
        
        for (int i = 0, cnt = 0; i <= 20 && cnt < total; i++) {
            if (!exists[i]) continue;
            cnt++;
            if (cnt == total) {
                out.printf("%d\n", i); // last one create new line
            }
            else {
                out.printf("%d ", i);
            }
        }
    }



    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "milk3";
    
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


