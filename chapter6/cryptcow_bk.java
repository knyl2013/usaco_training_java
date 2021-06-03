/*
ID: wyli1231
LANG: JAVA
TASK: cryptcow
*/

import java.io.*;
import java.util.*;

public class cryptcow {
	static String start = "Begin the Escape execution at the Break of Dawn";
	static int[] freq;
	static final int LIMIT = 75;
	static char[] current, encrypted;
	static Set<String> seen;
	static int n;
	static boolean found;
	static int getTimes()
	{
		freq = new int[128];
		for (int i = 0; i < n; i++) {
			freq[encrypted[i]]++;
		}
		for (int i = 0; i < start.length(); i++) {
			freq[start.charAt(i)]--;
		}
		for (int i = 0; i < 128; i++) {
			char ch = (char) i;
			if (ch == 'C' || ch == 'O' || ch == 'W') continue;
			if (freq[i] > 0) return -1;
		}
		boolean allEquals = freq['C'] == freq['O'] && freq['O'] == freq['W'];
		if (!allEquals) return -1;
		return freq['C'];
	}
	static void encrypt(int cIdx, int oIdx, int wIdx, int curLen)
	{
		char[] newCurrent = new char[current.length];
		int idx = 0;
		while (idx < cIdx) {
			newCurrent[idx] = current[idx];
			idx++;
		}
		newCurrent[idx++] = 'C';
		for (int i = oIdx+1; i <= wIdx; i++) {
			newCurrent[idx++] = current[i];
		}
		newCurrent[idx++] = 'O';
		for (int i = cIdx; i <= oIdx; i++) {
			newCurrent[idx++] = current[i];
		}
		newCurrent[idx++] = 'W';
		for (int i = wIdx+1; i < curLen; i++) {
			newCurrent[idx++] = current[i];
		}
		
		current = newCurrent;
	}
	static void show(int len)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++)
			sb.append(current[i]);
		System.out.println(sb);
	}
	static String encode(int curLen)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < curLen; i++)
			sb.append(current[i]);
		return sb.toString();
	}
	static boolean dfs(int curLen)
	{
		if (found) return true;
		if (curLen == n) {
			boolean isEqual = true;
			for (int i = 0; i < n; i++) {
				if (current[i] != encrypted[i]) return false;
			}
			found = true;
			return true;
		}
		String key = encode(curLen);
		if (seen.contains(key)) return false;
		seen.add(key);
		if (key.startsWith("CB"))show(curLen);
		char[] backup = current.clone();
		for (int cIdx = 0; cIdx < curLen; cIdx++) {
			for (int oIdx = cIdx; oIdx < curLen; oIdx++) {
				for (int wIdx = oIdx; wIdx < curLen; wIdx++) {
					encrypt(cIdx, oIdx, wIdx, curLen);
					// if (curLen+3==n)
						// show(curLen + 3);
					if (dfs(curLen + 3)) return true;
					for (int i = 0; i < backup.length; i++) {
						current[i] = backup[i];
					}
				}
			}
		}
		return false;
	}
	static boolean dfsCaller()
	{
		current = new char[75];
		for (int i = 0; i < start.length(); i++) {
			current[i] = start.charAt(i);
		}
		found = false;
		int curLen = start.length();
		return dfs(curLen);
	}
	static int[] answer()
	{
		if (new String(encrypted).equals(start)) return new int[]{1, 0};
		int times = getTimes();
		if (times == -1) return new int[]{0, 0};
		if (times*3 > LIMIT-start.length()) return new int[]{0, 0};
		if (start.length()+times*3 != n) return new int[]{0, 0};
		if (!dfsCaller()) return new int[]{0, 0};
		return new int[]{1, times};
	}
    static void solve()
    {
		// System.out.println(start.length());
		seen = new HashSet<>();
		StringBuilder sb = new StringBuilder();
		int b;
		try {
			while (true) {
				b = readByte();
				char ch = (char) b;
				boolean isSpace = ch == ' ';
				boolean lower = ch >= 'a' && ch <= 'z';
				boolean upper = ch >= 'A' && ch <= 'Z';
				if (isSpace || lower || upper)
					sb.append(ch);
			}
		}
		catch (Exception e) {
			
		}
		// System.out.println(sb);
		n = sb.length();
		// System.out.println(n);
		encrypted = new char[n];
		for (int i = 0; i < n; i++)
			encrypted[i] = sb.charAt(i);
		int[] ans = answer();
        out.printf("%d %d\n", ans[0], ans[1]);
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
	// static String taskName = "cryptcow";
    
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

