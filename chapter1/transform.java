/*
ID: wyli1231
LANG: JAVA
TASK: transform
*/

import java.io.*;
import java.util.*;

public class transform {
    static int n;
    static int NINETY = 1;
    static int ONE_EIGHTY = 2;
    static int TWO_SEVENTY = 3;
    static int REFLECTION = 4;
    static int REFLECT_ROTATE = 5;
    static int NO_CHANGE = 6;
    static int INVALID = 7;

    static char[][] rotate(char[][] matrix)
    {
        char[][] ans = new char[n][n];
        int r = 0, c = n - 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[r][c] = matrix[i][j];
                r++;
            }
            c--;
            r = 0;
        }
        return ans;
    }
    static boolean equals(char[][] a, char[][] b) 
    {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] != b[i][j]) return false;
            }
        }
        return true;
    }
    static void display(char[][] matrix)
    {
        for (char[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
    static int canRotate(char[][] ori, char[][] target)
    {
        char[][] r = rotate(ori);
        if (equals(target, r)) {
            return NINETY;
        }
        r = rotate(r);
        if (equals(target, r)) {
            return ONE_EIGHTY;
        }
        r = rotate(r);
        if (equals(target, r)) {
            return TWO_SEVENTY;
        }
        return INVALID;
    }
    static void reverse(char[] arr)
    {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            char tmp = arr[left];
            arr[left] = arr[right];
            arr[right] = tmp;
            left++;
            right--;
        }
    }
    static char[][] reflect(char[][] matrix)
    {
        char[][] ans = new char[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                ans[i][j] = matrix[i][j];
        for (char[] row : ans) {
            reverse(row);
        }
        return ans;
    }
    static int f(char[][] ori, char[][] target)
    {
        int tmp = canRotate(ori, target);
        if (tmp != INVALID) return tmp;
        if (equals(reflect(ori), target)) return REFLECTION;
        if (canRotate(reflect(ori), target) != INVALID) return REFLECT_ROTATE;
        if (equals(ori, target)) return NO_CHANGE;
        return INVALID;
    }
    static void solve()
    {
        n = ni();
        char[][] ori = nm(n, n);
        char[][] target = nm(n, n);
        int ans = f(ori, target);
        out.printf("%d\n", ans);
    }



    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "transform";
    
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


