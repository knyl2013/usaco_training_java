/*
ID: wyli1231
LANG: JAVA
TASK: betsy
*/


import java.io.*;
import java.util.*;

public class betsy {
    static int n;
    // static boolean[][] visited;
    static int[] dr = new int[]{0, 0, 1, -1};
    static int[] dc = new int[]{1, -1, 0, 0};
    static int nax = 1000000;
    static int[] memo = new int[nax];
    static long end;
    static List<Map<Long, Integer>> dp = new ArrayList<>();
    static int mul(int a, int b)
    {
        long c = a * b;
        return (int)(c % nax);
    }
    // static Map<String, Integer> mp = new HashMap<>();
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
        // int key = mul((int)(bit % nax), r*n+c);
        // if (memo[key] != -1) return memo[key];
        // int val = r * n + c;
        // Map<Long, Integer> curDp = dp.get(val);
        // if (curDp.containsKey(bit)) {
        //     // System.out.println("prune");
        //     return curDp.get(bit);
        // }
        // String key = encode(r, c);
        // if (mp.containsKey(key)) return mp.get(key);
        int ans = 0;
        // visited[r][c] = true;
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i], nc = c + dc[i];
            boolean out = nr < 0 || nc < 0 || nr >= n || nc >= n;
            int nval = nr * n + nc;
            if (out || ((bit >> nval) & 1) == 1) continue;
            ans += dfs(nr, nc, bit | (1L << nval));
        }
        // curDp.put(bit, ans);
        // visited[r][c] = false;
        // mp.put(key, ans);
        // memo[key] = ans;
        return ans;
    }

	static void solve()
    {
        n = ni();
        for (int i = 0; i < n*n; i++) {
            dp.add(new HashMap<>());
        }
        end = (1L << (n*n)) - 1L;
        Arrays.fill(memo, -1);
        // System.out.println(Long.toBinaryString(end));
        // visited = new boolean[n][n];
        int ans = dfs(0, 0, 1);
        out.printf("%d\n", ans);
    }
	




    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
    static boolean logTime = true;
	// static String taskName = "betsy";
    
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