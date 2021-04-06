/*
ID: wyli1231
LANG: JAVA
TASK: fence6
*/


import java.io.*;
import java.util.*;

public class fence6 {
    static final int INF = (int) 1e9;
    static final int MAX = 100;
    static int[] lengths = new int[MAX + 1];
    static List<List<Integer>> starts = new ArrayList<>();
    static List<List<Integer>> ends = new ArrayList<>();
    static boolean[][] startMat = new boolean[MAX + 1][MAX + 1];
    static boolean[][] endMat = new boolean[MAX + 1][MAX + 1];
    static int ans;
    static void helper(int current, int dest, int previous, int[] dist)
    {
        if (current == dest) {
            ans = Math.min(ans, dist[0]);
            return;
        }
        if (dist[0] > ans) {
            return;
        }
        boolean prevOnStart = startMat[current][previous];
        List<Integer> neis;
        boolean[] neiMat;
        if (prevOnStart) {
            neis = ends.get(current);
            neiMat = endMat[current];
        }
        else {
            neis = starts.get(current);
            neiMat = startMat[current];
        }
        dist[0] += lengths[current];
        if (neiMat[dest]) 
            helper(dest, dest, current, dist);
        else {
            for (int nei : neis) {
                helper(nei, dest, current, dist);
            }
        }
        dist[0] -= lengths[current];
    }
    // smallest cycle that starts from i and ends at i
    static void dfs(int i)
    {
        
        // consider starting at one of the starts and ending at one of the starts
        for (int start : starts.get(i)) {
            for (int end : starts.get(i)) {
                if (start == end) continue;
                helper(start, end, i, new int[]{lengths[end]});
            }
        }
        // consider starting at one of the starts and ending at one of the ends
        for (int start : starts.get(i)) {
            for (int end : ends.get(i)) {
                if (start == end) continue;
                helper(start, end, i, new int[]{lengths[end]+lengths[i]});
            }
        }

        // consider starting at one of the ends and ending at one of the ends
        for (int start : ends.get(i)) {
            for (int end : ends.get(i)) {
                if (start == end) continue;
                helper(start, end, i, new int[]{lengths[end]});
            }
        }
    }
    static void solve()
    {
        for (int i = 0; i <= MAX; i++) {
            starts.add(new ArrayList<>());
            ends.add(new ArrayList<>());
        }
        int n = ni();
        for (int i = 0; i < n; i++) {
            int segNo = ni();
            lengths[segNo] = ni();
            int n1 = ni(), n2 = ni();
            for (int j = 0; j < n1; j++) {
                int x = ni();
                startMat[segNo][x] = true;
                starts.get(segNo).add(x);
            }
            for (int j = 0; j < n2; j++) {
                int x = ni();
                endMat[segNo][x] = true;
                ends.get(segNo).add(x);
            }
        }
        ans = INF;
        for (int i = 1; i <= n; i++) {
            dfs(i);
        }

        out.printf("%d\n", ans);
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "fence6";
    
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


