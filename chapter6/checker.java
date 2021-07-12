/*
ID: wyli1231
LANG: JAVA
TASK: checker
*/


import java.io.*;
import java.util.*;

public class checker {
    static int n;
    static String[] finalAns = new String[3];
    static int finalAnsCnt = 0;
    static int[] board;
    static boolean[] visitedCol;
    static int[][] dia1Grp, dia2Grp;
    static boolean[] dia1, dia2;
	static int[] revs;
	static int callCnt = 0;
	static int[] mem, mem2;
	static Map<String, Integer> mp = new HashMap<>();
	static boolean someLvImpossible(int lv)
	{
		for (int i = lv; i < n; i++) {
			boolean ok = false;
			for (int j = 0; j < n; j++) {
				int grp1 = dia1Grp[i][j];
				int grp2 = dia2Grp[i][j];
				if (visitedCol[j] || dia1[grp1] || dia2[grp2]) continue;
				ok = true;
				break;
			}
			if (!ok) return true;
		}
		return false;
	}
	static String encode(int lv, boolean reverse)
	{
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < lv; i++) {
			if (reverse)
				sb.append(revs[board[i]]);
			else
				sb.append(board[i]);
			sb.append('-');
		}
		
		return sb.toString();
	}
	static int dfs2(int lv)
    {
        if (lv == n) {
            return 1;
        }
		// if (someLvImpossible(lv)) {
			// return 0;
		// }
		// String key = encode(lv, false);
		// String rkey = encode(lv, true);
		// if (mp.containsKey(key)) {
			// return mp.get(key);
		// }
		// if (mp.containsKey(rkey)) {
			// return mp.get(rkey);
		// }
		if (lv == 1 && mem[revs[board[0]]] != -1)
			return mem[revs[board[0]]];
		if (lv == 1 && mem[board[0]] != -1)
			return mem[board[0]];
		boolean firstOnMiddle = n % 2 == 1 && board[0] == n / 2;
		if (firstOnMiddle) {
			if (lv == 2 && mem2[revs[board[1]]] != -1)
				return mem2[revs[board[1]]];
			if (lv == 2 && mem2[board[1]] != -1)
				return mem2[board[1]];
		}
		
		
		callCnt++;
		int ans = 0;
        for (int i = 0; i < n; i++) {
            int grp1 = dia1Grp[lv][i];
            int grp2 = dia2Grp[lv][i];
            if (visitedCol[i] || dia1[grp1] || dia2[grp2]) continue;
            dia1[grp1] = true;
            dia2[grp2] = true;
            visitedCol[i] = true;
            board[lv] = i;

            ans += dfs2(lv + 1);
			// if (lv == 0) {
				// System.out.println("ans: " + ans);
			// }

            dia1[grp1] = false;
            dia2[grp2] = false;
            visitedCol[i] = false;
        }
		
		if (lv == 1) {
			mem[revs[board[0]]] = ans;
			mem[board[0]] = ans;
		}
		if (lv == 2 && firstOnMiddle) {
			mem2[revs[board[1]]] = ans;
			mem2[board[1]] = ans;
		}
		
		// mp.put(key, ans);
		// mp.put(rkey, ans);
		
		return ans;
    }
    static void dfs(int lv)
    {
        if (lv == n) {
            if (finalAnsCnt < 3) {
                StringBuilder sb = new StringBuilder();
                for (int x : board) {
                    sb.append(x+1).append(' ');
                }
                finalAns[finalAnsCnt++] = sb.toString().substring(0, sb.length() - 1);
            }
            else {
                finalAnsCnt++;
            }
            return;
        }
		if (finalAnsCnt >= 3) {
			return;
		}
        for (int i = 0; i < n; i++) {
            int grp1 = dia1Grp[lv][i];
            int grp2 = dia2Grp[lv][i];
            if (visitedCol[i] || dia1[grp1] || dia2[grp2]) continue;
            dia1[grp1] = true;
            dia2[grp2] = true;
            visitedCol[i] = true;
            board[lv] = i;

            dfs(lv + 1);

            dia1[grp1] = false;
            dia2[grp2] = false;
            visitedCol[i] = false;
        }

    }

    static void input()
    {
        n = ni();
    }
    static void fillGrp(int[][] diaGrp, int startR, int startC, int endR, int endC, int dr, int dc, int id)
    {
        diaGrp[startR][startC] = id;
        while (!(startR == endR && startC == endC)) {
            diaGrp[startR][startC] = id;
            startR += dr;
            startC += dc;
        }
        diaGrp[endR][endC] = id;
    }
	static void init()
	{
		board = new int[n];
        visitedCol = new boolean[n];
        dia1Grp = new int[n][n];
        dia2Grp = new int[n][n];
        dia1 = new boolean[n + (n - 1)];
        dia2 = new boolean[n + (n - 1)];
		revs = new int[n];
		mem = new int[n];
		mem2 = new int[n];
		Arrays.fill(mem, -1);
		Arrays.fill(mem2, -1);
		
		for (int i = 0, j = n-1; i < n; i++, j--) {
			revs[i] = j;
		}

        int id, startR, startC, endR, endC;

        id = 0;
        startR = 0;
        startC = 0;
        endR = 0;
        endC = 0;

        while (id < dia1.length) {
            fillGrp(dia1Grp, startR, startC, endR, endC, -1, 1, id);
            if (startR < n-1) {
                startR++;
                endC++;
            }
            else {
                startC++;
                endR++;
            }
            id++;
        }

        id = 0;
        startR = n-1;
        startC = 0;
        endR = n-1;
        endC = 0;
		
		while (id < dia2.length) {
            fillGrp(dia2Grp, startR, startC, endR, endC, 1, 1, id);
            if (startR > 0) {
                startR--;
                endC++;
            }
            else {
                startC++;
                endR--;
            }
            id++;
        }
	}
    static void solve()
    {
		init();
        dfs(0);
		init();
		finalAnsCnt = dfs2(0);
    }
    
    static void output()
    {
        for (String s : finalAns) {
            out.println(s);
        }
        out.println(finalAnsCnt);
    }

    static void myMain()
    {
        input();
        solve();
        output();
		
		if (logTime) {
			out.println("[callCnt: " + callCnt + "]");
		}
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
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
        
        myMain();
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


