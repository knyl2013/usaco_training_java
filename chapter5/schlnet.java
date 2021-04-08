/*
ID: wyli1231
LANG: JAVA
TASK: schlnet
*/

import java.io.*;
import java.util.*;

public class schlnet {
    static boolean[][] adj;
	static boolean[][] dp;
    static int n;
    static List<Integer> lst;

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
	static class Tarjan {
	  
		// No. of vertices    
		private int V; 
		  
		//Adjacency Lists 
		private LinkedList<Integer> adj[]; 
		private int Time;
		  
		// Constructor 
		@SuppressWarnings("unchecked")
		Tarjan(int v) 
		{ 
			V = v; 
			adj = new LinkedList[v];
			  
			for(int i = 0; i < v; ++i) 
				adj[i] = new LinkedList(); 
				  
			Time = 0;
		} 
		  
		// Function to add an edge into the graph 
		void addEdge(int v,int w) 
		{ 
			adj[v].add(w); 
		} 
		  
		// A recursive function that finds and prints strongly 
		// connected components using DFS traversal 
		// u --> The vertex to be visited next 
		// disc[] --> Stores discovery times of visited vertices 
		// low[] -- >> earliest visited vertex (the vertex with
		//             minimum discovery time) that can be reached
		//             from subtree rooted with current vertex 
		// st -- >> To store all the connected ancestors (could be part 
		//         of SCC) 
		// stackMember[] --> bit/index array for faster check
		//                   whether a node is in stack 
		void SCCUtil(int u, int low[], int disc[],
					 boolean stackMember[], 
					 Stack<Integer> st, List<List<Integer>> ans)
		{
			  
			// Initialize discovery time and low value 
			disc[u] = Time; 
			low[u] = Time; 
			Time += 1;
			stackMember[u] = true;
			st.push(u);
		  
			int n;
			  
			// Go through all vertices adjacent to this 
			Iterator<Integer> i = adj[u].iterator(); 
			  
			while (i.hasNext()) 
			{ 
				n = i.next(); 
				  
				if (disc[n] == -1) 
				{
					SCCUtil(n, low, disc, stackMember, st, ans);
					  
					// Check if the subtree rooted with v 
					// has a connection to one of the 
					// ancestors of u 
					// Case 1 (per above discussion on
					// Disc and Low value) 
					low[u] = Math.min(low[u], low[n]);
				}
				else if (stackMember[n] == true)
				{
					  
					// Update low value of 'u' only if 'v' is
					// still in stack (i.e. it's a back edge, 
					// not cross edge). 
					// Case 2 (per above discussion on Disc
					// and Low value)
					low[u] = Math.min(low[u], disc[n]);
				}
			} 
		  
			// head node found, pop the stack and print an SCC 
			// To store stack extracted vertices 
			int w = -1; 
			if (low[u] == disc[u])
			{
				List<Integer> cur = new ArrayList<>();
				while (w != u)
				{ 
					w = (int)st.pop();
					// System.out.print(w + " ");
					cur.add(w);
					stackMember[w] = false;
				}
				ans.add(cur);
				// System.out.println(); 
			}
		}
		  
		// The function to do DFS traversal.
		// It uses SCCUtil() 
		List<List<Integer>> SCC()
		{
			  
			// Mark all the vertices as not visited 
			// and Initialize parent and visited, 
			// and ap(articulation point) arrays 
			int disc[] = new int[V]; 
			int low[] = new int[V]; 
			for(int i = 0;i < V; i++)
			{
				disc[i] = -1;
				low[i] = -1;
			}
			  
			boolean stackMember[] = new boolean[V]; 
			Stack<Integer> st = new Stack<Integer>(); 
			List<List<Integer>> ans = new ArrayList<>();
			// Call the recursive helper function 
			// to find articulation points 
			// in DFS tree rooted with vertex 'i' 
			for(int i = 0; i < V; i++)
			{
				if (disc[i] == -1)
					SCCUtil(i, low, disc,
							stackMember, st, ans);
			}
			
			return ans;
		}
	} // This code is contributed by Prateek Gupta (@prateekgupta10)

	static int taskB()
	{
		Tarjan g = new Tarjan(n);
		dp = new boolean[n][n];
		for (int i = 0; i < n; i++)
			dfs(i, dp[i]);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (adj[i][j])
					g.addEdge(i, j);
			}
		}
		List<List<Integer>> groups = g.SCC();
		int m = groups.size();
		if (m == 1) return 0;
		boolean[][] conn = new boolean[m][m];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				if (i == j) continue;
				int memi = groups.get(i).get(0), memj = groups.get(j).get(0);
				conn[i][j] = dp[memi][memj];
			}
		}
		int ins = 0, outs = 0;
		for (int i = 0; i < m; i++) {
			int inDeg = 0, outDeg = 0;
			for (int j = 0; j < m; j++) {
				if (conn[i][j]) outDeg++;
				if (conn[j][i]) inDeg++;
			}
			if (inDeg == 0) ins++;
			if (outDeg == 0) outs++;
		}
		return Math.max(ins, outs);
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
        int aAns = taskA(new boolean[n]);
        int bAns = taskB();
        out.printf("%d\n%d\n", aAns, bAns);
    }




    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "schlnet";
    
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


