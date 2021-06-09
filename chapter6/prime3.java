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
	static int[][] board = new int[5][5];
	static int cnt = 0;
	static int topLeft, sum;
	static int p = 11;
	static int[] pPows = new int[30];
	static final int UNVISITED = 107;
	static boolean[] seen = new boolean[1];
	static String[] strings = new String[1];
	static Set<String> seenStr = new HashSet<>();
	static Set<Long> seenLong = new HashSet<>();
	static int topDia = 0, bottomDia = 0;
	static int[] rows = new int[5];
	static int[] cols = new int[5];
	
	static int[] tenPows = new int[6];
	static int hash(int p)
	{
		int ans = 0;
		int pp = 1;
		for (int i = 0, pidx = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (board[i][j] == UNVISITED) return ans;
				ans = add(ans, mul(board[i][j], pp));
				pp = mul(pp, p);
			}
		}
		return ans;
	}
	static long hashLong()
	{
		long ans = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (board[i][j] == UNVISITED) return ans;
				ans = ans * p + board[i][j];
			}
		}
		return ans;
	}
	static String hashStr()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++) {
				if (board[i][j] == UNVISITED) return sb.toString();
				sb.append(board[i][j]).append('-');
			}
		return sb.toString();
			
	}
	static int add(int a, int b)
	{
		int c = a + b;
		if (c >= nax) c -= nax;
		return c;
	}
	static int mul(int a, int b)
	{
		long c = a * b;
		return (int)(c % nax);
	}
	static void calcPows()
	{
		pPows[0] = 1;
		for (int i = 1; i < pPows.length; i++) {
			pPows[i] = mul(p, pPows[i-1]);
		}
		tenPows[0] = 1;
		for (int i = 1; i < tenPows.length; i++) {
			tenPows[i] = tenPows[i-1] * 10;
		}
	}
	static void printBoard()
	{
		for (int[] row : board) {
			System.out.println(Arrays.toString(row));
		}
	}
	static void backtrack(int r, int c)
	{
		// boolean debug = key.startsWith("7-2-4-3-1-4-2-2-2-7-2-4-5-3-3-3-8-3");
		// if (debug) printBoard();
		// boolean debug = false;
		// System.out.println(r + " " + c);
		
		// for (int[] row : board) {
			// System.out.println(Arrays.toString(row));
		// }
		// System.out.println();
		// String key = hashStr();
		String key = "";
		boolean debug = !true && key.startsWith("1-1-3-5-1-1-4-0");
		// int val;
		if (debug) {
			System.out.println("before prune: " + key);
			System.out.println(r + ", " + c);
			System.out.println(rows[r] + " " + cols[c] + " " + topDia + " " + bottomDia);
		}
		int nxtTopDia = 0, nxtBottomDia = 0;
		int nxtRow = 0, nxtCol = 0;
		int oldTopDia = 0, oldBottomDia = 0;
		int oldRow = 0, oldCol = 0;
		if (r == c) {
			nxtTopDia = topDia * 10 + board[r][c];
			if (!prefix[nxtTopDia]) {
				if (debug) System.out.println("A");
				return;
			}
		}
		if (r+c == 4) {
			nxtBottomDia = bottomDia + tenPows[r] * board[r][c];
			if (!suffix[nxtBottomDia]) {
				if (debug) {
					System.out.println("B " + nxtBottomDia);
				}
				return;
			}
			// val = 0;
			// for (int ri = 0, ci = 4, base = 1; ri <= r; ri++, ci--, base*=10) {
				// val += base * board[ri][ci];
				// if (!suffix[val]) {
					// if (debug) System.out.println("B: " + val);
					// return;
				// }
			// }
		}
		nxtRow = rows[r] * 10 + board[r][c];
		if (!prefix[nxtRow]) {
			if (debug) System.out.println("C");
			return;
		}
		nxtCol = cols[c] * 10 + board[r][c];
		if (!prefix[nxtCol]) {
			if (debug) System.out.println("D");
			return;
		}
		
		// val = 0;
		// for (int i = 0; i <= c; i++) {
			// val = val * 10 + board[r][i];
			// if (!prefix[val]) {
				// if (debug) System.out.println("C: " + val);
				// return;
			// }
		// }
		// val = 0;
		// for (int i = 0; i <= r; i++) {
			// val = val * 10 + board[i][c];
			// if (!prefix[val]) {
				// if (debug) System.out.println("D: " + val);
				// return;
			// }
		// }
		// String sh = hashStr();
		// int key = hash(11);
		// if (seen[key]) return;
		// seen[key] = true;
		
		if (debug) System.out.println("after prune: " + key);
		// if (seenStr.contains(key)) {
			// System.out.println("prune");
			// return;
		// }
		// seenStr.add(key);
		// printBoard();
		// long lh = hashLong();
		// if (seenLong.contains(lh)) return;
		// seenLong.add(lh);
		if (r == 4 && c == 4) {
			cnt++;
			if (cnt>1) out.println();
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					out.print(board[i][j]);
					if (j==4) out.println();
				}
			}
			return;
		}
		int nextR = r, nextC = c + 1;
		if (nextC == 5) {
			nextR = r + 1;
			nextC = 0;
		}
		if (nextR == 5) return;
		if (r==c) oldTopDia = topDia;
		if (r+c==4) oldBottomDia = bottomDia;
		oldRow = rows[r];
		oldCol = cols[c];
		
		if (r==c) topDia = nxtTopDia;
		if (r+c==4) bottomDia = nxtBottomDia;
		rows[r] = nxtRow;
		cols[c] = nxtCol;
		
		
		
		for (int i = 0; i <= 9; i++) {
			board[nextR][nextC] = i;
			backtrack(nextR, nextC);
		}
		
		if (r==c) topDia = oldTopDia;
		if (r+c==4) bottomDia = oldBottomDia;
		rows[r] = oldRow;
		cols[c] = oldCol;
		board[nextR][nextC] = UNVISITED;
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
				// System.out.println(pf);
				prefix[pf] = true;
			}
			for (int j = 4, sf = 0, base = 1; j >= 0; j--, base*=10) {
				sf += base * digits[j];
				suffix[sf] = true;
			}
			// System.out.println(Arrays.toString(digits));
		}
		for (int i = 0; i < 5; i++)
			Arrays.fill(board[i], UNVISITED);
		board[0][0] = topLeft;
		Arrays.fill(rows, 0);
		Arrays.fill(cols, 0);
		// rows[0] = topLeft;
		// cols[0] = topLeft;
		// topDia = topLeft;
		backtrack(0, 0);
		if (cnt == 0) out.print("NONE\n");
		// System.out.println(prefix[11]);
		// System.out.println(prefix[11]);
		// System.out.println(suffix[17111]);
		// System.out.println(prefix[7243]);
		// System.out.println(prefix[72431]);
		// System.out.println(prefix[7]);
		// System.out.println(prefix[2]);
		// System.out.println(prefix[4]);
		// System.out.println(prefix[3]);
		// System.out.println(prefix[1]);
		// System.out.println(suffix[1]);
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
	static boolean logTime = true;
	// static String taskName = "prime3";
    
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

