/*
ID: wyli1231
LANG: JAVA
TASK: prime3
*/

import java.io.*;
import java.util.*;

public class prime3 {
	static int nax = (int)1e6+7;
	static boolean[] prefix = new boolean[nax];
	static boolean[] suffix = new boolean[nax];
	static boolean[] sufStartWithTopLeft = new boolean[nax];
	static int[][] board = new int[5][5];
	static int topLeft, sum;
	static int topDia = 0, bottomDia = 0;
	static int[] rows = new int[5];
	static int[] cols = new int[5];
	static final int UNVISITED = 99;
	static int[] tenPows = new int[6];
	static List<int[]> ans = new ArrayList<>();
	static int[] orders = new int[]{
		24, 23, 22, 21, 20, 19, 14, 9, 18, 13, 17, 16, 12, 8, 4, 15, 11, 7, 3, 10, 6, 2, 5, 1, 0
	};
	static int[] ordersR = new int[25], ordersC = new int[25];
	static void calcPows()
	{
		tenPows[0] = 1;
		for (int i = 1; i < tenPows.length; i++) {
			tenPows[i] = tenPows[i-1] * 10;
		}
	}
	static void dfs(int oidx)
	{
		int r = ordersR[oidx], c = ordersC[oidx];
		int nxtTopDia = -1, nxtBottomDia = -1;
		int nxtRow = -1, nxtCol = -1;
		int oldTopDia = topDia, oldBottomDia = bottomDia;
		int oldRow = rows[r], oldCol = cols[c];
		if (r == c) {
			nxtTopDia = topDia + board[r][c] * tenPows[4-c];
			if (!sufStartWithTopLeft[nxtTopDia]) {
				return;
			}
		}
		if (r+c == 4) {
			nxtBottomDia = bottomDia * 10 + board[r][c];
			if (!prefix[nxtBottomDia]) {
				return;
			}
		}
		
		nxtRow = rows[r] + board[r][c] * tenPows[4-c];
		if (!suffix[nxtRow]) {
			return;
		}
		nxtCol = cols[c] + board[r][c] * tenPows[4-r];
		if (!suffix[nxtCol]) {
			return;
		}
		
		if (oidx == 24) {
			int[] cloned = new int[25];
			for (int i = 0, idx = 0; i < 5; i++)
				for (int j = 0; j < 5; j++)
					cloned[idx++] = board[i][j];
			ans.add(cloned);
			return;
		}

		int nextR = ordersR[oidx+1], nextC = ordersC[oidx+1];

		if (r==c) topDia = nxtTopDia;
		if (r+c==4) bottomDia = nxtBottomDia;
		rows[r] = nxtRow;
		cols[c] = nxtCol;
		
		
		for (int i = 9; i >= 0; i--) {
			boolean primeEnd = i == 1 || i == 3 || i == 7 || i == 9;
			if ((nextR == 4 || nextC == 4) && !primeEnd) continue;
			if ((nextR == 0 || nextC == 0) && i == 0) continue;
			board[nextR][nextC] = i;
			dfs(oidx+1);
		}
		board[nextR][nextC] = UNVISITED;
		
		if (r==c) topDia = oldTopDia;
		if (r+c==4) bottomDia = oldBottomDia;
		rows[r] = oldRow;
		cols[c] = oldCol;
	}
	
	static void dfsCaller()
	{
		for (int i = 0; i < 25; i++) {
			ordersR[i] = orders[i] / 5;
			ordersC[i] = orders[i] % 5;
		}
		for (int i = 9; i >= 0; i-=2) {
			if (i==5) continue;
			board[4][4] = i;
			topDia = 0;
			bottomDia = 0;
			Arrays.fill(rows, 0);
			Arrays.fill(cols, 0);
			dfs(0);
		} 
	}
	
	static boolean isPrime(int x)
	{
		if (x == 2) return true;
		if (x % 2 == 0) return false;

		for (int i = 3; i*i <= x; i+=2) {
			if (x % i == 0) return false;
		}
		
		return true;
	}
	
    static void solve()
    {
        sum = ni();
		topLeft = ni();
		calcPows();
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++)
				board[i][j] = UNVISITED;
		int[] digits = new int[5];
		for (int i = 10000; i <= 99999; i++) {
			int val = i;
			int digitSum = 0;
			for (int j = 0; j < 5; j++) {
				digits[4-j] = val % 10;
				val /= 10;
				digitSum += digits[4-j];
			}
			if (digitSum != sum) continue;
			if (!isPrime(i)) continue;
			for (int j = 0, pf = 0; j < 5; j++) {
				pf = pf * 10 + digits[j];
				prefix[pf] = true;
			}
			for (int j = 4, sf = 0, base = 1; j >= 0; j--, base*=10) {
				sf += base * digits[j];
				suffix[sf] = true;
				if (digits[0] == topLeft)
					sufStartWithTopLeft[sf] = true;
			}
		}
		dfsCaller();
		if (ans.isEmpty()) out.print("NONE\n");
		Collections.sort(ans, (a, b) -> {
			for (int i = 0; i < 25; i++) {
				if (a[i] < b[i]) return -1;
				if (a[i] > b[i]) return 1;
			}
			return 0;
		});
		for (int i = 0; i < ans.size(); i++) {
			int[] b = ans.get(i);
			for (int idx = 0; idx < 25; idx++) {
				out.print(b[idx]);
				if (idx % 5 == 4) out.println();
			}
			if (i < ans.size()-1) out.println();
		}
    }
	
	
	// I/O Template
	// static String taskName = null;
	static boolean logTime = !true;
	static String taskName = "prime3";
	static InputStream is;
	static PrintWriter out;
	static String INPUT = "";

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

