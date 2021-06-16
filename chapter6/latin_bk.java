/*
ID: wyli1231
LANG: JAVA
TASK: latin
*/

import java.io.*;
import java.util.*;

public class latin_bk {
    static int n;
    static int[][] board;
    static boolean[][] rowappear, colappear;
    static int[] rorders, corders;
    static int ans = 0;
    static int limit;
    static int nax = (int) 1e5+7;
    static int p = 7;
    static long[] memo = new long[nax];
    static int[] ppows = new int[100];
    static Map<String, Long> mp = new HashMap<>();
    static int add(int a, int b)
    {
        int c = a + b;
        if (c >= nax) {
            c -= nax;
        }
        return c;
    }
    static int mul(int a, int b)
    {
        long c = (long) a * b;
        return (int)(c % nax);
    }
    static void calcpows()
    {
        ppows[0] = 1;
        for (int i = 1; i < ppows.length; i++) {
            ppows[i] = mul(ppows[i-1], i);
        }
    }
    static int hash(String s)
    {
        int ans = 0;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            ans = add(ans, mul(ppows[i], s.charAt(i)));
        }
        return ans;
    }
    static List<Integer> encode3(int r)
    {
        List<Integer> lst = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int cur = 0;
            for (int j = 0; j < n; j++) {
                if (colappear[i][j])
                    cur = cur | (1 << j);
            }
            lst.add(cur);
        }
        Collections.sort(lst, (a, b) -> {
            int bita = Integer.bitCount(a), bitb = Integer.bitCount(b);
            if (bita != bitb) return bita - bitb;
            return a - b;
        });
        int cur = 0;
        for (int j = 0; j < n; j++) {
            if (rowappear[r][j]) {
                cur = cur | (1 << j);
            }
        }
        lst.add(cur);
        return lst;
    }
    static String encode2(int r)
    {
        List<Integer> lst = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int cur = 0;
            for (int j = 0; j < n; j++) {
                if (colappear[i][j])
                    cur = cur | (1 << j);
            }
            lst.add(cur);
        }
        Collections.sort(lst, (a, b) -> {
            int bita = Integer.bitCount(a), bitb = Integer.bitCount(b);
            if (bita != bitb) return bita - bitb;
            return a - b;
        });
        int cur = 0;
        for (int j = 0; j < n; j++) {
            if (rowappear[r][j]) {
                cur = cur | (1 << j);
            }
        }
        lst.add(cur);
        StringBuilder sb = new StringBuilder();
        for (int x : lst) {
            sb.append(x).append('-');
        }
        return sb.toString();
    }
	static int f()
	{
		int ans = 0;
		List<Integer> lst = new ArrayList<>();
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < n; i++) {
			int cur = 0;
			for (int j = 0; j < n; j++) {
				// if (i==j) continue;
				if (colappear[i][j]) {
					cur = cur | (1 << (j));
				}
				// if (!colappear[i][j]&& !colappear[j][i]) {
					// ans++;
				// }
			}
			lst.add(cur);
			set.add(cur);
			ans += cur;
		}
		System.out.println(lst);
		return set.size();
		
		// return ans;
	}
    static String encode(int r)
    {
        char[] ans = new char[n*n+n];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[idx++] = colappear[i][j] ? '1' : '0';
            }
        }
        for (int i = 0; i < n; i++)
            ans[idx++] = rowappear[r][i] ? '1' : '0';
        return new String(ans);
    }
    static void dfs(int level)
    {
        if (level >= limit) {
            ans++;
            return;
        }
        int r = rorders[level], c = corders[level];
        for (int i = 0; i < n; i++) {
            if (rowappear[r][i] || colappear[c][i]) continue;
            rowappear[r][i] = true;
            colappear[c][i] = true;
            dfs(level + 1);
            rowappear[r][i] = false;
            colappear[c][i] = false;
        }
    }
    static long dfs(int r, int c)
    {
        if (r == n) {
            return 1;
        }
        // int h = hash(encode2(r));
        // if (memo[h] != -1) return memo[h];
        String str = encode2(r);
        if (mp.containsKey(str)) {
            // System.out.println("prune");
            return mp.get(str);
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            if (rowappear[r][i] || colappear[c][i]) continue;
            rowappear[r][i] = true;
            colappear[c][i] = true;
            int nextr = c == n-1 ? r + 1 : r;
            int nextc = c == n-1 ? 0 : c + 1;
            ans += dfs(nextr, nextc);
            rowappear[r][i] = false;
            colappear[c][i] = false;
        }
        mp.put(str, ans);
		if (c == 0 && r == 3) {
			System.out.println(r + " " + ans + " " + f());
			// for (boolean[] row : rowappear) {
				// for (boolean b : row) {
					// System.out.print(b ? 1 : 0);
				// }
				// System.out.println();
			// }
			// System.out.println();
			// for (boolean[] row : colappear) {
				// for (boolean b : row) {
					// System.out.print(b ? 1 : 0);
				// }
				// System.out.println();
			// }
		}
        // memo[h] = ans;
        return ans;
    }
    static void solve()
    {
        n = ni();
        board = new int[n][n];
        rowappear = new boolean[n][n];
        colappear = new boolean[n][n];
        limit = n*n-n;
        rorders = new int[limit];
        corders = new int[limit];
        calcpows();
        Arrays.fill(memo, -1);
        int r = 1, c = 0;
        for (int i = 0; i < limit; i++) {
            rorders[i] = r;
            corders[i] = c;
            int nextr = r == n-1 ? 1 : r+1;
            int nextc = r == n-1 ? c+1 : c;
            r = nextr;
            c = nextc;
        }
        for (int i = 0; i < n; i++) {
            board[0][i] = i;
            rowappear[0][i] = true;
            colappear[i][i] = true;
        }
        // dfs(0);
        long ans = dfs(1, 0);
        out.printf("%d\n", ans);
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
    // static String taskName = "latin";
    static boolean logTime = true;
    
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


