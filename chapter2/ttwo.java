/*
ID: wyli1231
LANG: JAVA
TASK: ttwo
*/


import java.io.*;
import java.util.*;

public class ttwo {
    static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
    static char[][] grid;
    static int[][] dirs = new int[][] {
        new int[] {-1, 0},
        new int[] {0, 1},
        new int[] {1, 0},
        new int[] {0, -1}
    };
    static int[] move(int r, int c, int d)
    {
        int dr = dirs[d][0] + r, dc = dirs[d][1] + c;
        boolean out = dr < 0 || dc < 0 || dr >= 10 || dc >= 10;
        if (out || grid[dr][dc] == '*') return new int[] {r, c, (d+1)%4};
        return new int[] {dr, dc, d};
    }
    static void solve()
    {
        grid = new char[10][10];
        int johnDir = NORTH, cowDir = NORTH;
        int johnR = -1, johnC = -1, cowR = -1, cowC = -1;
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                grid[r][c] = nc();
                if (grid[r][c] == 'F') {
                    johnR = r;
                    johnC = c;
                }
                if (grid[r][c] == 'C') {
                    cowR = r;
                    cowC = c;
                }
            }
        }
        Set<String> seen = new HashSet<>();
        seen.add(johnR + " " + johnC + " " + johnDir + " " + cowR + " " + cowC + " " + cowDir);
        int level = 1;
        boolean met = false;
        while (seen.size() < 10*10*16) {
            int[] nextJohn = move(johnR, johnC, johnDir);
            int[] nextCow = move(cowR, cowC, cowDir);
            johnR = nextJohn[0]; johnC = nextJohn[1]; johnDir = nextJohn[2];
            cowR = nextCow[0]; cowC = nextCow[1]; cowDir = nextCow[2];
            if (johnR == cowR && johnC == cowC) { 
                met = true;
                out.printf("%d\n", level);
                break;
            }
            String key = johnR + " " + johnC + " " + johnDir + " " + cowR + " " + cowC + " " + cowDir;
            if (seen.contains(key)) break;
            seen.add(key);
            level++;
        }
        if (!met)
            out.printf("0\n");
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "ttwo";
    
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


