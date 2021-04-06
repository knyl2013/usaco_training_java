/*
ID: wyli1231
LANG: JAVA
TASK: ratios
*/

import java.io.*;
import java.util.*;

public class ratios {
    static void solve()
    {
        int[] goals = new int[] {ni(), ni(), ni()};
        int[][] foods = new int[3][];
        int[] current = new int[3];
        if (goals[0] == 0 && goals[1] == 0 && goals[2] == 0) { // base case
            out.printf("0 0 0 0\n");
            return;
        }
        for (int i = 0; i < 3; i++) {
            foods[i] = na(3);
        }
        boolean foundAns = false;
        int[] ans = new int[4];
        for (int a = 0; a < 100; a++) {
            for (int b = 0; b < 100; b++) {
                for (int c = 0; c < 100; c++) {
                    // barley
                    current[0] = a * foods[0][0] + b * foods[1][0] + c * foods[2][0];
                    // oats
                    current[1] = a * foods[0][1] + b * foods[1][1] + c * foods[2][1];
                    // wheat
                    current[2] = a * foods[0][2] + b * foods[1][2] + c * foods[2][2];
                    int desire = -1;
                    boolean allGood = true;
                    for (int i = 0; i < 3; i++) {
                        if (goals[i] == 0) {
                            if (current[i] != 0) {
                                allGood = false;
                                break;
                            }
                        }
                        else if (current[i] % goals[i] != 0) {
                            allGood = false;
                            break;
                        }
                        else {
                            int cur = current[i] / goals[i];
                            if (desire != -1 && cur != desire) {
                                allGood = false;
                                break;
                            }
                            else {
                                desire = cur;
                            }
                        }
                    }
                    allGood = allGood && desire > 0; // we can't make empty mixture
                    if (allGood) {
                        if (!foundAns || a+b+c < ans[0]+ans[1]+ans[2]) {
                            ans[0] = a;
                            ans[1] = b;
                            ans[2] = c;
                            ans[3] = desire;
                        }
                        foundAns = true;
                    }
                }
            }
        }
        if (foundAns) {
            out.printf("%d %d %d %d\n", ans[0], ans[1], ans[2], ans[3]);
        }
        else {
            out.printf("NONE\n");
        }
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "ratios";
    
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


