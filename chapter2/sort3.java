/*
ID: wyli1231
LANG: JAVA
TASK: sort3
*/

import java.io.*;
import java.util.*;
// Time: O(N)
public class sort3 {

    static int cntOne, cntTwo, cntThree;
    
    // return after sorting, what arr[idx] should be? based on
    // arr[0..cntOne-1] = 1, arr[cntOne..cntTwo-1] = 2, arr[cntTwo..cntThree-1] = 3
    static int getPos(int idx)
    {
        if (idx < cntOne) return 1;
        else if (idx < (cntOne+cntTwo)) return 2;
        else return 3;
    }
    static void solve()
    {
        int n = ni();
        int[] arr = na(n);
        cntOne = 0; cntTwo = 0; cntThree = 0;
        for (int x : arr) {
            if (x == 1) cntOne++;
            else if (x == 2) cntTwo++;
            else cntThree++;
        }
        // oneWentTwo := no. of elements that is expected to be in one position but ended up in two position
        int oneWentTwo = 0, oneWentThree = 0;
        int twoWentThree = 0, twoWentOne = 0;
        int threeWentOne = 0, threeWentTwo = 0;

        for (int i = 0; i < n; i++) {
            int expect = getPos(i); // after sorting arr[i] is expected to be ...
            int actual = arr[i];
            if (expect == actual) continue;
            if (actual == 1 && expect == 2) oneWentTwo++;
            if (actual == 1 && expect == 3) oneWentThree++;
            if (actual == 2 && expect == 1) twoWentOne++;
            if (actual == 2 && expect == 3) twoWentThree++;
            if (actual == 3 && expect == 1) threeWentOne++;
            if (actual == 3 && expect == 2) threeWentTwo++;
        }
        int ans = 0;
        // first deal with the cheap case, e.g. oneWentTwo & twoWentOne -> only one swap can make both sorted
        if (oneWentTwo > 0 && twoWentOne > 0) {
            int cost = Math.min(oneWentTwo, twoWentOne);
            ans += cost;
            oneWentTwo -= cost;
            twoWentOne -= cost;
        }
        if (oneWentThree > 0 && threeWentOne > 0) {
            int cost = Math.min(oneWentThree, threeWentOne);
            ans += cost;
            oneWentThree -= cost;
            threeWentOne -= cost;
        }
        if (twoWentThree > 0 && threeWentTwo > 0) {
            int cost = Math.min(twoWentThree, threeWentTwo);
            ans += cost;
            twoWentThree -= cost;
            threeWentTwo -= cost;
        }
        
        // lastly deal with expensive case (if there is any)
        // e.g. oneWentTwo & twoWentThree & threeWentOne
        // needs two swaps for each to make all sorted: 
        // [3, 1, 2] -> [1, 3, 2] -> [1, 2, 3]
        // these types are chained with three, so looking one is sufficient
        ans += (oneWentTwo+oneWentThree) * 2;

        out.printf("%d\n", ans);
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "sort3";
    
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


