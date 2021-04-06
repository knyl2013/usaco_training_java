/*
ID: wyli1231
LANG: JAVA
TASK: wormhole
*/

import java.io.*;
import java.util.*;

public class wormhole {
    static int[][] points;
    static int n, ans;
    static int[] pairedTo;
    static boolean hasLoop(int start)
    {
        // Set<Integer> seen = new HashSet<>();
        int current = pairedTo[start];
        // seen.add(start);
        while (current != -1) {
            int next = -1;
            if (current + 1 < n && points[current+1][1] == points[current][1]) {
                next = current + 1;
                if (next == start) return true;
                next = pairedTo[next];
            }
            current = next;
        }
        return false;
    }
    static boolean hasLoop()
    {
        // System.out.println(Arrays.toString(pairedTo));
        for (int start = 0; start < n; start++) {
            if (hasLoop(start)) {
                return true;
            }
        }
        return false;
    }
    static void backtrack(int idx)
    {
        if (idx == n) {
            if (hasLoop()) {
                ans++;
            }
            return;
        }
        if (pairedTo[idx] != -1) {
            backtrack(idx + 1);
            return;
        }
        for (int other = idx + 1; other < n; other++) {
            if (pairedTo[other] != -1) continue;
            pairedTo[other] = idx;
            pairedTo[idx] = other;
            backtrack(idx + 1);
            pairedTo[other] = -1;
        }
        pairedTo[idx] = -1;
    }
    static void solve()
    {
        n = ni();
        points = new int[n][2];
        pairedTo = new int[n];
        for (int i = 0; i < n; i++) {
            pairedTo[i] = -1;
            points[i] = na(2);
        }
        Arrays.sort(points, (a,b) -> a[1] != b[1] ? a[1]-b[1] : a[0]-b[0]);
        // for (int[] row : points) {
        //     System.out.println(Arrays.toString(row));
        // }
        ans = 0;
        backtrack(0);
        out.printf("%d\n", ans);
    }



    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
    // static String taskName = "wormhole";
    
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


