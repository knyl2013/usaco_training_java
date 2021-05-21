/*
ID: wyli1231
LANG: JAVA
TASK: calfflac
*/


import java.io.*;
import java.util.*;

public class calfflac {
	static char[] arr;
	static int n;
	static int[] mp;
	static int extend(int left, int right)
	{
		int ans = 0;
		while (left >= 0 && right < n && arr[left] == arr[right]) {
			ans+=2;
			left--;
			right++;
		}
		return ans;
	}
	static void solve()
    {
        StringBuilder sb = new StringBuilder();
		int b;
		n = 0;
		while ((b = readByte()) != -1) {
			char ch = (char) b;
			boolean lower = ch >= 'a' && ch <= 'z';
			boolean upper = ch >= 'A' && ch <= 'Z';
			if (lower || upper) n++;
			sb.append((char)b);
		}
		arr = new char[n];
		mp = new int[n];
		int inf = (int) 1e9;
		int ans = 0, start = inf, end = inf;
		for (int i = 0, j = 0; i < sb.length(); i++) {
			char ch = sb.charAt(i);
			boolean lower = ch >= 'a' && ch <= 'z';
			boolean upper = ch >= 'A' && ch <= 'Z';
			if (!lower && !upper) continue;
			mp[j] = i;
			arr[j++] = upper ? Character.toLowerCase(ch) : ch;
		}
		for (int i = 0; i < n; i++) {
			int val1 = extend(i, i) - 1;
			if (val1 > ans || (val1 == ans && mp[i - val1/2] < start)) {
				ans = val1;
				start = mp[i - val1/2];
				end = mp[i + val1/2];
			}
			int val2 = extend(i, i+1);
			if (val2 > ans || (val2 == ans && mp[i - (val2-1)/2] < start)) {
				ans = val2;
				start = mp[i - (val2-1)/2];
				end = mp[i + val2/2];
			}
		}
		out.printf("%d\n", ans);
		for (int i = start; i <= end; i++)
			out.print(sb.charAt(i));
		out.print("\n");
    }
	




    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
	static String taskName = "calfflac";
    
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