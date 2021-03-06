/*
ID: wyli1231
LANG: JAVA
TASK: lamps
*/


import java.io.*;
import java.util.*;

public class lamps {
    static int n;
    static char[] values;
    static Set<String> seen;
    static Set<String> ones; // settings that appear on level one
    static void button1()
    {
        for (int i = 0; i < n; i++) {
            values[i] = values[i] == '1' ? '0' : '1';
        }
    }
    static void button2()
    {
        for (int i = 0; i < n; i+=2) {
            values[i] = values[i] == '1' ? '0' : '1';
        }
    }
    static void button3()
    {
        for (int i = 1; i < n; i+=2) {
            values[i] = values[i] == '1' ? '0' : '1';
        }
    }
    static void button4()
    {
        for (int i = 0; i < n; i+=3) {
            values[i] = values[i] == '1' ? '0' : '1';
        }
    }

    // assume we still have 'level' button to click, where can we go?
    static void dfs(int level)
    {
        String key = new String(values);
        if (seen.contains(key)) return;
        seen.add(key);
        if (level == 1) ones.add(key);
        if (level == 0) return;
        button1();
        dfs(level - 1);
        button1(); // restore

        button2();
        dfs(level - 1);
        button2(); // restore

        button3();
        dfs(level - 1);
        button3(); // restore

        button4();
        dfs(level - 1);
        button4(); // restore

    }
    static void solve()
    {
        n = ni();
        int c = ni();
        boolean[] ons = new boolean[n]; // ons[i] true if final i-th bit must be on
        boolean[] offs = new boolean[n];
        int cur;
        while ((cur = ni()) != -1) {
            ons[cur - 1] = true;
        }
        while ((cur = ni()) != -1) {
            offs[cur - 1] = true;
        }
        values = new char[n];
        for (int i = 0; i < n; i++) values[i] = '1';
        seen = new TreeSet<>();
        ones = new HashSet<>();
        dfs(c);
        boolean impossible = true;
        for (String s : seen) {
            if (ones.contains(s)) continue;
            boolean allGood = true;
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == '1' && offs[i]) {
                    allGood = false;
                    break;
                }
                if (s.charAt(i) == '0' && ons[i]) {
                    allGood = false;
                    break;
                }
            }
            if (!allGood) continue;
            impossible = false;
            out.printf("%s\n", s);
        }
        if (impossible) out.printf("IMPOSSIBLE\n");
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "lamps";
    
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


