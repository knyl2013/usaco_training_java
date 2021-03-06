/*
ID: wyli1231
LANG: JAVA
TASK: fence8
*/

import java.io.*;
import java.util.*;

public class fence8 {
    static int[] boards, rails, railPrefix;
    static int n, r;
    static int totBoard;
    static boolean found;
	static List<Map<String, Boolean>> maps;
	
	static String encode()
	{
		StringBuilder sb = new StringBuilder();
        int[] sorted = boards.clone();
        Arrays.sort(sorted);
		for (int i = 0; i < n; i++)
			sb.append(sorted[i]).append('-');
		return sb.toString();
	}
    static boolean dfsid(int limit)
    {
        if (limit == 0 || found) {
            found = true;
            return true;
        }
        int idx = limit - 1;
		String key = encode();
		Map<String, Boolean> mp = maps.get(idx);
		if (mp.containsKey(key)) return mp.get(key);
        if (railPrefix[idx] > totBoard) {
            mp.put(key, false);
            return false;
        }
        int deadSpace = 0;
        for (int i = 0; i < n; i++) {
            if (rails[0] > boards[i]) { // boards[i] is not usable
                deadSpace += boards[i];
            }
        }
        if (railPrefix[idx] > totBoard-deadSpace) {
            mp.put(key, false);
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (boards[i] < rails[idx]) continue;
            boards[i] -= rails[idx];
            totBoard -= rails[idx];
            dfsid(limit-1);
            totBoard += rails[idx];
            boards[i] += rails[idx];
            if (found) {
                mp.put(key, true);
                return true;
            }
        }
        mp.put(key, false);
        return false;
    }
    static void solve()
    {
        n = ni();
        boards = na(n);
        r = ni();
        rails = na(r);
        railPrefix = new int[r];
        Arrays.sort(rails);
        Arrays.sort(boards);
        for (int i = 0; i < n; i++)
            totBoard += boards[i];
        int run = 0;
        for (int i = 0; i < r; i++) {
            run += rails[i];
            railPrefix[i] = run;
        }
        int ans = 0;
        maps = new ArrayList<>();
        for (int j = 0; j < r; j++)
            maps.add(new HashMap<>());
		int lo = 1, hi = r;
		while (lo <= hi) {
			int mid = (lo + hi) / 2;
			found = false;
			if (dfsid(mid)) {
				ans = mid;
				lo = mid + 1;
			}
			else {
				hi = mid - 1;
			}
		}
        out.printf("%d\n", ans);
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
	static String taskName = "fence8";
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


