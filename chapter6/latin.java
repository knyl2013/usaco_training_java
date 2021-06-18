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
    static int[] rorders, corders;
    static int ans = 0;
    static int limit;
    static int nax = (int) 1e4;
    static int p = (int) 131;
    static long[] memo = new long[nax];
	static String[] cmps = new String[nax];
    static int[] ppows = new int[100];
    static int[][] perms;
    static int[] facts = new int[8];
    static int[] cols, recols;
    static Map<String, Long> mp = new HashMap<>();
    static long[][] seen;
    static void swap(int[] arr, int i, int j)
    {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    
    static void reverse(int[] arr, int start, int end)
    {
        while (start < end) {
            swap(arr, start++, end--);
        }
    }
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
            ppows[i] = mul(ppows[i-1], p);
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
    static String encode4()
    {
        StringBuilder sb = new StringBuilder();
        for (int x : recols) {
            sb.append(x);
        }
        return sb.toString();
    }
	static int hash2()
    {
        List<Integer> lst = new ArrayList<>();
		int ans = 0;
        for (int i = 1; i < n; i++) {
            int cur = 0;
            for (int j = 0; j < n; j++) {
                if (colappear[i][j])
                    cur = cur | (1 << j);
            }
            lst.add(cur);
        }
        Collections.sort(lst, (a, b) -> {
            // int bita = Integer.bitCount(a), bitb = Integer.bitCount(b);
            // if (bita != bitb) return bita - bitb;
            return a - b;
        });
		int pidx = 0;
		for (int x : lst) {
			// System.out.println("p: " + pidx + " " + ppows[pidx] + " " + x);
			ans = add(ans, mul(ppows[pidx++], x));
		}
		// for (int x : lst) {
			// while (x>0) {
				// ans = add(ans, mul(ppows[pidx++], x%10));
				// x/=10;
			// }
			// ans = add(ans, mul(ppows[pidx++], 10));
			// ans = add(ans, mul(ppows[pidx++], x));
		// }
		
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
        for (int i = 1; i < n; i++) {
            int cur = 0;
            for (int j = 0; j < n; j++) {
                if (colappear[i][j])
                    cur = cur | (1 << j);
            }
            lst.add(cur);
        }
        Collections.sort(lst, (a, b) -> {
            // int bita = Integer.bitCount(a), bitb = Integer.bitCount(b);
            // if (bita != bitb) return bita - bitb;
            return a - b;
        });
        // int cur = 0;
        // for (int j = 0; j < n; j++) {
            // if (rowappear[r][j]) {
                // cur = cur | (1 << j);
            // }
        // }
        // lst.add(cur);
        StringBuilder sb = new StringBuilder();
        for (int x : lst) {
            sb.append(x).append('-');
        }
        return sb.toString();
    }
    static void permutate()
    {
        perms = new int[facts[n]][];
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = i;
        
        perms[0] = arr.clone();
        for (int i = 1; i < perms.length; i++) {
            int maxsofar = arr[n-1];
            int swapidx = -1;
            for (int j = n-2; j >= 0; j--) {
                if (arr[j] < maxsofar) {
                    swapidx = j;
                    break;
                }
                else {
                    maxsofar = arr[j];
                }
            }
            int minidx = -1;
            
            for (int j = swapidx+1; j < n; j++) {
                if (arr[j] > arr[swapidx]) {
                    if (minidx == -1 || arr[minidx] > arr[j])
                        minidx = j;
                }
            }
            
            swap(arr, minidx, swapidx);
            reverse(arr, swapidx+1, n-1);
            
            perms[i] = arr.clone();
        }
    }
	static int f()
	{
		// int ans = 0;
		// List<Integer> lst = new ArrayList<>();
		// Set<Integer> set = new HashSet<>();
		// for (int i = 0; i < n; i++) {
		// 	int cur = 0;
		// 	for (int j = 0; j < n; j++) {
		// 		// if (i==j) continue;
		// 		if (colappear[i][j]) {
		// 			cur = cur | (1 << (j));
		// 		}
		// 		// if (!colappear[i][j]&& !colappear[j][i]) {
		// 			// ans++;
		// 		// }
		// 	}
		// 	lst.add(cur);
		// 	set.add(cur);
		// 	ans += cur;
		// }
		// System.out.println(lst);
		// return set.size();
		
		// return ans;

        int cnt = 0;
        for (int[] perm : perms) {
            int i;
            for (i = 0; i < n; i++) {
                int val = perm[i];
                if (colappear[i][val]) break;
            }
            if (i == n) cnt++;
        }
        return cnt;
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
    // TODO: replace a 'from' value to 'to' value in recols, and keep recols sorted
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
			// for (int[] row : board) {
				// System.out.println(Arrays.toString(row));
			// }
			// System.out.println("==============");
            return 1;
        }
        // int h = hash(encode2(r));
		// int h = hash2();
		// System.out.println(h + " " + encode2(r));
		String str = encode4();
		// String str = encode2(r);
		// if (memo[h] != -1 && !cmps[h].equals(str)) {
			// System.out.println(h + " " + cmps[h] + " " + str);
		// }
        // if (memo[h] != -1) return memo[h];
		// cmps[h] = str;
        
        if (mp.containsKey(str)) {
            // System.out.println("prune");
            return mp.get(str);
        }
        // int fv = -1;
        // if (c == 0) {
            // fv = f();
            // if (seen[r][fv] != -1) {
                // return seen[r][fv];
            // }
        // }
        long ans = 0;
        int curcol = cols[c-1];
        for (int i = 0; i < n; i++) {
            if (rowappear[r][i] || colappear[c][i]) continue;
            rowappear[r][i] = true;
            colappear[c][i] = true;
			board[r][c] = i;
            cols[c-1] = curcol | (1 << i);
            rr(curcol, cols[c-1]);
            int nextr = c == n-1 ? r + 1 : r;
            int nextc = c == n-1 ? 1 : c + 1;
            ans += dfs(nextr, nextc);
            rowappear[r][i] = false;
            colappear[c][i] = false;
            rr(cols[c-1], curcol);
            cols[c-1] = curcol;
        }
        mp.put(str, ans);
        // if (c == 0) {
            // seen[r][fv] = ans;
        // }
		// if (c == 0 && f() == 16) {
			// System.out.println(r + " " + ans + " " + f());
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
		// }
        // memo[h] = ans;
        return ans;
    }
    static void solve()
    {
        n = ni();
        board = new int[n][n];
        rowappear = new boolean[n][n];
        colappear = new boolean[n][n];
        cols = new int[n-1];
        recols = new int[n-1];
        limit = n*n-n;
        rorders = new int[limit];
        corders = new int[limit];

        facts[0] = 1;
        for (int i = 1; i < facts.length; i++)
            facts[i] = facts[i-1] * i;
        seen = new long[n][facts[facts.length-1]+1];
		for (long[] row : seen)
			Arrays.fill(row, -1);
        calcpows();
        permutate();
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
			
			board[i][0] = i;
			rowappear[i][i] = true;
			colappear[0][i] = true;

            if (i>0) {
                cols[i-1] = (1 << i);
                recols[i-1] = (1 << i);
            }
        }
        // dfs(0);
        // System.out.println(Arrays.toString(cols));
        long ans = dfs(1, 1) * facts[n-1];
		// System.out.println(mp.size());
        out.printf("%d\n", ans);
		
		
		recols = new int[]{1,2,5,10,18};
		rr(2, 13);
		System.out.println(Arrays.toString(recols));
		rr(13, 2);
		System.out.println(Arrays.toString(recols));
		rr(10, 0);
		System.out.println(Arrays.toString(recols));
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


