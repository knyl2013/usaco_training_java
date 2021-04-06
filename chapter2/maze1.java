/*
ID: wyli1231
LANG: JAVA
TASK: maze1
*/


import java.io.*;
import java.util.*;

public class maze1 {
    static int unvisited = (int) 1e9;
    static int h, w;
    static char[][] grid;
    static int[][] dirs = new int[][] {
        new int[] {-1, 0}, // UP
        new int[] {1, 0}, // DOWN
        new int[] {0, -1}, // LEFT
        new int[] {0, 1} // RIGHT
    };
    // dir's bit 1 = can go, no otherwise
    static int canGo(int r, int c)
    {
        int ans = 0;
        int gr = r * 2 + 1;
        int gc = c * 2 + 1;
        for (int i = 0; i < 4; i++) {
            int dr = dirs[i][0] + gr;
            int dc = dirs[i][1] + gc;
            if (grid[dr][dc] == ' ') {
                ans = ans | (1 << i);
            }
        }
        return ans;
    }
    static boolean isExit(int r, int c)
    {
        int gr = r * 2 + 1;
        int gc = c * 2 + 1;
        for (int[] d : dirs) {
            int dr = d[0] + gr;
            int dc = d[1] + gc;
            boolean bound = dr == 0 || dc == 0 || dr == h*2 || dc == w*2;
            if (grid[dr][dc] == ' ' && bound) 
                return true;
        }
        return false;
    }
    static void bfs(int r, int c, int[][] table)
    {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{r, c});
        table[r][c] = 1;
        int level = 2;
        while (!q.isEmpty()) {
            int sz = q.size();
            while (sz-- > 0) {
                int[] p = q.poll();
                int bit = canGo(p[0], p[1]); // where can (p[0], p[1]) go?
                for (int i = 0; i < 4; i++) {
                    if (((bit >> i) & 1) == 0) continue;
                    int dr = p[0] + dirs[i][0];
                    int dc = p[1] + dirs[i][1];
                    boolean out = dr < 0 || dc < 0 || dr >= h || dc >= w;
                    if (out || table[dr][dc] != unvisited) continue;
                    table[dr][dc] = level;
                    q.offer(new int[]{dr, dc});
                }
            }
            level++;
        }

    }
    static void solve()
    {
        w = ni(); h = ni();
        grid = new char[h*2+1][];
        int[][] table1 = new int[h][w]; // shortest distance from exit1 to (r, c)
        int[][] table2 = new int[h][w];
        for (int r = 0; r < h; r++) {
            Arrays.fill(table1[r], unvisited);
            Arrays.fill(table2[r], unvisited);
        }
        List<Integer[]> exits = new ArrayList<>();
        for (int r = 0; r < h*2+1; r++) {
            String s = ns().substring(1);
            if (s.length() < w*2+1) {
                grid[r] = new char[w*2+1];
                for (int i = 0; i < grid[r].length; i++) {
                    grid[r][i] = ' ';
                }
            } else {
                grid[r] = s.toCharArray();
            }
        }
        // for (int r = 0; r < h*2+1; r++) {
        //     System.out.println(Arrays.toString(grid[r]));
            
        // }
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                if (isExit(r, c)) {
                    exits.add(new Integer[]{r, c});
                }
            }
        }
        // assert(exits.size() == 2);
        bfs(exits.get(0)[0], exits.get(0)[1], table1);
        if (exits.size() >= 2)
            bfs(exits.get(1)[0], exits.get(1)[1], table2);
        int ans = 0;
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                int d1 = table1[r][c];
                int d2 = table2[r][c];
                ans = Math.max(ans, Math.min(d1, d2));
            }
        }
        out.printf("%d\n", ans);
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "maze1";
    
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
        int b;
        StringBuilder sb = new StringBuilder();
        boolean includeSpaces = true;
        if (includeSpaces) {
            b = ' ';
            while (!(isSpaceChar(b) && b != ' ')) {
                sb.appendCodePoint(b);
                b = readByte();
            }
        } else {
            b = skip();
            while(!(isSpaceChar(b))){ // when nextLine, (isSpaceChar(b) && b != ' ')
                sb.appendCodePoint(b);
                b = readByte();
            }
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


