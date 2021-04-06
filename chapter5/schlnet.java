/*
ID: wyli1231
LANG: JAVA
TASK: schlnet
*/

import java.io.*;
import java.util.*;

public class schlnet {
    static boolean[][] adj;
    static int n, aAns, bAns;
    static List<Integer> lst;
    // static List<Integer> represents;

    static int dfs(int idx, boolean[] visited)
    {
        visited[idx] = true;
        int cnt = 1;
        for (int i = 0; i < n; i++) {
            if (visited[i] || !adj[idx][i]) continue;
            cnt += dfs(i, visited);
        }
        return cnt;
    }
    static int taskA(boolean[] visited)
    {
        boolean allVisited = true;
        for (boolean b : visited) {
            if (!b) {
                allVisited = false;
                break;
            }
        }
        if (allVisited) return 0;
        int bestIdx = -1;
        int best = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            int cur = dfs(i, visited.clone());
            if (bestIdx == -1 || cur > best) {
                bestIdx = i;
                best = cur;
            }
        }
        lst.add(bestIdx);
        dfs(bestIdx, visited);
        return 1 + taskA(visited);
    }
    static void bHelperUtil(int idx, boolean[] visited)
    {
        visited[idx] = true;
        for (int i = 0; i < n; i++) {
            if (visited[i] || !adj[idx][i]) continue;
            bHelperUtil(i, visited);
            return;
        }
        adj[idx][lst.get(0)] = true;
    }
    static void bHelper(int idx)
    {
        boolean[] visited = new boolean[n];
        bHelperUtil(idx, visited);
        bAns++;
    }
    static int taskB()
    {
        for (int i = 1; i < lst.size(); i++) {
            adj[lst.get(i - 1)][lst.get(i)] = true;
        }
        bAns = aAns - 1;
        // if (lst.size() > 1) {
        //     adj[lst.get(lst.size() - 1)][lst.get(0)] = true;
        //     bAns++;
        // }
        // System.out.println(lst);
        for (int i = 0; i < n; i++) {
            boolean[] current = new boolean[n];
            int cnt = dfs(i, current);
            if (cnt == n) continue;
            bHelper(i);
        }
        return bAns;
    }
    static void solve()
    {
        n = ni();
        adj = new boolean[n][n];
        lst = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int inp;
            while ((inp = ni()) != 0) {
                adj[i][inp - 1] = true;
            }
        }
        aAns = taskA(new boolean[n]);
        bAns = taskB();
        out.printf("%d\n%d\n", aAns, bAns);
    }




    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
    // static String taskName = "schlnet";
    
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


