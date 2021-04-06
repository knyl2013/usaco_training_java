/*
ID: wyli1231
LANG: JAVA
TASK: camelot
*/

import java.io.*;
import java.util.*;

public class camelot {
    static int kingR, kingC;
    static List<int[]> knights;
    static int[] knightDirs = new int[]{-2, -1, 1, 2};
    static int[] kingDirs = new int[]{0, -1, 1};
    static int[][][][] fKnights;
    static int[][] fKings;
    static int[][] totals; // totals[i][j] := total cost gathering all knights into (i, j)
    // totalsVisited[i][j][k][m] := min. cost for all knights to meet at (i, j) while at least one knight has visited (k, m) before (i, j)
    static int[][][][] totalsVisited; 
    static int h, w;
    static final int INF = (int) 1e5;
    // f(kr, kc, mr, mc) := minimum cost such that king walks to (kr, kc)
    //   by himself before being picked up by knight, and finally they all meet at (mr, mc)
    static int f(int kr, int kc, int mr, int mc)
    {
        int ans = INF;
        for (int[] knight : knights) { // this knight will pick up king at (kr, kc) before moving to (mr, mc)
            int cur = totals[mr][mc];
            cur -= fKnights[knight[0]][knight[1]][mr][mc]; // this knight will not go directly to (mr, mc)
            cur += fKnights[knight[0]][knight[1]][kr][kc]; // pick up the king
            cur += fKnights[kr][kc][mr][mc]; // go to (mr, mc) together with king
            ans = Math.min(ans, cur);
        }
        return ans;
    }

    // get total cost gathering all knights into (i, j)
    static int total(int i, int j)
    {
        int cost = 0;

        for (int[] k : knights) {
            // System.out.println(k[0] + " " + k[1] + " " + i + " " + j);
            cost += fKnights[k[0]][k[1]][i][j];
        }

        return cost;
    }
    static int[][] bfs(int i, int j)
    {
        int[][] distances = new int[h][w];
        for (int k = 0; k < h; k++) Arrays.fill(distances[k], INF);
        distances[i][j] = 0;
        Queue<int[]> q = new LinkedList<>();
        int level = 0;
        q.offer(new int[]{i, j});
        while (!q.isEmpty()) {
            int sz = q.size();
            while (sz-- > 0) {
                int[] p = q.poll();
                for (int d1 : knightDirs) {
                    for (int d2 : knightDirs) {
                        if (Math.abs(d1) == Math.abs(d2)) continue;
                        int dr = p[0] + d1, dc = p[1] + d2;
                        boolean out = dr < 0 || dc < 0 || dr >= h || dc >= w;
                        if (out || distances[dr][dc] != INF) continue;
                        distances[dr][dc] = level + 1;
                        q.offer(new int[]{dr, dc});
                    }
                }
            }
            level++;
        }
        return distances;
    }
    static void solve()
    {
        h = ni();
        w = ni();
        // fKnights[i][j][k][m] := fastest knight can go from (i,j) to (k,m)
        fKnights = new int[h][w][][]; 
        totals = new int[h][w];
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
                fKnights[i][j] = bfs(i, j);
        kingC = nc() - 'A';
        kingR = ni() - 1;
        knights = new ArrayList<>();
        // read until empty
        try {
            while (true) {
                int c = nc() - 'A';
                int r = ni() - 1;
                knights.add(new int[]{r, c});
            }
        } catch(Exception e){};
        // base case when there is no knights
        if (knights.isEmpty()) {
            out.printf("0\n");
            return;
        }
        // for (int [][][] r1 : fKnights) 
        //     for (int[][] r2 : r1)
        //         for (int[] r3 : r2)
        //         System.out.println(Arrays.toString(r3));
        
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
                totals[i][j] = total(i, j);
                
        int ans = INF;
        for (int dr = -2; dr <= 2; dr++) {
            for (int dc = -2; dc <= 2; dc++) {
                int extraCost = Math.max(Math.abs(dr), Math.abs(dc));
                int kr = dr + kingR, kc = dc + kingC;
                boolean out = kr < 0 || kc < 0 || kr >= h || kc >= w;
                if (out) continue;
                for (int mr = 0; mr < h; mr++) {
                    for (int mc = 0; mc < w; mc++) {
                        ans = Math.min(ans, f(kr, kc, mr, mc) + extraCost);
                    }
                }
            }
        }    
        out.printf("%d\n", ans);
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "camelot";
    
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


