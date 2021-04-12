/*
ID: wyli1231
LANG: JAVA
TASK: milk6
*/


import java.io.*;
import java.util.*;

public class milk6 {
    static void solve()
    {
        int n = ni(), m = ni();
		long[][] graph = new long[n][n];
		int[][] counts = new int[n][n];
		int[][] greatest = new int[n][n];
		List< List<List<Integer>> > ref = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			List<List<Integer>> lst = new ArrayList<>();
			for (int j = 0; j < n; j++) lst.add(new ArrayList<>());
			ref.add(lst);
		}
		for (int i = 0; i < m; i++) {
			int u = ni() - 1, v = ni() - 1, w = ni();
			graph[u][v] += w;
			counts[u][v]++;
			greatest[u][v] = i;
			ref.get(u).get(v).add(i+1);			
		}
		MinCut mc = new MinCut();
		MaxFlow mf = new MaxFlow();
		long min = mf.maxFlow(0, n - 1, MaxFlow.copy(graph));
		out.printf("%d ", min);
		for (int u = 0; u < n; u++) {
			for (int v = 0; v < n; v++) {
				graph[u][v] = graph[u][v] * 1005 + counts[u][v];
				graph[u][v] = graph[u][v] * 1005 + greatest[u][v];
			}
		}
		List<Integer> cuts = mc.minCut(0, n - 1, MaxFlow.copy(graph));
		List<Integer> returns = new ArrayList<>();
		for (int i = 0; i < cuts.size(); i += 2) {
			int u = cuts.get(i), v = cuts.get(i+1);
			List<Integer> idxs = ref.get(u).get(v);
			for (int idx : idxs) returns.add(idx);
		}
		out.printf("%d\n", returns.size());
		Collections.sort(returns);
		for (int idx : returns) {
			out.printf("%d\n", idx);
		}
    }
	static class MaxFlow {
        static final long INF = (long) 1e16;
        public static long[][] copy(long[][] mat)
        {
            long[][] ans = new long[mat.length][mat[0].length];
            for (int i = 0; i < mat.length; i++)
                for (int j = 0; j < mat[0].length; j++)
                    ans[i][j] = mat[i][j];
            return ans;
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
	static class MinCut { 
		// Returns true if there is a path 
		// from source 's' to sink 't' in residual  
		// graph. Also fills parent[] to store the path  
		private boolean bfs(long[][] rGraph, int s, 
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
		private void dfs(long[][] rGraph, int s, 
									boolean[] visited) { 
			visited[s] = true; 
			for (int i = 0; i < rGraph.length; i++) { 
					if (rGraph[s][i] > 0 && !visited[i]) { 
						dfs(rGraph, i, visited); 
					} 
			} 
		} 
	 
		private List<Integer> minCut(int s, int t, long[][] graph) { 
			List<Integer> ans = new ArrayList<>();
			int u,v; 
			  
			// Create a residual graph and fill the residual  
			// graph with given capacities in the original  
			// graph as residual capacities in residual graph 
			// rGraph[i][j] indicates residual capacity of edge i-j 
			long[][] rGraph = new long[graph.length][graph.length];  
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
				long pathFlow = Long.MAX_VALUE;          
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

    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
	// static String taskName = "milk6";
    
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


