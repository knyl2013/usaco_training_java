/*
ID: wyli1231
LANG: JAVA
TASK: fence
*/


import java.io.*;
import java.util.*;

public class fence {
    static final int MAX = 500;
    static int[][] adjMat = new int[MAX][MAX];
    static int[] degrees = new int[MAX];
    static int[] ans;
    static int ansIdx = 0;
    // Eulerian path / circuit
    static void dfs(int i)
    {
        List<Integer> neis = new ArrayList<>();
        for (int j = 0; j < MAX; j++) {
            if (adjMat[i][j] > 0 && adjMat[j][i] > 0) {
                neis.add(j);
            }
        }
        if (neis.isEmpty()) {
            ans[ansIdx++] = i;
        }
        else {
            for (int nei : neis) {
                if (adjMat[i][nei] > 0 && adjMat[nei][i] > 0) {
                    adjMat[i][nei]--;
                    adjMat[nei][i]--;
                    dfs(nei);
                }
            }
            ans[ansIdx++] = i;
        }
    }
    static void solve()
    {
        
        int f = ni();
        for (int i = 0; i < f; i++) {
            int u = ni() - 1, v = ni() - 1;
            adjMat[u][v]++;
            adjMat[v][u]++;
            degrees[u]++;
            degrees[v]++;
        }
        int start = -1;
        int mini = MAX + 1;
        for (int i = 0; i < MAX; i++) {
            if (degrees[i] % 2 == 1) {
                start = i;
                break;
            }
            else {
                mini = Math.min(mini, i);
            }
        }
        if (start == -1) start = mini; // if no odd degree vertex found, use the minimum vertex (Eulerian circuit)
        ans = new int[f + 1];
        dfs(start);
        for (int i = f; i >= 0; i--) {
            out.printf("%d\n", ans[i]+1); // answer is 1-based
        }
    }


    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "fence";
    
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


