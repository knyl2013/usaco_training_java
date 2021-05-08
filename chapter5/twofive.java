/*
ID: wyli1231
LANG: JAVA
TASK: twofive
*/

import java.io.*;
import java.util.*;

public class twofive {
	static char[] t = new char[25];
	static long[][][][][] memo = new long[6][6][6][6][6];
	static long f(int a, int b, int c, int d, int e)
	{
		int s = a + b + c + d + e;
        if (memo[a][b][c][d][e] != -1) return memo[a][b][c][d][e];
		if (s >= 25) return 1;
		long ans = 0;
		if (a < 5 && (t[a]==0||t[a]==s+'A')) ans += f(a+1,b,c,d,e);
		if (b < a && (t[b+5]==0||t[b+5]==s+'A')) ans += f(a,b+1,c,d,e);
		if (c < b && (t[c+10]==0||t[c+10]==s+'A')) ans += f(a,b,c+1,d,e);
		if (d < c && (t[d+15]==0||t[d+15]==s+'A')) ans += f(a,b,c,d+1,e);
		if (e < d && (t[e+20]==0||t[e+20]==s+'A')) ans += f(a,b,c,d,e+1);
		return memo[a][b][c][d][e] = ans;
	}
    static void clearMemo()
    {
    	for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 5; j++) {
                for (int k = 0; k <= 5; k++) {
                    for (int m = 0; m <= 5; m++) {
                        Arrays.fill(memo[i][j][k][m], -1);
                    }
                }
            }
        }
    }
    static void solve()
    {
        char type = nc();
        if (type == 'W') {
    		clearMemo();
    		char[] w = new char[25];
        	for (int i = 0; i < 25; i++)
            	w[i] = nc();
            long ans = 1;
            int i = 0;
            while (i < 25 && w[i] == 'A'+i) {
            	t[i] = (char) ('A'+i);
            	i++;
            }
            for (; i < 25; i++) {
            	for (t[i] = 'A'; t[i] < w[i]; t[i]++) {
            		ans += f(0, 0, 0, 0, 0);
            		clearMemo();
            	}
            }
            out.printf("%d\n", ans);
        }
        else { // type == 'N'
        	long n = nl();
        	int start = 0;
        	clearMemo();
        	while (true) {
        		int i = start;
        		int[] used = new int[]{i, i-5, i-10, i-15, i-20};
	            for (int j = 0; j < 5; j++) {
	                used[j] = Math.min(used[j], 5);
	                used[j] = Math.max(used[j], 0);
	            }
	            if (f(used[0], used[1], used[2], used[3], used[4]) <= n) break;
	            start++;
        	}
        	for (int i = 0; i < start; i++)
        		t[i] = (char) ('A' + i);
        	for (int i = Math.max(0,start - 1); i < 25; i++) {
        		long x;
				clearMemo();
        		for (t[i] = 'A'; (x = f(0,0,0,0,0)) < n; t[i]++) {
        			n -= x;
        			clearMemo();
        		}
        	}
        	out.printf("%s\n", new String(t));
        }
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = "twofive";
    // static String taskName = null;
    
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

