/*
ID: wyli1231
LANG: JAVA
TASK: msquare
*/

import java.io.*;
import java.util.*;

public class msquare {
    static int[] C(int[] x)
    {
        int[] ans = new int[8];
        ans[0] = x[0]; ans[1] = x[6]; ans[2] = x[1]; ans[3] = x[3];
        ans[4] = x[4]; ans[5] = x[2]; ans[6] = x[5]; ans[7] = x[7];
        return ans;
    }
    static int[] B(int[] x)
    {
        int[] ans = new int[8];
        ans[0] = x[3]; ans[1] = x[0]; ans[2] = x[1]; ans[3] = x[2];
        ans[4] = x[5]; ans[5] = x[6]; ans[6] = x[7]; ans[7] = x[4];
        return ans;
    }
    static int[] A(int[] x)
    {
        int[] ans = new int[8];
        for (int i = 0; i < 8; i++) ans[i] = x[7-i];
        return ans;
    }
    static class Tuple {
        int[] x;
        Tuple parent;
        char lastOperation;
        public Tuple(int[] x, Tuple parent, char lastOperation)
        {
            this.x = x;
            this.parent = parent;
            this.lastOperation = lastOperation;
        }
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            Tuple cur = this;
            while (cur.parent != null) {
                sb.append(cur.lastOperation);
                cur = cur.parent;
            }
            return sb.reverse().toString();
        }
    }
    static void solve()
    {
        String goal = Arrays.toString(na(8));
        int[] initial = new int[8];
        for (int i = 0; i < 8; i++) initial[i] = i+1;
        String key = Arrays.toString(initial);
        if (key.equals(goal)) { // base case
            out.printf("0\n\n");
            return;
        }
        Queue<Tuple> q = new LinkedList<>();
        Set<String> seen = new HashSet<>();
        q.offer(new Tuple(initial, null, '*'));
        seen.add(Arrays.toString(initial));
        int level = 1;
        outer:
        while (!q.isEmpty()) {
            int sz = q.size();
            while (sz-- > 0) {
                Tuple p = q.poll();
                Tuple[] next = new Tuple[] {
                    new Tuple(A(p.x), p, 'A'),
                    new Tuple(B(p.x), p, 'B'),
                    new Tuple(C(p.x), p, 'C')
                };
                for (Tuple nxt : next) {
                    key = Arrays.toString(nxt.x);
                    if (seen.contains(key)) continue;
                    if (key.equals(goal)) {
                        out.printf("%d\n", level);
                        out.printf("%s\n", nxt);
                        break outer;
                    }
                    seen.add(key);
                    q.offer(nxt);
                }
            }
            level++;
        }
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "msquare";
    
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


