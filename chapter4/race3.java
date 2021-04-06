/*
ID: wyli1231
LANG: JAVA
TASK: race3
*/
import java.io.*;
import java.util.*;

public class race3 {
    static final int INF = (int) 1e5;
    static final int MAX = 51;
    static boolean[][] adj;
    static int[][] dp;
    static int n;
    static boolean hasLoop(int start)
    {
        boolean[] visited = new boolean[n + 1];
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        visited[start] = true;
        while (!q.isEmpty()) {
            int p = q.poll();
            for (int nei = 0; nei <= n; nei++) {
                if (!adj[p][nei]) continue;
                if (nei == start) return true;
                if (!visited[nei]) {
                    q.offer(nei);
                    visited[nei] = true;
                }
            }
        }
        return false;
    }
    // whether 0 can reach n in this graph
    static boolean canReach(boolean[][] graph)
    {
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        boolean[] visited = new boolean[n+1];
        visited[0] = true;
        while (!q.isEmpty()) {
            int p = q.poll();
            if (p == n) return true;
            for (int nei = 0; nei <= n; nei++) {
                if (!graph[p][nei] || visited[nei]) continue;
                if (nei == n) return true;
                visited[nei] = true;
                q.offer(nei);
            }
        }
        return false;
    }
    static boolean isUnavoidable(int ban)
    {
        boolean[][] mat = new boolean[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == ban || j == ban) 
                    mat[i][j] = false;
                else
                    mat[i][j] = adj[i][j];
            }
        }
        return !canReach(mat);
    }
    static List<Integer> getUnavoidables()
    {
        List<Integer> ans = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (isUnavoidable(i)) {
                ans.add(i);
            }
        }
        return ans;
    }
    static void floyd()
    {
        for (int k = 0; k <= n; k++) {
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= n; j++) {
                    if (dp[i][k] + dp[k][j] < dp[i][j]) {
                        dp[i][j] = dp[i][k] + dp[k][j];
                    }
                }
            }
        }
    }
    static boolean isSplit(int idx)
    {
        int[] groups = new int[n+1];
        for (int i = 0; i <= n; i++) {
            if (dp[i][idx] < INF && dp[idx][i] == INF) {
                groups[i] = 1;
            }
            else {
                groups[i] = 0;
            }
        }
        int sum = 0;
        for (int grp : groups) sum += grp;
        if (sum == n+1) return false; // all in group 1
        if (sum == 0) return false; // all in group 0

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == idx || j == idx || !adj[i][j]) continue;
                if (groups[i] != groups[j]) return false;
            }
        }
        return true;
    }
    static void solve()
    {
        adj = new boolean[MAX][MAX];
        int i = 0;
        while (true) {
            int j;
            while ((j = ni()) != -2 && j != -1) {
                adj[i][j] = true;
            }
            if (j == -1) break;
            i++;
        }
        n = i - 1;
        dp = new int[n+1][n+1];
        for (int[] row : dp) Arrays.fill(row, INF);
        for (i = 0; i <= n; i++) dp[i][i] = 0;
        for (i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (adj[i][j]) dp[i][j] = 1;
            }
        }
        floyd();
        List<Integer> unavoidable = getUnavoidables();
        List<Integer> splits = new ArrayList<>();
        for (int cur : unavoidable) {
            if (isSplit(cur)) {
                splits.add(cur);
            }
        }
        if (unavoidable.isEmpty()) {
            out.printf("0\n0\n");
            return;
        }
        out.printf("%d ", unavoidable.size());
        for (i = 0; i < unavoidable.size(); i++) {
            int cur = unavoidable.get(i);
            boolean newLine = i == unavoidable.size() - 1;
            out.printf("%d", cur);
            out.printf(newLine ? "\n" : " ");
        }
        if (splits.isEmpty()) {
            out.printf("0\n");
            return;
        }
        out.printf("%d ", splits.size());
        for (i = 0; i < splits.size(); i++) {
            int cur = splits.get(i);
            boolean newLine = i == splits.size() - 1;
            out.printf("%d", cur);
            out.printf(newLine ? "\n" : " ");
        }
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "race3";
    
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


