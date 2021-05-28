/*
ID: wyli1231
LANG: JAVA
TASK: fence8
*/

import java.io.*;
import java.util.*;

public class fence8 {
	static int[] boards, rails, prefixs;
	static int n, m;
	static int[] currents;
	static boolean bruteAns;
	static int k;
	static boolean backtrack(int idx)
	{
		if (bruteAns) return true;
		if (idx == k) {
			bruteAns = true;
			return bruteAns;
		}
		for (int i = 0; i < n; i++) {
			if (currents[i] < rails[idx]) continue;
			currents[i] -= rails[idx];
			if (backtrack(idx + 1)) return true;
			currents[i] += rails[idx];
		}
		return false;
	}
	static boolean okBrute(int _k)
	{
		k = _k;
		bruteAns = false;
		currents = boards.clone();
		backtrack(0);
		return bruteAns;
	}
	static boolean ok(int k) // whether it is possible to fill first k rails
	{
		int len = m + n + 2;
		int source = m + n, sink = m + n + 1;
		/*
			0..m-1 -> rails' id
			m..m+n-1 -> boards' id
		*/
		long[][] cap = new long[len][len];
		// long[][] d = new long[len][len];
		MaxFlow mf = new MaxFlow();
		for (int i = 0; i < n; i++)
			cap[i+m][sink] = boards[i];
		for (int i = 0; i < k; i++) {
			cap[source][i] = rails[i];
			for (int j = 0; j < n; j++) {
				if (boards[j] >= rails[i])
					cap[i][j+m] = rails[i];
				// d[i][j+m] = rails[i];
			}
		}
		return mf.maxFlow(source, sink, cap) >= prefixs[k-1];
	}
	static int brute(int[] _boards, int[] _rails, int _n, int _m)
	{
		boards = _boards;
		rails = _rails;
		n = _n;
		m = _m;
		Arrays.sort(rails);
		prefixs = new int[m];
		int run = 0;
		for (int i = 0; i < m; i++) {
			run += rails[i];
			prefixs[i] = run;
		}
		int ans = 0;
		int lo = 1, hi = m;
		// System.out.println(lo + " " + hi);
		while (lo <= hi) {
			int mid = (lo + hi) / 2;
			// if (ok(mid)) {
			if (okBrute(mid)) {
				// System.out.println("ok");
				ans = mid;
				lo = mid + 1;
			}
			else {
				// System.out.println("not ok");
				hi = mid - 1;
			}
		}
		return ans;
	}
	List<Integer> canUse;
	int[][] memo;
	static int dfs(int val, int last)
	{
		if (val == 0) return 0;
		if (last < 0) return val;
		int ans = dfs(val, last-1);
		if (val >= rails[canUse.get(last)]) {
			ans = Math.min(ans, dfs(val - rails[canUse.get(last)], last-1));
		}
		return ans;
	}
	static List<Integer> changeHelper(int val, boolean[] railMarked)
	{
		canUse = new ArrayList<>();
		int k = railMarked.length;
		for (int i = 0; i < k; i++)
			if (!railMarked[i])
				canUse.add(i);
		int len = canUse.size();
		memo = new int[val+1][len-1];
		for (int i = 0; i < memo.length; i++)
			Arrays.fill(memo[i], -1);
		int mini = dfs(val, len-1);
		List<Integer> ans = new ArrayList<>();
		ans.add(mini);
		
		int idx = len-1;
		int v = val;
		while (idx >= 0) {
			int chooseIdx = dfs(v - rails[canUse.get(idx)], last-1);
			int ignoreIdx = dfs(v, last-1);
			if (chooseIdx < ignoreIdx) {
				ans.add(idx);
				v -= rails[canUse.get(idx)];
			}
			idx--;
		}
		
		return ans;
	}
	static int change(boolean[] boardMarked, boolean[] railMarked)
	{
		int best = 0, bestIdx = 0;
		List<Integer> bestList = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			if (boardMarked[i]) continue;
			List<Integer> cur = changeHelper(boards[i], railMarked);
			if (cur.size() > 0 && cur.get(0) > best) {
				best = cur.get(0);
				bestList = cur;
				bestIdx = i;
			}
		}
		if (best == 0) return best;
		boardMarked[bestIdx] = true;
		for (int i = 1; i < bestList.size(); i++) {
			int idx = bestList.get(i);
			railMarked[idx] = true;
		}
		return best;
	}
	static boolean ok2(int k)
	{
		boolean[] boardMarked = new boolean[n];
		boolean[] railMarked = new boolean[k];
		int cnt = k;
		while (cnt > 0) {
			int minus = change(boardMarked, railMarked);
			if (minus == 0) break;
			cnt -= minus;
		}
		return cnt == 0;
	}
	static int answer(int[] _boards, int[] _rails, int _n, int _m)
	{
		boards = _boards;
		rails = _rails;
		n = _n;
		m = _m;
		Arrays.sort(rails);
		prefixs = new int[m];
		int run = 0;
		for (int i = 0; i < m; i++) {
			run += rails[i];
			prefixs[i] = run;
		}
		int ans = 0;
		int lo = 1, hi = m;
		// System.out.println(lo + " " + hi);
		while (lo <= hi) {
			int mid = (lo + hi) / 2;
			if (ok2(mid)) {
			// if (okBrute(mid)) {
				// System.out.println("ok");
				ans = mid;
				lo = mid + 1;
			}
			else {
				// System.out.println("not ok");
				hi = mid - 1;
			}
		}
		return ans;
	}
	static int[] randomArr(int n, int lo, int hi)
    {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) ((Math.random() * (hi - lo + 1)) + lo);
        }
        return ans;
    }
	static void testcase()
	{
		while (true) {
			int n = randomArr(1, 1, 5)[0];
			int[] boards = randomArr(n, 1, 5);
			int m = randomArr(1, 1, 5)[0];
			int[] rails = randomArr(m, 1, 5);
			int expect = brute(boards, rails, n, m);
			int actual = answer(boards, rails, n, m);
			if (expect != actual) {
				System.out.println("boards: " + Arrays.toString(boards));
				System.out.println("rails: " + Arrays.toString(rails));
				System.out.println("expect: " + expect);
				System.out.println("actual: " + actual);
				break;
			}
			else System.out.println("ok");
		}
	}
	static void solve()
    {
		boolean debug = true;
		if (debug) {
			testcase();
			return;
		}
        int n = ni();
		int[] boards = new int[n];
		for (int i = 0; i < n; i++)
			boards[i] = ni();
		int m = ni();
		int[] rails = new int[m];
		for (int i = 0; i < m; i++)
			rails[i] = ni();
		int ans = answer(boards, rails, n, m);
		out.printf("%d\n", ans);
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





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
	// static String taskName = "fence8";
    
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

