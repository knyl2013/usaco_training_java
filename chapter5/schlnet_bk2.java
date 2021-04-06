/*
ID: wyli1231
LANG: JAVA
TASK: schlnet
*/

import java.io.*;
import java.util.*;

public class schlnet_bk2 {
    static boolean[][] adj;
    static int n;
    static boolean debug = false;
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
    static int dfsInv(int idx, boolean[] visited)
    {
        visited[idx] = true;
        int cnt = 1;
        for (int i = 0; i < n; i++) {
            if (visited[i] || !adj[i][idx]) continue;
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
        dfs(bestIdx, visited);
        return 1 + taskA(visited);
    }
    static boolean[][] clone(boolean[][] mat)
    {
        boolean[][] ans = new boolean[mat.length][];
        for (int i = 0; i < mat.length; i++)
            ans[i] = mat[i].clone();
        return ans;
    }
    static int count(boolean[][] mat)
    {
        int ans = 0;
        boolean[][] tmp = adj;
        adj = mat;
        for (int i = 0; i < n; i++) {
            ans += dfs(i, new boolean[n]);
            if (debug)
                System.out.println(i + ": " + dfs(i, new boolean[n]));
        }
        adj = tmp;
        return ans;
    }
    static int count2(boolean[][] mat, int i, int j)
    {
        boolean[][] tmp = adj;
        adj = mat;
        int before = dfs(i, new boolean[n]);
        int beforeInv = dfsInv(i, new boolean[n]);
        mat[i][j] = true;
        int after = dfs(i, new boolean[n]);
        int afterInv = dfsInv(i, new boolean[n]);
        int score = (after - before) * 10000 + (afterInv - beforeInv);
        mat[i][j] = false;
        adj = tmp;
        return score;
    }
    static int taskB(boolean[][] mat)
    {
        int cnt = count(mat);
        if (cnt == n * n) return 0;
        int bestI = -1, bestJ = -1;
        int best = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j]) continue;
                int cur = count2(mat, i, j);
                if (bestI == -1 || cur > best) {
                    bestI = i;
                    bestJ = j;
                    best = cur;
                }
            }
        }
        mat[bestI][bestJ] = true;
        return 1 + taskB(mat);
    }
    static void solve()
    {
        n = ni();
        adj = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            int inp;
            while ((inp = ni()) != 0) {
                adj[i][inp - 1] = true;
            }
        }
        
        // System.out.println(dp.get(0));
        int aAns = taskA(new boolean[n]);
        int bAns = taskB(clone(adj));
        // System.out.println(aAns);
        // System.out.println(bAns);
        out.printf("%d\n%d\n", aAns, bAns);

        // connect(1,6);
        // connect(6,7);
        // connect(7,8);
        // connect(8,9);
        // connect(9,10);
        // connect(10,1);
        // connect(3,4);
        // connect(4,1);
        // System.out.println(count(adj));
    }

    static void connect(int i, int j)
    {
        adj[i-1][j-1] = true;
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


