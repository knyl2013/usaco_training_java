/*
ID: wyli1231
LANG: JAVA
TASK: cowtour
*/
import java.io.*;
import java.util.*;

public class cowtour {
    static Stack<Integer> stk = new Stack<>();
    static double inf = 1e18;
    static int[][] points;
    static int[][] adjMat;
    static double[][] memo;
    static double[][] dp;
    static int[][] next;
    static int n;
    static double dist(int i, int j)
    {
        long x = (long) points[i][0] - points[j][0];
        long y = (long) points[i][1] - points[j][1];
        x *= x;
        y *= y;
        return Math.sqrt(x + y);
    }
    static double[] shortest(int from)
    {
        double[] distance = new double[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(distance, inf);
        PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(a[0], b[0]));
        pq.offer(new double[] {0, from});
        while (!pq.isEmpty()) {
            double[] p = pq.poll();
            double d = p[0];
            int v = (int) p[1];
            if (visited[v]) continue;
            distance[v] = p[0];
            visited[v] = true;
            for (int nei = 0; nei < n; nei++) {
                if (adjMat[v][nei] == 0 || visited[nei]) 
                    continue;
                pq.offer(new double[]{p[0] + dist(v, nei), nei});
            }
        }
        return distance;
    }
    static void solve()
    {
        n = ni();
        dp = new double[n][n]; // {i, j} -> shortest path from i to j
        double[][] dp2 = new double[n][]; // {i, j} -> shortest path from i to j
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < n; i++) adjList.add(new ArrayList<>());
        int[] degrees = new int[n];
        next = new int[n][n];
        memo = new double[n][n];
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], -1);
        for (int i = 0; i < n; i++)
            Arrays.fill(dp[i], inf);
        points = new int[n][];
        for (int i = 0; i < n; i++) {
            points[i] = na(2);
        }
        adjMat = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjMat[i][j] = nc() - '0';
                if (adjMat[i][j] == 1) {
                    degrees[i]++;
                    dp[i][j] = dist(i, j);
                    next[i][j] = j;
                    adjList.get(i).add(j);
                } else {
                    next[i][j] = -1;
                }
                
            }
        }

        for (int i = 0; i < n; i++) {
            dp[i][i] = 0;
        }
        
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dp[i][j] > dp[i][k] + dp[k][j]) {
                        next[i][j] = next[i][k];
                        dp[i][j] = dp[i][k] + dp[k][j];
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            dp2[i] = shortest(i);
        }        
        int current = 0;
        boolean useDijkstra = true;
        if (useDijkstra) // otherwise use floyd
            dp = dp2; 
        double[] farthest = new double[n];
        double ori = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == inf) continue;
                farthest[i] = Math.max(farthest[i], dp[i][j]);
            }
            ori = Math.max(ori, farthest[i]);
        }
        double connected = inf; // largest field after connected
        int u = -1, v = -1;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (dp[i][j] != inf) continue; // already connected
                double a = farthest[i];
                double b = farthest[j];
                double cur = a + b + dist(i, j);
                if (cur < connected) {
                    u = i;
                    v = j;
                    connected = cur;
                }
            }
        }
        // printing only first 6 decimal places
        double ans = Math.max(ori, connected);
        String s = String.format("%.6f", ans);
        out.printf("%s\n", s);
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "cowtour";
    
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


