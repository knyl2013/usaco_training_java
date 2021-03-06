/*
ID: wyli1231
LANG: JAVA
TASK: cowxor
*/


import java.io.*;
import java.util.*;

public class cowxor {
	static int[] dp = new int[1 << 22];
	static int[] answer(int[] arr)
	{
		Arrays.fill(dp, 0);
		int n = arr.length;
		int xor = 0;
        int ans = -1, start = -1, end = -1;
		for (int i = 0; i < dp.length; i = i*2+2)
			dp[i] = 1;
		for (int i = 0; i < n; i++) {
            xor ^= arr[i];
			int cur = 0;
            int val = 0;
            for (int j = 20; j >= 0; j--) {
                int bit = (xor >> j) & 1;
				int on = cur * 2 + 1, off = cur * 2 + 2;
				int want = (bit ^ 1) == 1 ? on : off;
				if (dp[want] != 0) {
					cur = want;
					val = val | (1 << j);
				}
                else {
					cur = (want == on) ? off : on;
                }
            }
            if (ans < val) {
                ans = val;
                start = dp[cur];
                end = i + 1;
            }
			cur = 0;
            for (int j = 20; j >= 0; j--) {
                int bit = (xor >> j) & 1;
				int next = (bit == 1) ? cur * 2 + 1 : cur * 2 + 2;
				cur = next;
				dp[cur] = 1;
            }
			
			dp[cur] = i + 2;
        }

		return new int[]{ans, start, end};
	}
	
	static void solve()
    {
        int[] arr = na(ni());
		int[] ans = answer(arr);
		out.printf("%d %d %d\n", ans[0], ans[1], ans[2]);
    }
	




    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
	static String taskName = "cowxor";
    
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
static class TrieNode {
        TrieNode[] children;
        int start;
        public TrieNode() 
        {
            children = new TrieNode[2];
            start = -1;
        }
    }
	static int[] brute(int[] arr)
	{
		int n = arr.length;
        int xor = 0;
        int ans = -1, start = -1, end = -1;
        TrieNode root = new TrieNode();
        for (int i = 0; i < n; i++) {
            xor ^= arr[i];
            TrieNode cur = root;
            int val = 0;
            for (int j = 20; j >= 0 && i > 0; j--) {
                int bit = (xor >> j) & 1;
                int want = bit ^ 1;
                if (cur.children[want] != null) {
                    cur = cur.children[want];
                    val = val | (1 << j);
                }
                else {
                    cur = cur.children[bit];
                }
            }
            if (i > 0 && ans < val) {
                ans = val;
                start = cur.start;
                end = i + 1;
            }
            if (ans < arr[i]) {
                ans = arr[i];
                start = i + 1;
                end = i + 1;
            }

            cur = root;
            for (int j = 20; j >= 0; j--) {
                int bit = (xor >> j) & 1;
                if (cur.children[bit] == null)
                    cur.children[bit] = new TrieNode();
                cur = cur.children[bit];
            }
            cur.start = i + 2;
        }
		return new int[]{ans, start, end};
	}
	static int[] randomArr(int n, int lo, int hi)
    {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) ((Math.random() * (hi - lo + 1)) + lo);
        }
        return ans;
    }
*/
