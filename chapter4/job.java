/*
ID: wyli1231
LANG: JAVA
TASK: job
*/


import java.io.*;
import java.util.*;

public class job {
    static void show(long[][] mat)
    {
        for (int i = 0; i < mat.length; i++) {
            System.out.println(Arrays.toString(mat[i]));
        }
    }
    // assign[i] := time to finish (i+1)-th fastest task, given n tasks to finish
    static int[] assign(int[] times, int n)
    {
        int mx = times.length;
        int source = mx, sink = mx + 1;
        int prev = source;
        long[][] cap = new long[mx + 5][mx + 5];
        MaxFlow flow = new MaxFlow();
        MinCut mc = new MinCut();
        for (int i = 0; i < mx; i++) {
            cap[prev][i] = times[i];
            prev = i;
        }
        cap[prev][sink] = MaxFlow.INF;
        long[][] ori = flow.copy(cap);
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            List<Integer> res = mc.minCut(source, sink, flow.copy(cap));
            int frm = res.get(0), to = res.get(1);
            ans[i] = (int) flow.maxFlow(source, sink, flow.copy(cap));
            cap[frm][to] += ori[frm][to];
            // long[][] res = flow.minCut(source, sink, flow.copy(cap));
            // int frm = (int) res[1][0], to = (int) res[1][1];
            // cap[frm][to] += ori[frm][to];
            // ans[i] = (int) res[0][0];
        }
        return ans;
    }
    static void solve()
    {
        int n = ni(), m1 = ni(), m2 = ni();
        int[] as = na(m1);
        int[] bs = na(m2);
        int[] assign1 = assign(as, n);
        int[] assign2 = assign(bs, n);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, assign1[i] + assign2[n-i-1]);
        }
        out.printf("%d %d\n", assign1[n-1], ans);
        // System.out.println(Arrays.toString(assign1));
        // long[][] capA = new long[m1 + 5][m1 + 5];
        // int source = m1, sink = m1 + 1;
        // int prev = source;
        // for (int i = 0; i < m1; i++) {
            // capA[prev][i] = as[i];
            // prev = i;
        // }
        // capA[prev][sink] = INF;
        // long[][] oriA = flow.copy(capA);
        // List<Integer> tasks = new ArrayList<>();
        // int a = 0;
        // for (int i = 0; i < n; i++) {
            // long[][] res = flow.minCut(source, sink, flow.copy(capA));
            // int frm = (int) res[1][0], to = (int) res[1][1];
            // capA[frm][to] += oriA[frm][to];
            // a = (int) res[0][0];
            // tasks.add(a);
        // }
        // System.out.println(tasks);

        // long[][] capB = new long[m2 + 5][m2 + 5];
        // source = m2;
        // sink = m2 + 1;
        // prev = source;
        // for (int i = 0; i < m2; i++) {
        //     capB[prev][i] = bs[i];
        //     prev = i;
        // }
        // capB[prev][sink] = INF;
        // long[][] oriB = copy(capB);
        // List<int[]> edges = new ArrayList<>();
        // int b = 0;
        // int last = tasks.get(tasks.size() - 1);
        // System.out.println(tasks);
        // // try to arrange tasks by choosing min-cut from last to beginning
        // for (int k = tasks.size() - 1; k >= 0; k--) {
        //     int t = tasks.get(k);
        //     int delta = last - t;
        //     // System.out.println("delta: " + delta);
        //     last = t;
        //     for (int i = 0; i < capB.length; i++) {
        //         for (int j = 0; j < capB.length; j++) {
        //             if (capB[i][j] == 0 || capB[i][j] == oriB[i][j]) 
        //                 continue;
        //             long cur = capB[i][j] - delta;
        //             capB[i][j] = Math.max(oriB[i][j], cur);
        //         }
        //     }
        //     long[][] res = flow.minCut(source, sink, copy(capB));
        //     int frm = (int) res[1][0], to = (int) res[1][1];
        //     System.out.println("min-cut: " + res[0][0] + ", t: " + t);
        //     // System.out.println(frm + " " + to);
        //     capB[frm][to] += oriB[frm][to]; // next time we need extra cost to make for this path
        //     // System.out.println(res[0][0] + t);
        //     edges.add(new int[]{frm, to});
        //     b = Math.max(b, (int) (res[0][0] + t));
        //     // b = (int) (res[0][0] + t);
        // }
        // System.out.println(Arrays.toString(edges.peek()));
        // for (int[] s : edges) {
        //     System.out.println(Arrays.toString(s));
        // }
        // last = 0;
        // // try to finish tasks by the arrangement formed above
        // for (int i = edges.size() - 1, j = 0; i >= 0; i--, j++) {
        //     int[] e = edges.get(i);
        //     int t = tasks.get(j);
        //     int delta = t - last;
        //     last = t;
        //     for (int k = 0; k < capB.length; k++) {
        //         for (int m = 0; m < capB.length; m++) {
        //             if (capB[k][m] == 0 || capB[k][m] == oriB[k][m]) 
        //                 continue;
        //             long cur = capB[k][m] - delta;
        //             capB[k][m] = Math.max(oriB[k][m], cur);
        //         }
        //     }
        //     long finish = t + capB[e[0]][e[1]];
        //     System.out.println(e[0] + " " + e[1] + " " + finish);
        //     b = Math.max(b, (int) finish);
        // }

        // System.out.println(a);
        // System.out.println(b);
        // out.printf("%d %d\n", a, b);

    }
    static class MinCut { 
        // Returns true if there is a path 
        // from source 's' to sink 't' in residual  
        // graph. Also fills parent[] to store the path  
        private boolean bfs(int[][] rGraph, int s, 
                                    int t, int[] parent) { 
              
            // Create a visited array and mark  
            // all vertices as not visited      
            boolean[] visited = new boolean[rGraph.length]; 
              
            // Create a queue, enqueue source vertex 
            // and mark source vertex as visited      
            Queue<Integer> q = new LinkedList<Integer>(); 
            q.add(s); 
            visited[s] = true; 
            parent[s] = -1; 
              
            // Standard BFS Loop      
            while (!q.isEmpty()) { 
                int v = q.poll(); 
                for (int i = 0; i < rGraph.length; i++) { 
                    if (rGraph[v][i] > 0 && !visited[i]) { 
                        q.offer(i); 
                        visited[i] = true; 
                        parent[i] = v; 
                    } 
                } 
            } 
              
            // If we reached sink in BFS starting  
            // from source, then return true, else false      
            return (visited[t] == true); 
        } 
          
        // A DFS based function to find all reachable  
        // vertices from s. The function marks visited[i]  
        // as true if i is reachable from s. The initial  
        // values in visited[] must be false. We can also  
        // use BFS to find reachable vertices 
        private void dfs(int[][] rGraph, int s, 
                                    boolean[] visited) { 
            visited[s] = true; 
            for (int i = 0; i < rGraph.length; i++) { 
                    if (rGraph[s][i] > 0 && !visited[i]) { 
                        dfs(rGraph, i, visited); 
                    } 
            } 
        } 
      
        // Prints the minimum s-t cut 
        private List<Integer> minCut(int s, int t, long[][] graph) {
            int n = graph.length;
            int[][] convert = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    convert[i][j] = (int)graph[i][j];
                }
            }
            return minCut(s, t, convert);
        }
        private List<Integer> minCut(int s, int t, int[][] graph) { 
            List<Integer> ans = new ArrayList<>();
            int u,v; 
              
            // Create a residual graph and fill the residual  
            // graph with given capacities in the original  
            // graph as residual capacities in residual graph 
            // rGraph[i][j] indicates residual capacity of edge i-j 
            int[][] rGraph = new int[graph.length][graph.length];  
            for (int i = 0; i < graph.length; i++) { 
                for (int j = 0; j < graph.length; j++) { 
                    rGraph[i][j] = graph[i][j]; 
                } 
            } 
      
            // This array is filled by BFS and to store path 
            int[] parent = new int[graph.length];  
              
            // Augment the flow while tere is path from source to sink      
            while (bfs(rGraph, s, t, parent)) { 
                  
                // Find minimum residual capacity of the edhes  
                // along the path filled by BFS. Or we can say  
                // find the maximum flow through the path found. 
                int pathFlow = Integer.MAX_VALUE;          
                for (v = t; v != s; v = parent[v]) { 
                    u = parent[v]; 
                    pathFlow = Math.min(pathFlow, rGraph[u][v]); 
                } 
                  
                // update residual capacities of the edges and  
                // reverse edges along the path 
                for (v = t; v != s; v = parent[v]) { 
                    u = parent[v]; 
                    rGraph[u][v] = rGraph[u][v] - pathFlow; 
                    rGraph[v][u] = rGraph[v][u] + pathFlow; 
                } 
            } 
              
            // Flow is maximum now, find vertices reachable from s      
            boolean[] isVisited = new boolean[graph.length];      
            dfs(rGraph, s, isVisited); 
              
            // Print all edges that are from a reachable vertex to 
            // non-reachable vertex in the original graph      
            for (int i = 0; i < graph.length; i++) { 
                for (int j = 0; j < graph.length; j++) { 
                    if (graph[i][j] > 0 && isVisited[i] && !isVisited[j]) { 
                        // System.out.println(i + " - " + j); 
                        ans.add(i);
                        ans.add(j);
                    } 
                } 
            } 
            
            return ans;
        } 
      
    } // This code is contributed by Himanshu Shekhar 
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
    

    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "job";
        
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





