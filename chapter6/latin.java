/*
ID: wyli1231
LANG: JAVA
TASK: latin
*/

import java.io.*;
import java.util.*;

public class latin {
    static int n;
    static int[][] board;
    static boolean[][] rowappear, colappear;
    static int p = (int) 131;
	static long[] lppows = new long[100];
    static int[] facts = new int[8];
    static int[] cols, recols;
	static int[] rowCanUses;
	static Map<Long, Long> lmp = new HashMap<>();
    static Map<Long, Long> cycleMp = new HashMap<>();
	static int mask;
	
    static void calcpows()
    {
		lppows[0] = 1;
		for (int i = 1; i < lppows.length; i++) {
			lppows[i] = lppows[i-1] * p;
		}
    }
	
    // replace a 'from' value to 'to' value in recols, and keep recols sorted
    // [1,2,5,10,18] -> rr(2,13) -> [1,5,10,13,18]
    // [1,5,10,13,18] -> rr(13,2) -> [1,2,5,10,18]
    static void rr(int from, int to)
    {
		boolean larger = to > from;
		int idx = -1;
		int len = recols.length;
		if (larger) {
			for (int i = 0; i < len; i++) {
				if (recols[i] == from) {
					idx = i;
					break;
				}
			}
			int i;
			for (i = idx; i < len-1 && recols[i+1] < to; i++) {
				recols[i] = recols[i+1];
			}
			recols[i] = to;
		}
		else {
			for (int i = len-1; i >= 0; i--) {
				if (recols[i] == from) {
					idx = i;
					break;
				}
			}
			int i;
			for (i = idx; i >= 1 && recols[i-1] > to; i--) {
				recols[i] = recols[i-1];
			}
			recols[i] = to;
		}
    }
	
	static boolean impossible(int r, int c)
	{
		return (rowCanUses[r-1] & (cols[c-1]^mask)) == 0;
	}
	
    static boolean impossible2(int r, int c)
    {
        int toFill = (n-1) - c + 1;
        int canUse = 0;
        for (int i = c; i < n; i++) {
            canUse = canUse | (cols[i-1]^mask);
        }
        int rowCanUse = rowCanUses[r-1];
        return Integer.bitCount(rowCanUse&canUse) < toFill;
    }
	
	static boolean[] vis;
	static void dfsCycle(int idx, List<Integer> grp)
	{
		if (vis[idx]) return;
		vis[idx] = true;
		grp.add(idx);
		dfsCycle(board[1][idx], grp);
	}
    static long getCycleKey()
    {
		List<List<Integer>> groups = new ArrayList<>();
		vis = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (vis[i]) continue;
			List<Integer> grp = new ArrayList<>();
			dfsCycle(i, grp);
			groups.add(grp);
		}
		StringBuilder sb = new StringBuilder();
		Collections.sort(groups, (a, b) -> a.size() - b.size());
		long ans = 1;
		for (List<Integer> grp : groups) {
			ans = ans*p + grp.size();
		}
		return ans;
    }
    static long dfs(int r, int c)
    {
        if (r == n-1) {
            return 1;
        }
		if (impossible(r, c)) {
			return 0;
		}
		if (c > 1 && impossible2(r, c)) {
            return 0;
        }
        long cycleKey = -1;
        if (r == 2 && c == 1) {
            cycleKey = getCycleKey();
            if (cycleMp.containsKey(cycleKey)) {
                return cycleMp.get(cycleKey);
            }
        }
        long h = 7;
        for (int i = 0; i < recols.length; i++) {
			h = h + lppows[i] * recols[i];
		}
		Long l = lmp.get(h);
		if (l != null) return l;
 
        long ans = 0;
        int curcol = cols[c-1];
		int nextr, nextc;
        nextr = c == n-1 ? r + 1 : r;
        nextc = c == n-1 ? 1 : c + 1;
        for (int i = 0; i < n; i++) {
            if (rowappear[r][i] || colappear[c][i]) continue;
            rowappear[r][i] = true;
            colappear[c][i] = true;
			board[r][c] = i;
            cols[c-1] = curcol | (1 << i);
			rowCanUses[r-1] = rowCanUses[r-1] ^ (1 << i);
            rr(curcol, cols[c-1]);
            ans += dfs(nextr, nextc);
			rowCanUses[r-1] = rowCanUses[r-1] | (1 << i);
            rowappear[r][i] = false;
            colappear[c][i] = false;
            rr(cols[c-1], curcol);
            cols[c-1] = curcol;
        }

		lmp.put(h, ans);

        if (cycleKey != -1) {
            cycleMp.put(cycleKey, ans);
        }

        return ans;
    }
    static void solve()
    {
        n = ni();
		mask = (1 << n)-1;
        board = new int[n][n];
        rowappear = new boolean[n][n];
        colappear = new boolean[n][n];
        cols = new int[n-1];
        recols = new int[n-1];
		rowCanUses = new int[n-1];

        facts[0] = 1;
        for (int i = 1; i < facts.length; i++)
            facts[i] = facts[i-1] * i;

        calcpows();
        for (int i = 0; i < n; i++) {
            board[0][i] = i;
            rowappear[0][i] = true;
            colappear[i][i] = true;
			
			board[i][0] = i;
			rowappear[i][i] = true;
			colappear[0][i] = true;

            if (i>0) {
                cols[i-1] = (1 << i);
                recols[i-1] = (1 << i);
				rowCanUses[i-1] = (1 << i) ^ mask;
            }
        }
        long ans = dfs(1, 1) * facts[n-1];
        out.printf("%d\n", ans);
    }


    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "latin";
    static boolean logTime = !true;
    
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
    
    private static void tr(Object... o) { if(logTime)System.out.println(Arrays.deepToString(o)); }
}


