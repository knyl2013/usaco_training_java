/*
ID: wyli1231
LANG: JAVA
TASK: betsy
*/


import java.io.*;
import java.util.*;

public class betsy {
    static int n;
    static boolean[][] visited, fillTable;
    static int visitedCnt;
    static int[] dr = new int[]{0, 0, 1, -1};
    static int[] dc = new int[]{1, -1, 0, 0};
    static long end;
    static Map<Long, Integer> mp;
    static int fcnt;
    static int want;
    static boolean ffSuccess;
    static int[][] bottomRightBadSample = new int[][] {
        new int[]{-1, 1, 0},
        new int[]{1, 0, 0},
        new int[]{0, 0, 0},
    };
	static int[][] bottomLeftBadSample = new int[][] {
		new int[]{1, 1, -1},
		new int[]{0, -1, 0},
		new int[]{0, 0, 1}
	};

    static void floodClose(int r, int c)
    {
        fillTable[r][c] = false;
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i], nc = c + dc[i];
            boolean out = nr < 0 || nc < 0 || nr >= n || nc >= n;
            boolean isMarket = nr == n-1 && nc == 0;
            if (out || isMarket || visited[nr][nc] || !fillTable[nr][nc]) continue;
            floodClose(nr, nc);
        }
    }
	static int startR, startC;
    static void floodFill(int r, int c)
    {
        fillTable[r][c] = true;
        fcnt++;
        if (fcnt == want) {
            ffSuccess = true;
        }
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i], nc = c + dc[i];
            boolean out = nr < 0 || nc < 0 || nr >= n || nc >= n;
            boolean isMarket = nr == n-1 && nc == 0;
            if (out || isMarket || visited[nr][nc] || fillTable[nr][nc]) continue;
            floodFill(nr, nc);
			if (r == startR && c == startC) break;
        }
    }
    // true if all unvisited cells are connected 4-directionally
    static boolean allConnected(int r, int c)
    {
		boolean verticalSafe = (r-1>=0&&!visited[r-1][c]) || (r+1<n&&!visited[r+1][c]);
		boolean horizontalSafe = (c-1>=0&&!visited[r][c-1]) || (c+1<n&&!visited[r][c+1]);
		if (verticalSafe && horizontalSafe) return true;
        fcnt = 0;
        ffSuccess = false;
        want = n * n - visitedCnt;
		startR = r;
		startC = c;
		for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                fillTable[i][j] = false;
        floodFill(r, c);
        return ffSuccess;
    }

    // true if border and (n-1, 0) cannot reach in one path
    static boolean isBorderBad(int r, int c)
    {
        boolean hasPrevUnVisit, isPrevVisited, isAtStart;

        // right border
        hasPrevUnVisit = false;
        isPrevVisited = true;
        for (int j = n-1, i = 0; i < n; i++) {
            if (visited[i][j]) {
                if (hasPrevUnVisit) return true;
                isPrevVisited = true;
                continue;
            }
            isAtStart = i == r && j == c;
            if (visited[i][j-1] && isPrevVisited && !isAtStart) {
                return true;
            }
            hasPrevUnVisit = true;
            isPrevVisited = false;
        }

        // left border
        hasPrevUnVisit = false;
        isPrevVisited = true;
        for (int j = 0, i = 0; i < n; i++) {
            if (visited[i][j]) {
                if (hasPrevUnVisit) return true;
                isPrevVisited = true;
                continue;
            }
            isAtStart = i == r && j == c;
            if (visited[i][j+1] && isPrevVisited && !isAtStart) {
                return true;
            }
            hasPrevUnVisit = true;
            isPrevVisited = false;
        }

        // top border
        hasPrevUnVisit = false;
        isPrevVisited = true;
        for (int j = 0, i = 0; j < n; j++) {
            if (visited[i][j]) {
                if (hasPrevUnVisit) return true;
                isPrevVisited = true;
                continue;
            }
            isAtStart = i == r && j == c;
            if (visited[i+1][j] && isPrevVisited && !isAtStart) {
                return true;
            }
            hasPrevUnVisit = true;
            isPrevVisited = false;
        }

        // bottom border
        hasPrevUnVisit = false;
        isPrevVisited = true;
        for (int j = n-1, i = n-1; j >= 0; j--) {
            if (visited[i][j]) {
                if (hasPrevUnVisit) return true;
                isPrevVisited = true;
                continue;
            }
            isAtStart = i == r && j == c;
            if (visited[i-1][j] && isPrevVisited && !isAtStart) {
                return true;
            }
            hasPrevUnVisit = true;
            isPrevVisited = false;
        }
        

        return false;
    }

    // return true if current map has a tunnel (1 way in/out path)
    static boolean hasTunnel(int r, int c)
    {
        for (int i = 1; i < n-1; i++) {
            for (int j = 1; j < n-1; j++) {
                if (visited[i][j] || (r == i && c == j)) 
                    continue;
                if ((visited[i-1][j] || visited[i+1][j]) && visited[i][j-1] && visited[i][j+1]) {
                    return true;
                }
                if ((visited[i][j-1] || visited[i][j+1]) && visited[i+1][j] && visited[i-1][j]) {
                    return true;
                }
            }
        }
        return false;
    }
	static boolean onlyOneWayOut(int r, int c)
	{
		// boolean hasUnvisit = false;
		for (int i = 0; i < r; i++) {
			int vcnt = 0;
			for (int j = 0; j < n; j++) {
				if (visited[i][j])
					vcnt++;
			}
			if (vcnt == n-1) return true;
		}
		for (int j = n-1; j > c; j--) {
			int vcnt = 0;
			for (int i = 0; i < n; i++) {
				if (visited[i][j])
					vcnt++;
			}
			if (vcnt == n-1) return true;
		}
		return false;
	}
	static boolean isEndBlock(int r, int c)
	{
		boolean endRight = r == n-1 && c == 1;
		boolean endUp = r == n-2 && c == 0;
		if (!endRight && !endUp) return false;
		if (endRight)
			return visited[n-2][0] && (n*n)-visitedCnt > 1;
		else
			return visited[n-1][1] && (n*n)-visitedCnt > 1;
	}
	static boolean equals(int[][] ref, int startR, int startC)
	{
		for (int i = 0; i < ref.length; i++) {
			for (int j = 0; j < ref[i].length; j++) {
				if (ref[i][j] == -1) continue;
				int val = visited[startR+i][startC+j] ? 1 : 0;
				if (val != ref[i][j]) return false;
			}
		}
		return true;
	}
	static boolean bottomRightBad()
	{
		if (n < 5) return false;
		return equals(bottomRightBadSample, n-3, n-3);
	}
	static boolean bottomLeftBad(int r, int c)
	{
		if (n < 5) return false;
		return equals(bottomLeftBadSample, n-3, 0);
	}
    // how many ways to go to (n-1, 0) if cur pos is (r, c) and bit is visited
    static int dfs(int r, int c, long bit)
    {
        // System.out.println(Long.toBinaryString(bit));
        if (bit == end) {
            return (r == n-1 && c == 0) ? 1 : 0;
        }
        if (r == n-1 && c == 0) {
            return 0;
        }
		if (isEndBlock(r, c)) {
			return 0;
		}
        if (isBorderBad(r, c)) {
            return 0;
        }
        if (hasTunnel(r, c)) {
            return 0;
        }
		if (onlyOneWayOut(r, c)) {
			return 0;
		}
		if (bottomRightBad()) {
			return 0;
		}
		if (bottomLeftBad(r, c)) {
			return 0;
		}
        if (!allConnected(r, c)) {
            return 0;
        }
		long key = bit * (n*n) + (n*r+c);

		int mr, mc;

		if (mp.containsKey(key)) return mp.get(key);

        int ans = 0;
        visited[r][c] = true;
        visitedCnt++;
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i], nc = c + dc[i];
            boolean out = nr < 0 || nc < 0 || nr >= n || nc >= n;
            int nval = nr * n + nc;
            if (out || ((bit >> nval) & 1) == 1) continue;
			ans += dfs(nr, nc, bit | (1L << nval));
        }
		
        visitedCnt--;
        visited[r][c] = false;
        mp.put(key, ans);
        return ans;
    }
	static int helper(int inp)
	{
		n = inp;
        end = (1L << (n*n)) - 1L;
        visitedCnt = 1;
        visited = new boolean[n][n];
        fillTable = new boolean[n][n];
		mp = new HashMap<>(88418 * 2);
		return dfs(0, 0, 1);
	}
	static void solve()
    {
		int inp = ni();
        int ans = helper(inp);
        out.printf("%d\n", ans);
    }
	




    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static boolean logTime = !true;
	static String taskName = "betsy";
    
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