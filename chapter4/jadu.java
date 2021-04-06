/*
https://www.hackerearth.com/practice/algorithms/graphs/maximum-flow/practice-problems/algorithm/jadu-and-spaceship/
*/
import java.io.*;
import java.util.*;

public class jadu {
    static int M;
    static Map<Long, Boolean> memo;
    static boolean isPrime(long x)
    {
        if (x <= 1) return false;
        if (x == 2) return true;
        if (x % 2 == 0) return false;
        // if (memo.containsKey(x)) return memo.get(x);
        for (long i = 3; i*i <= x; i += 2) {
            if (x % i == 0) {
                memo.put(x, false);
                return false;
            }
        }
        // memo.put(x, true);
        return true;
    }
    static List<Long> divisors(long x)
    {
        List<Long> ans = new ArrayList<>();

        for (long i = 2; i*i <= x; i++) {
            if (x % i != 0 || !isPrime(i)) continue;
            ans.add(i);
            if (i*i != x && x/i <= M && isPrime(x/i))
                ans.add(x/i);
        }

        return ans;
    }
    static void solve()
    {
        int N = ni();
        M = ni();
        int[] arr = na(N);
        memo = new HashMap<>();
        Map<Integer, Integer> mp1 = new HashMap<>(), mp2 = new HashMap<>();
        List<int[]> edges = new ArrayList<>();
        for (int x : arr) {
            if (!mp1.containsKey(x)) mp1.put(x, mp1.size());
            int xi = mp1.get(x);
            for (long d : divisors(x)) {
                int di = (int) d;
                if (!mp2.containsKey(di)) mp2.put(di, mp2.size() + N);
                int dii = mp2.get(di);
                edges.add(new int[]{xi, dii});
            }
        }
        // System.out.println(mp1.size() + " " + mp2.size());
        int k = N + mp2.size() + 20;
        int source = mp1.size() + mp2.size() + 1, sink = source + 1;
        long[][] c = new long[k][k];
        for (int[] e : edges) {
            // System.out.println(e[0] + " " + e[1]);
            c[e[0]][e[1]] = 1;
        }
        for (int key : mp1.keySet()) {
            c[source][mp1.get(key)] = MaxFlow.INF;
        }
        for (int key : mp2.keySet()) {
            c[mp2.get(key)][sink] = 1;
        }
        // for (long[] row : c) {
        //     System.out.println(Arrays.toString(row));
        // }
        MaxFlow flow = new MaxFlow();
        long ans = flow.maxFlow(source, sink, c);
        out.printf("%d\n", ans);
        // System.out.println(mp1);
        // System.out.println(mp2);
        
        // int max = arr[0];
        // for (int x : arr) {
        //     max = Math.max(max, x);
        // }

        // boolean[] flags = new boolean[max + 1];
        // for (int i = )

        // int k = N * 2 + 1;
        // long[][] c = new long[k][k];
        // /*
        // 0..N := no. in array
        // N..2N-1 := no. in 
        // */
        // MaxFlow flow = new MaxFlow();
        // long ans = flow.maxFlow(source, sink, c);
    }




