/*
ID: wyli1231
LANG: JAVA
TASK: window
*/

import java.io.*;
import java.util.*;

public class window {
    static long area(int[] r)
    {
        long width = r[2] - r[0], height = r[1] - r[3];
        if (width <= 0 || height <= 0) return 0;
        return width * height;
    }
    static int[] overlap(int[] r1, int[] r2)
    {
        r1 = translate(r1);
        r2 = translate(r2);
        int topLeftX = Math.max(r1[0], r2[0]);
        int bottomRightX = Math.min(r1[2], r2[2]);
        int topLeftY = Math.min(r1[1], r2[1]);
        int bottomRightY = Math.max(r1[3], r2[3]);
        int[] ans = new int[]{topLeftX, topLeftY, bottomRightX, bottomRightY};
        return ans;
    }
    static int[] translate(int[] r) // translate any opposite two points to {topLeftX, topLeftY, bottomRightX, bottomRightY}
    {
        int top = Math.max(r[1], r[3]);
        int left = Math.min(r[0], r[2]);
        int bottom = Math.min(r[1], r[3]);
        int right = Math.max(r[0], r[2]);
        return new int[]{left, top, right, bottom};
    }
    static int get(char ch)
    {
        if (ch >= 'a' && ch <= 'z') return ch - 'a';
        else if (ch >= 'A' && ch <= 'Z') return 26 + (ch - 'A');
        else return 52 + (ch - '0');
    }
    static int[] levels = new int[62];
    static int[][] windows = new int[62][];
    static boolean[] exists = new boolean[62];
    static int curTop = 0;
    static int curBottom = -1;
    static void w(String input)
    {
        String[] inputs = input.split(",");
        int idx = get(inputs[0].charAt(0));
        int[] r = new int[4];
        for (int i = 0; i < 4; i++)
            r[i] = Integer.parseInt(inputs[i+1]);
        levels[idx] = curTop++;
        exists[idx] = true;
        windows[idx] = translate(r);
    }
    static long areas(List<int[]> rects)
    {
        if (rects.isEmpty()) return 0;
        long tot = 0;
        int n = rects.size();
        for (int i = 0; i < n; i++) {
            int[] cur = rects.get(i);
            List<int[]> overlaps = new ArrayList<>();
            for (int j = i + 1; j < n; j++) {
                int[] o = overlap(cur, rects.get(j));
                if (area(o) > 0) overlaps.add(o);
            }
            tot += area(cur) - areas(overlaps);
        }
        return tot;
    }
    static long helper(List<int[]> overlaps)
    {
        List<int[]> nexts = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        long tot = 0;
        int n = overlaps.size();
        for (int i = 0; i < n; i++) {
            int[] cur = overlaps.get(i);
            tot += area(cur);
            for (int j = i + 1; j < n; j++) {
                int[] temp = overlap(cur, overlaps.get(j));
                String s = Arrays.toString(temp);
                if (area(temp) > 0 && !seen.contains(s)) {
                    nexts.add(temp);
                    seen.add(s);
                }
            }
        }
        for (int[] o : nexts) {
            tot -= area(o);
        }
        return tot;
    }
    static double s(char ch)
    {
        int idx = get(ch);
        List<int[]> overlaps = new ArrayList<>();
        for (int i = 0; i < 62; i++) {
            if (i == idx || !exists[i] || levels[idx] > levels[i]) continue;
            int[] o = overlap(windows[i], windows[idx]);
            if (area(o) > 0)
                overlaps.add(o);
        }
        long a = area(windows[idx]);
        long visible = a - areas(overlaps);
        double ans = (double) visible / a;
        return ans;
    }
    static void d(char ch)
    {
        exists[get(ch)] = false;
    }
    static void t(char ch)
    {
        levels[get(ch)] = curTop++;
    }
    static void b(char ch)
    {
        levels[get(ch)] = curBottom--;
    }
    static void solve()
    {
        Arrays.fill(exists, false);
        do {
            String input = null;
            try {
                input = ns();
            } catch (Exception e) {
                return;
            }
            if (input == null || input.isEmpty()) return;
            char type = input.charAt(0);
            if (type == 'w') {
                w(input.substring(2, input.length() - 1));
            }
            else if (type == 's') {
                double ans = s(input.charAt(2)) * 100;
                out.printf("%.3f\n", ans);
            }
            else if (type == 'd') {
                d(input.charAt(2));
            }
            else if (type == 't') {
                t(input.charAt(2));
            }
            else if (type == 'b') {
                b(input.charAt(2));
            }
        } while (true);
    }



    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "window";
    
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


