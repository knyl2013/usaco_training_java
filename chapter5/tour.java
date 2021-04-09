/*
ID: wyli1231
LANG: JAVA
TASK: tour
*/

import java.io.*;
import java.util.*;

public class tour {
	static int n, m;
	static List<List<Integer>> adj;
	static final int INF = (int)1e7;
	static int[][] memo;
	
	static int dfs(int a, int b)
	{
		if (a == b && a != 0 && a != n-1)
			return -INF;
		if (a == n-1 && b == n-1)
			return 1;
		if (memo[a][b] != -1)
			return memo[a][b];
		int ans = -INF;
		if (a == 0 && b == 0) {
			for (int n1 : adj.get(0)) {
				for (int n2 : adj.get(0)) {
					ans = Math.max(ans, 1 + dfs(n1, n2));
				} 
			}
		}
		else if (a < b) {
			for (int nei : adj.get(a))
				ans = Math.max(ans, 1 + dfs(nei, b));
		}
		else {
			for (int nei : adj.get(b))
				ans = Math.max(ans, 1 + dfs(a, nei));
		}
		return memo[a][b] = ans;
	}
    static void solve()
    {
        n = ni();
		m = ni();
		adj = new ArrayList<>();
		memo = new int[n][n];
		for (int[] row : memo)
			Arrays.fill(row, -1);
		Map<String, Integer> mp = new HashMap<>();
		for (int i = 0; i < n; i++) {
			adj.add(new ArrayList<>());
			mp.put(ns(), i);
		}
		for (int i = 0; i < m; i++) {
			int u = mp.get(ns()), v = mp.get(ns());
			int frm = Math.min(u, v), to = Math.max(u, v);
			adj.get(frm).add(to);
		}
		int ans = dfs(0, 0);
		if (ans <= 0) ans = 1;
		out.printf("%d\n", ans);
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
	static String taskName = "tour";
    
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

