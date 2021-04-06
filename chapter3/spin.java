/*
ID: wyli1231
LANG: JAVA
TASK: spin
*/


import java.io.*;
import java.util.*;

public class spin {
    static final int DONE = 0, NOT_DONE = 1, REPEAT = 2;
    static Set<String> seen = new HashSet<>();
    static int[][][] wedges;
    static int[] speeds;
    

    // return whether there is a common intersections among wheels
    static int check(int second)
    {
        // wheels[i][x] := i-th wheel has opening on x degree
        boolean[][] wheels = new boolean[5][360];
        int[] starts = new int[5];
        for (int i = 0; i < 5; i++) {
            starts[i] = (wedges[i][0][0] + (speeds[i] * second)) % 360;
            for (int j = 0; j < wedges[i].length; j++) {
                int start = (wedges[i][j][0] + (speeds[i] * second)) % 360;
                int size = wedges[i][j][1];

                for (int k = 0; k <= size; k++) {
                    int pos = (start + k) % 360;
                    wheels[i][pos] = true;
                }
            }
        }
        String key = Arrays.toString(starts);
        if (seen.contains(key)) {
            return REPEAT;
        }
        seen.add(key);
        for (int x = 0; x < 360; x++) {
            boolean allOk = true;
            for (int i = 0; i < 5; i++) {
                if (!wheels[i][x]) {
                    allOk = false;
                    break;
                }
            }    
            if (allOk) return DONE;
        }
        return NOT_DONE;
    }
    static void solve()
    {
        wedges = new int[5][][];
        speeds = new int[5];
        for (int i = 0; i < 5; i++) {
            speeds[i] = ni();
            int w = ni();
            wedges[i] = new int[w][2];
            for (int j = 0; j < w; j++) {
                int startAngle = ni(), angleSize = ni();
                wedges[i][j][0] = startAngle;
                wedges[i][j][1] = angleSize;
            }
        }
        int second = 0, status;
        while ((status = check(second)) == NOT_DONE) {
            second++;
        }
        if (status == REPEAT) {
            out.printf("none\n");
        }
        else {
            out.printf("%d\n", second);
        }
    }









    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "spin";
    
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


