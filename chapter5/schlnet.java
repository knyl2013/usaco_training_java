/*
ID: wyli1231
LANG: JAVA
TASK: schlnet
*/

import java.io.*;
import java.util.*;

public class schlnet {
    static boolean[][] adj;
    static int n, aAns;
    static List<Integer> lst;
	static int loops;
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
        if (visited[idx]) return;
        for (int i = 0; i < n; i++) {
            if (!adj[idx][i]) continue;
            bHelperUtil(i, visited);
            visited[idx] = true;
            return;
        }
        visited[idx] = true;
        // bAns++;
    }
    static void bHelper(int idx)
    {
        boolean[] visited = new boolean[n];
        bHelperUtil(idx, visited);
        // bAns++;
    }
    static final int UNVISIT = 0, VISITING = 1, VISITED = 2;
    static void util(int idx, int[] states, boolean reverse)
    {
        if (states[idx] == VISITED) return;
        states[idx] = VISITING;
        for (int i = 0; i < n; i++) {
            if (states[i] == VISITING || (!reverse && !adj[idx][i])) continue;
			if (states[i] == VISITING || (reverse && !adj[i][idx])) continue;
            if (states[i] == VISITED) {
                states[idx] = VISITED;
                return;
            }
            util(i, states, reverse);
            states[idx] = VISITED;
            return;
        }
        // bAns++;
		loops++;
        // System.out.println("idx: " + idx);
        states[idx] = VISITED;
    }
	static void findCycles(int start, int current, boolean[] visited, Stack<Integer> stk, Set<String> cycles)
	{
		if (start == current && visited[start]) {
			List<Integer> tmp = new ArrayList<>();
			for (int ele : stk)
				tmp.add(ele);
			Collections.sort(tmp);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < tmp.size(); i++) {
				boolean lastEle = i == tmp.size() - 1;
				sb.append(tmp.get(i));
				if (!lastEle) sb.append('|');
			}
			cycles.add(sb.toString());
			return;
		}
		visited[current] = true;
		stk.push(current);
		for (int i = 0; i < n; i++) {
			if (!adj[current][i] || current == i) continue;
			if (visited[i] && i != start) continue;
			findCycles(start, i, visited, stk, cycles);
		}
		stk.pop();
		visited[current] = false;
	}
    static int taskB()
    {
        // bAns = 0;
        // int[] states = new int[n];
        // for (int i = 0; i < n; i++)
            // util(i, states, false);
        // bAns--;
        // TODO: find in-deg = 0 vertices
		// states = new int[n];
		// for (int i = 0; i < n; i++)
			// util(i, states, true);
        // return bAns;
		boolean[][] dp = new boolean[n][n];
		
		List<Integer> ends = new ArrayList<>();
		int paths = 0;
		for (int i = 0; i < n; i++) {
			int outDeg = dfs(i, dp[i]);
			if (outDeg == 1) {
				ends.add(i);
				paths++;
			}
		}
		int[] states = new int[n];
		
		for (int i = 0; i < n; i++) {
			boolean connectEnd = false;
			for (int e : ends) {
				if (dp[i][e]) {
					connectEnd = true;
					break;
				}
			}
			if (connectEnd) continue;
			int inDeg = 0;
			for (int j = 0; j < n; j++)
				if (dp[j][i]) inDeg++;
			if (inDeg == 1) {
				loops++;
				continue;
			}
			util(i, states, false);
		}
		if (paths == 0 && loops == 1) return 0;
		else return paths + loops;
		// Set<String> cycles = new HashSet<>();
		// for (int i = 0; i < n; i++)
			// findCycles(i, i, new boolean[n], new Stack<>(), cycles);
		// for (String cycle : cycles) {
			// List<String> lst = cycle.split("|");
			// for (String str : lst) {
				// int vertex = Integer.parseInt(str);
				
			// }
		// }
		
		
		// return bAns;
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
        int bAns = taskB();
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


