/*
ID: wyli1231
LANG: JAVA
TASK: concom
*/


import java.io.*;
import java.util.*;

public class concom {
    static int[][] ownMat;
    // companies that company i is able to reach
    static List<Integer> canReach(int i)
    {
        List<Integer> justOwned = new ArrayList<>();
        int[] owns = new int[101];
        boolean[] owned = new boolean[101];
        justOwned.add(i);
        owned[i] = true;

        while (!justOwned.isEmpty()) {
            List<Integer> newComers = new ArrayList<>();
            for (int j : justOwned) {
                for (int nei = 1; nei <= 100; nei++) {
                    if (owned[nei]) continue;
                    owns[nei] += ownMat[j][nei];
                    if (owns[nei] > 50) {
                        newComers.add(nei);
                        owned[nei] = true;
                    }
                }
            }
            justOwned = newComers;
        }

        List<Integer> ans = new ArrayList<>();

        for (int nei = 1; nei <= 100; nei++) {
            if (nei != i && owned[nei]) ans.add(nei);
        }

        return ans;
    }
    // no. of percentage that company i owns company j
    // static int f(int i, int j)
    // {
    //     if (i == j) return 0;
    //     int ans = ownMat[i][j];
    //     for (int c = 1; c <= 100; c++) {
    //         if (c == j || f(i, c) < 50) continue;
    //         ans += ownMat[c][j];
    //     }
    //     return ans;
    // }
    static void solve()
    {
        int n = ni();
        ownMat = new int[101][101];
        for (int i = 0; i < n; i++) {
            int u = ni(), v = ni(), w = ni();
            ownMat[u][v] = w;
        }
        for (int i = 1; i <= 100; i++) {
            for (int j : canReach(i)) {
                out.printf("%d %d\n", i, j);
            }
        }
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "concom";
    
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


