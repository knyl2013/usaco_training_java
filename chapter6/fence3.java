/*
ID: wyli1231
LANG: JAVA
TASK: fence3
*/

import java.io.*;
import java.util.*;

public class fence3 {
    static int n;
    static int[][] fences;
    static double dist(double x1, double y1, double x2, double y2)
    {
        double xt = x1-x2, yt = y1-y2;
        return Math.sqrt((xt*xt) + (yt*yt));
    }
    static double calcDist(double cenX, double cenY)
    {
        double ans = 0;
        
        for (int[] fence : fences) {
            boolean vertical = fence[0] == fence[2];
            double add;
            if (vertical) {
                int startY = Math.min(fence[1],fence[3]), endY = Math.max(fence[1],fence[3]);
                int fenceX = fence[0];
                boolean inBetween = cenY >= startY && cenY <= endY;
                if (inBetween) {
                    add = Math.abs(cenX-fenceX);
                }
                else if (cenY <= startY) {
                    add = dist(fenceX, startY, cenX, cenY);
                }
                else {
                    add = dist(fenceX, endY, cenX, cenY);
                }
            }
            else {
                int startX = Math.min(fence[0], fence[2]), endX = Math.max(fence[0], fence[2]);
                int fenceY = fence[1];
                boolean inBetween = cenX >= startX && cenX <= endX;
                if (inBetween) {
                    add = Math.abs(cenY-fenceY);
                }
                else if (cenX <= startX) {
                    add = dist(startX, fenceY, cenX, cenY);
                }
                else {
                    add = dist(endX, fenceY, cenX, cenY);
                }
            }
            ans += add;
        }
        return ans;
    }

    static void solve()
    {
        n = ni();
        fences = new int[n][];
        double totX = 0, totY = 0;
        for (int i = 0; i < n; i++) {
            fences[i] = na(4);
            totX += fences[i][0];
            totX += fences[i][2];
            totY += fences[i][1];
            totY += fences[i][3];
        }
        double cenX = totX / (2*n), cenY = totY / (2*n);
        double step = 100;
        double e = step;
        int cnt = 100;
        while (e > 0.001) {
            double cur = calcDist(cenX, cenY);
            double nxtX = cenX, nxtY = cenY;
            for (int i = 0; i < 2; i++) {
                boolean positive = i == 0;
                double delta = positive ? e : -e;
                for (int j = 0; j < 2; j++) {
                    boolean toX = j == 0;
                    double dx = toX ? delta : 0;
                    double dy = toX ? 0 : delta;
                    double nxt;
                    if ((nxt=calcDist(cenX+dx, cenY+dy)) < cur) {
                        cur = nxt;
                        nxtX = cenX+dx;
                        nxtY = cenY+dy;
                    }
                }
            }
            if (nxtX == cenX && nxtY == cenY) {
                e /= 2;
            }
            else {
                cenX = nxtX;
                cenY = nxtY;
            }
        }
        
        out.printf("%.1f %.1f %.1f\n", cenX, cenY, calcDist(cenX, cenY));


    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "fence3";
    
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


