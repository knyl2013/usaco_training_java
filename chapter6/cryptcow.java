/*
ID: wyli1231
LANG: JAVA
TASK: cryptcow
*/

import java.io.*;
import java.util.*;

public class cryptcow {
	static String start = "Begin the Escape execution at the Break of Dawn";
	static final int LIMIT = 75;
	static boolean found = false;
	static int nax = (int) 1e5+7;
	static boolean[] cache = new boolean[nax];
	static boolean[] cache2 = new boolean[nax];
	static int[] pPows = new int[75];
	static int p = 53;
	static void calcPows()
	{
		pPows[0] = 1;
		for (int i = 1; i < 75; i++)
			pPows[i] = mul(pPows[i-1], p);
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
		return (int)(c%nax);
	}
	static int hash(String s)
	{
		int ans = 0;
		int len = s.length();
		for (int i = 0; i < len; i++) {
			int val = s.charAt(i);
			ans = add(ans, mul(pPows[i], val));
		}
		return ans;
	}
	static int hash(String s, int start, int end)
	{
		int ans = 0;
		for (int i = start, pidx = 0; i < end; i++, pidx++) {
			int val = s.charAt(i);
			ans = add(ans, mul(pPows[pidx], val));
		}
		return ans;
	}
	
	static int getTimes(String encrypted)
	{
		int[] freq = new int[128];
		for (int i = 0; i < encrypted.length(); i++) {
			freq[encrypted.charAt(i)]++;
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
	
	static String decrypt(String s, int cIdx, int oIdx, int wIdx)
	{
		int len = s.length(), idx = 0;
		char[] ans = new char[len-3];
		for (int i = 0; i < cIdx; i++)
			ans[idx++] = s.charAt(i);
		for (int i = oIdx+1; i < wIdx; i++)
			ans[idx++] = s.charAt(i);
		for (int i = cIdx+1; i < oIdx; i++)
			ans[idx++] = s.charAt(i);
		for (int i = wIdx+1; i < len; i++)
			ans[idx++] = s.charAt(i);
		return new String(ans);
	}
	static boolean substringFail(String current)
	{
		int h = 0, pidx = 0;
		int len = current.length();
		for (int i = 0; i < len; i++) {
			char ch = current.charAt(i);
			if (ch == 'C' || ch == 'O' || ch == 'W') {
				if (h > 0 && !cache2[h]) {
					return true;
				}
				h = 0;
				pidx = 0;
			}
			else {
				h = add(h, mul(pPows[pidx++], (int)ch));
			}
		}
		return false;
	}
	static boolean prefixFail(String current)
	{
		int idx = 0;
		for (int i = 0; i < current.length(); i++) {
			char ch = current.charAt(i);
			if (ch == 'C' || ch == 'O' || ch == 'W') break;
			if (start.charAt(idx++) != ch) return true;
		}
		return false;
	}
	static boolean isSuffixBalance(String current)
	{
		int len = current.length();
		for (int i = len-1; i >= 0; i--) {
			char ch = current.charAt(i);
			if (ch == 'O' || ch == 'C') return false;
			if (ch == 'W') return true;
		}
		return true;
	}
	static boolean isPrefixBalance(String current)
	{
		int len = current.length();
		for (int i = 0; i < len; i++) {
			char ch = current.charAt(i);
			if (ch == 'O' || ch == 'W') return false;
			if (ch == 'C') return true;
		}
		return true;
	}
	static boolean suffixFail(String current)
	{
		int idx = start.length()-1;
		for (int i = current.length()-1; i >= 0; i--) {
			char ch = current.charAt(i);
			if (ch == 'C' || ch == 'O' || ch == 'W') break;
			if (start.charAt(idx--) != ch) return true;
		}
		return false;
	}

	static String encodeCow(String current)
	{
		int len = current.length();
		int k = len - start.length();
		char[] ans = new char[k];
		int idx = 0;
		for (int i = 0; i < len; i++) {
			char ch = current.charAt(i);
			if (ch == 'C' || ch == 'O' || ch == 'W')
				ans[idx++] = ch;
		}
		return new String(ans);
	}
	static boolean dfs(String current)
	{
		if (found) return true;
		if (current.length() == start.length()) {
			if (current.equals(start)) {
				found = true;
				return true;
			}
			return false;
		}
		int k = (current.length() - start.length()) / 3;
		if (prefixFail(current) || suffixFail(current)) return false;
		if (!isPrefixBalance(current) || !isSuffixBalance(current)) return false;
		if (substringFail(current)) return false;

		int h = hash(current);
		if (cache[h]) return false;
		cache[h] = true;

		int cPt = 0, oPt = 0, wPt = 0;
		int[] cIdxs = new int[k], oIdxs = new int[k], wIdxs = new int[k];

		for (int i = 0; i < current.length(); i++) {
			char ch = current.charAt(i);
			if (ch == 'C')
				cIdxs[cPt++] = i;
			else if (ch == 'O')
				oIdxs[oPt++] = i;
			else if (ch == 'W')
				wIdxs[wPt++] = i;
		}
		oPt = 0;
		wPt = 0;
		for (cPt = 0; cPt < k; cPt++) {
			int cIdx = cIdxs[cPt];
			while (oPt < k && oIdxs[oPt] < cIdx) oPt++;
			if (oPt == k) {
				return false;
			}
			while (wPt < k && wIdxs[wPt] < oIdxs[oPt]) wPt++;
			if (wPt == k) {
				return false;
			}
			for (int i = oPt; i < k; i++) {
				int oIdx = oIdxs[i];
				for (int j = k-1; j >= wPt; j--) {
					int wIdx = wIdxs[j];
					if (wIdx < oIdx) break;
					if (dfs(decrypt(current, cIdx, oIdx, wIdx))) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	static int[] answer(String encrypted)
	{
		if (encrypted.equals(start)) return new int[]{1, 0};
		int times = getTimes(encrypted);
		if (times == -1) return new int[]{0, 0};
		if (times*3 > LIMIT-start.length()) return new int[]{0, 0};
		if (start.length()+times*3 != encrypted.length()) return new int[]{0, 0};
		calcPows();
		for (int i = 0; i < start.length(); i++) {
			for (int j = i; j < start.length(); j++) {
				int h = hash(start, i, j+1);
				cache2[h] = true;
			}
		}
		if (!dfs(encrypted)) return new int[]{0, 0};
		return new int[]{1, times};
	}
	
    static void solve()
    {
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
		String encrypted = sb.toString();
		int[] ans = answer(encrypted);
        out.printf("%d %d\n", ans[0], ans[1]);
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
	static boolean logTime = !true;
	static String taskName = "cryptcow";
    
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