    static class MaxFlow {
        static final long INF = (long) 1e16;
        private long[][] copy(long[][] mat)
        {
            long[][] ans = new long[mat.length][mat[0].length];
            for (int i = 0; i < mat.length; i++)
                for (int j = 0; j < mat[0].length; j++)
                    ans[i][j] = mat[i][j];
            return ans;
        }
        /*  minCut returns:
            long[0] = {min_cut}, 
            long[1] = {edge1_from, edge1_to, edge2_from, edge2_to, ...} */
        public long[][] minCut(int source, int sink, long[][] capMat)
        {
            int n = capMat.length;
            long[][] ori = copy(capMat);
            long cut = this.maxFlow(source, sink, capMat);
            long[][] res = new long[2][];
            boolean[] reachable = new boolean[capMat.length];
            Queue<Integer> q = new LinkedList<>();
            // use bfs to mark all reachable vertices
            q.offer(source);
            while (!q.isEmpty()) {
                int p = q.poll();
                if (reachable[p]) continue;
                reachable[p] = true;
                for (int nei = 0; nei < n; nei++) {
                    if (capMat[p][nei] == 0 || reachable[nei]) continue;
                    q.offer(nei);
                    reachable[nei] = true;
                }
            }
            res[0] = new long[] {cut};
            List<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (ori[i][j] == 0) continue;
                    /* if some edges that one hand is reachable but another isn't, 
                        then it must be one of the cut
                    */
                    if (reachable[i] && !reachable[j]) {
                        tmp.add(i);
                        tmp.add(j);
                    }
                }
            }
            res[1] = new long[tmp.size()];
            for (int i = 0; i < tmp.size(); i++) {
                res[1][i] = tmp.get(i);
            }
            return res;
        }
        public long maxFeasibleFlow(int source, int sink, long[][] c, long[][] d)
        {
            int n = c.length;
            long[][] nc = new long[n+2][n+2];
            long[] a = new long[n];
            int source2 = n, sink2 = n + 1;
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    nc[u][v] = c[u][v] - d[u][v];
                    a[u] -= d[u][v];
                    a[v] += d[u][v];
                }
            }
            nc[sink][source] = INF;
            long sum = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] > 0) {
                    sum += a[i];
                    nc[source2][i] = a[i];
                }
                else {
                    nc[i][sink2] = -a[i];
                }
            }
            long f1 = maxFlow(source2, sink2, nc);
            if (f1 < sum) return -1; // no feasible flow
            return maxFlow(source, sink, nc);
        }
        public boolean feasible(long[][] c, long[][] d)
        {
            int n = c.length;
            long[][] nc = new long[n+2][n+2];
            long[] a = new long[n];
            int source2 = n, sink2 = n + 1;
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    nc[u][v] = c[u][v] - d[u][v];
                    a[u] -= d[u][v];
                    a[v] += d[u][v];
                }
            }
            long sum = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] > 0) {
                    sum += a[i];
                    nc[source2][i] = a[i];
                }
                else {
                    nc[i][sink2] = -a[i];
                }
            }
            return maxFlow(source2, sink2, nc) >= sum;
        }
        public long maxFlow(int source, int sink, long[][] capMat)
        {
            if (source == sink) return INF;
            int n = capMat.length;
            long total = 0;
            while (true) {
                int[] prevNodes = new int[n];
                Arrays.fill(prevNodes, -1);
                long[] flows = new long[n];
                boolean[] visited = new boolean[n];
                flows[source] = INF;
                long maxFlow = 0;
                int maxLoc = -1;
                while (true) {
                    maxFlow = 0;
                    maxLoc = -1;
                    // find the unvisited node with highest capacity
                    for (int i = 0; i < n; i++) {
                        if (flows[i] > maxFlow && !visited[i]) {
                            maxFlow = flows[i];
                            maxLoc = i;
                        }
                    }
                    if (maxLoc == -1) break;
                    if (maxLoc == sink) break;
                    visited[maxLoc] = true;
                    for (int i = 0; i < n; i++) {
                        if (capMat[maxLoc][i] == 0) continue;
                        if (flows[i] < Math.min(maxFlow, capMat[maxLoc][i])) {
                            prevNodes[i] = maxLoc;
                            flows[i] = Math.min(maxFlow, capMat[maxLoc][i]);
                        }
                    }
                }
                // no path
                if (maxLoc == -1) break;
                long pathCap = flows[sink];
                total += pathCap;
                int cur = sink;
                while (cur != source) {
                    int next = prevNodes[cur];
                    capMat[next][cur] -= pathCap;
                    capMat[cur][next] += pathCap;
                    cur = next;
                }
            }
            
            return total;
        }
    }
    
    

    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
    
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


