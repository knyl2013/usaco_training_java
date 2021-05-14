/*
ID: wyli1231
LANG: JAVA
TASK: vans
*/

import java.io.*;
import java.util.*;
import java.math.*;

public class vans {
    static BigInteger[] memo, sums;
    static BigInteger h(int n)
	{
		if (n == 0) return BigInteger.valueOf(1);
		if (n == 1) return BigInteger.valueOf(0);
		if (n == 2) return BigInteger.valueOf(3);
		return BigInteger.valueOf((n - 1) * 2);
	}
	static void solve()
	{
		int n = ni();
		memo = new BigInteger[n];
		sums = new BigInteger[n];
		
		for (int i = 0; i <= n-2; i++) {
			if (i <= 2)
				memo[i] = h(i);
			else
				memo[i] = memo[i-1]
							.add(i-3<0?BigInteger.valueOf(0):sums[i-3])
							.add(i-4<0?BigInteger.valueOf(0):sums[i-4])
							.add(memo[i-2].multiply(h(2)));
			sums[i] = i-1<0?memo[i]:memo[i].add(sums[i-1]);
		}
		
		BigInteger ans = BigInteger.valueOf(0);
		for (int i = 0; i <= n-2; i++)
			ans = ans.add(memo[i].multiply(BigInteger.valueOf(2*((n-2)-i+1))));
        out.printf("%s\n", ans);
	}

    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "vans";
    
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
/*
brute force approach - O(2^n)
static int[][] dirs = new int[][] {
        new int[]{1, 0},
        new int[]{-1, 0},
        new int[]{0, 1},
        new int[]{0, -1}
    };
    static boolean[][] visited;
    static void dfs(int x, int y, int vcnt)
    {
        if (x == 0 && y == 0 && visited[x][y]) {
            if (vcnt == n * 4) cnt++;
            return;
        }
        if (visited[x][y]) return;
        visited[x][y] = true;
        for (int[] dir : dirs) {
            int dx = x + dir[0], dy = y + dir[1];
            boolean out = dx < 0 || dy < 0 || dx >= 4 || dy >= n;
            if (out) continue;
            dfs(dx, dy, vcnt+1);
        }
        visited[x][y] = false;
    }
    static int brute()
    {
        visited = new boolean[4][n];
        cnt = 0;
        dfs(0, 0, 0);
        return cnt;
    }

suboptimal approach - O(n^2)
static BigInteger caller()
    {
        BigInteger ans = BigInteger.valueOf(0);
		n -= 2;
        for (int left = 0; left <= n; left++) {
            for (int right = 0; right <= n; right++) {
                if (left+right > n) continue;
                ans = ans.add(f(n-left-right).multiply(BigInteger.valueOf(2)));
            }
        }
		return ans;
    }

recursive approach - O(n)
static BigInteger sum(int end)
{
	if (end < 0) return BigInteger.valueOf(0);
	if (sums[end] != null) return sums[end];
	return sums[end] = f(end).add(sum(end-1));
}
static BigInteger f(int n)
{
	if (n <= 2) return h(n);
	if (memo[n] != null) return memo[n];
	BigInteger ans = f(n - 1);
	ans = ans.add(sum(n-3)).add(sum(n-4)).add(f(n-2).multiply(h(2)));
	return memo[n] = ans;
}
static BigInteger caller(int n)
{
	BigInteger ans = BigInteger.valueOf(0);
	for (int i = 0; i <= n-2; i++)
		ans = ans.add(f(i).multiply(BigInteger.valueOf(2*((n-2)-i+1))));
	return ans;
}
static void solve()
{
	int n = ni();
	memo = new BigInteger[n];
	sums = new BigInteger[n];
	out.printf("%s\n", caller(n));
}
*/