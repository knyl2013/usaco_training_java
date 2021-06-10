/*
ID: wyli1231
LANG: JAVA
TASK: prime3
*/

import java.io.*;
import java.util.*;

public class prime3_bk2 {
	static int nax = (int)1e6+7;
	static boolean[] prefix = new boolean[nax];
	static boolean[] suffix = new boolean[nax];
	static int[][] board = new int[5][5];
	static int cnt = 0;
	static int topLeft, sum;
	static int topDia = 0, bottomDia = 0;
	static int[] cols = new int[5];
	static int[] tenPows = new int[6];
	static List<Integer> firstRows = new ArrayList<>();
	static List<Integer> others = new ArrayList<>();
	static boolean checkOk(int r, int c)
	{
		int nxtTopDia = 0, nxtBottomDia = 0, nxtCol = 0;
		if (r == c) {
			nxtTopDia = topDia * 10 + board[r][c];
			if (!prefix[nxtTopDia]) {
				return false;
			}
		}
		if (r+c == 4) {
			nxtBottomDia = bottomDia + tenPows[r] * board[r][c];
			if (!suffix[nxtBottomDia]) {
				return false;
			}
		}
		nxtCol = cols[c] * 10 + board[r][c];
		if (!prefix[nxtCol]) {
			return false;
		}
		if (r == c) topDia = nxtTopDia;
		if (r+c == 4) bottomDia = nxtBottomDia;
		cols[c] = nxtCol;
		return true;
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
	static void backtrack(int idx)
	{
		if (idx == 5) {
			// out.println(Arrays.toString(cols));
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
		int[] oldCols = cols.clone();
		int oldBottomDia = bottomDia, oldTopDia = topDia;
		List<Integer> choices = idx == 0 ? firstRows : others;

		for (int choice : choices) {
			int didx = 4;
			boolean canGo = true;
			while (choice > 0) {
				board[idx][didx] = choice % 10;
				if (!checkOk(idx, didx)) {
					canGo = false;
					break;
				}
				choice /= 10;
				didx--;
			}
			if (canGo) backtrack(idx + 1);
			for (int i = 0; i < 5; i++) {
				cols[i] = oldCols[i];
			}
			bottomDia = oldBottomDia;
			topDia = oldTopDia;
		}
	}
	
    static void solve()
    {
        sum = ni();
		topLeft = ni();
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
			}
			if (digits[0] == topLeft) firstRows.add(i);
			others.add(i);
		}
		// for (int i = 0; i < 5; i++)
		// 	Arrays.fill(board[i], UNVISITED);
		// board[0][0] = topLeft;
		// Arrays.fill(rows, 0);
		// Arrays.fill(cols, 0);
		// rows[0] = topLeft;
		// cols[0] = topLeft;
		// topDia = topLeft;
		// System.out.println(firstRows.size());
		// System.out.println(others.size());
		tenPows[0] = 1;
		for (int i = 1; i < tenPows.length; i++) {
			tenPows[i] = tenPows[i-1]*10;
		}
		backtrack(0);
		// backtrack(0, 0);
		if (cnt == 0) out.print("NONE\n");
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

