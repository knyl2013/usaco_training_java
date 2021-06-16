/*
ID: wyli1231
LANG: JAVA
TASK: latin
*/

import java.io.*;
import java.util.*;

public class latin {
	static int n;
	static int[] facts = new int[8];
	static int[][] perms;
	static int[][] board;
	
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
	static boolean valid(int[][] board)
	{
		boolean[][] rowappear = new boolean[n][n];
		boolean[][] colappear = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int val = board[i][j];
				if (rowappear[i][val] || colappear[j][val])
					return false;
				rowappear[i][val] = true;
				colappear[j][val] = true;
			}
		}
		return true;
	}
	static void greedy(int[][] board)
	{
		boolean[][] rowappear = new boolean[n][n];
		boolean[][] colappear = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			colappear[i][i] = true;
		}
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < n; j++) {
				board[i][j] = (i+j)%n;
				// for (int d = 0; d < n; d++) {
					// if (rowappear[i][d] || colappear[j][d])
						// continue;
					// rowappear[i][d] = true;
					// colappear[j][d] = true;
					// board[i][j] = d;
					// break;
				// }
				// if (board[i][j] == -1) throw new RuntimeException(i + ", " + j);
			}
		}
	}
	static boolean[][] cols = new boolean[8][8];
	static long f(int r)
	{
		if (r == n) return 1;
		for (int i = 0; i < n; i++) {
			int val = board[r-1][i];
			cols[i][val] = true;
		}
		long cnt = 0;
		for (int[] perm : perms) {
			int i;
			for (i = 0; i < n; i++) {
				int val = perm[i];
				if (cols[i][val]) break;
			}
			if (i == n) cnt++;
		}
		return cnt * f(r+1);
	}
    static void solve()
    {
        n = ni();
		facts[0] = 1;
		for (int i = 1; i < facts.length; i++)
			facts[i] = facts[i-1] * i;
		permutate();
		board = new int[n][n];
		for (int[] row : board)
			Arrays.fill(row, -1);
		for (int i = 0; i < n; i++)
			board[0][i] = i;
		greedy(board);
		long ans = f(1);
		out.printf("%d\n", ans);
		// for (int[] row : board) {
			// System.out.println(Arrays.toString(row));
		// }
		// System.out.println("valid: " + valid(board));
		
		// for (int[] p : perms) {
			// System.out.println(Arrays.toString(p));
		// }
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


