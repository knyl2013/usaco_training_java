/*
ID: wyli1231
LANG: JAVA
TASK: castle
*/
import java.io.*;
import java.util.*;

public class castle {
    static final int MAX = 2501; // maximum no. of groups
    static final int WEST = 0;
    static final int NORTH = 1;
    static final int EAST = 2;
    static final int SOUTH = 3;
    static int h, w, maxGroup;
    static int[] groupCnt = new int[MAX]; // how many group member in this group
    static int[][] dirs = new int[][] {
        new int[] {0, -1},
        new int[] {-1, 0},
        new int[] {0, 1},
        new int[] {1, 0}
    };
    static int[][] walls;
    static int[][] groups; // the room number that (i, j) is in
    
    // check if (i, j) dir wall is blocked or not
    static boolean block(int i, int j, int dir)
    {
        return ((walls[i][j] >> dir) & 1) > 0;
    }
    // fill all rooms connected to (i, j) to groupNo
    static void floodFill(int i, int j, int groupNo)
    {
        groups[i][j] = groupNo;
        groupCnt[groupNo]++;
        if (groupCnt[groupNo] > maxGroup) {
            maxGroup = groupCnt[groupNo];
        }
        for (int go = WEST; go <= SOUTH; go++) {
            int di = i + dirs[go][0], dj = j + dirs[go][1];
            boolean out = di < 0 || dj < 0 || di >= h || dj >= w;
            if (!out && groups[di][dj] == 0 && !block(i, j, go)) {
                floodFill(di, dj, groupNo);
            }
        }
    }
    
    static void solve()
    {
        w = ni(); h = ni();
        int groupNo = 1;
        walls = new int[h][];
        groups = new int[h][w];
        maxGroup = 0;
        for (int i = 0; i < h; i++) {
            walls[i] = na(w);
        }
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (groups[i][j] != 0) continue; // already assigned a group
                floodFill(i, j, groupNo);
                groupNo++;
            }
        }
        int splitMax = maxGroup; // maximum after split
        int splitI = -1, splitJ = -1, splitDir = -1;
        // try to remove walls for every room(i, j) if it's north/east is a different room
        for (int j = 0; j < w; j++) {
            for (int i = h-1; i >= 0; i--) {
                int[] goes = new int[] {NORTH, EAST}; // walls to check
                for (int go : goes) {
                    int di = i + dirs[go][0], dj = j + dirs[go][1];
                    boolean out = di < 0 || dj < 0 || di >= h || dj >= w;
                    if (out || groups[i][j] == groups[di][dj]) continue;
                    int ifGroup = groupCnt[groups[i][j]] + groupCnt[groups[di][dj]];
                    if (ifGroup > splitMax) {
                        splitMax = ifGroup;
                        splitI = i + 1; // ans is one-based
                        splitJ = j + 1;
                        splitDir = go;
                    }
                }
            }
        }


        out.printf("%d\n", groupNo-1);
        out.printf("%d\n", maxGroup);
        out.printf("%d\n", splitMax);
        out.printf("%d %d %s\n", splitI, splitJ, splitDir == NORTH ? "N" : "E");
    }



    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "castle";
    
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


