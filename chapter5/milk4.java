/*
ID: wyli1231
LANG: JAVA
TASK: milk4
*/

import java.io.*;
import java.util.*;

public class milk4 {
    static class Record {
        Record parent;
        int x;
        int cap;
        public Record(int x) {this.x = x; this.parent = null; this.cap = -1;}
        public Record(int x, Record parent, int cap) {this.x = x; this.parent = parent; this.cap = cap;}
        public int compareTo(Record other)
        {
            List<Integer> caps = Record.getCaps(this);
            List<Integer> otherCaps = Record.getCaps(other);
            for (int i = 0; i < caps.size(); i++) {
                if (caps.get(i) < otherCaps.get(i)) return -1;
                if (caps.get(i) > otherCaps.get(i)) return 1;
            }
            return 0;
        }
        public static List<Integer> getCaps(Record r)
        {
            List<Integer> rs = new ArrayList<>();
            while (r.cap != -1) {
                rs.add(r.cap);
                r = r.parent;
            }
            Collections.sort(rs);
            return rs;
        }        
    }
    
    static int[] pails;
    static Object[] bfs(int target)
    {
        Queue<Record> q = new ArrayDeque<>();
        q.offer(new Record(target));
        int lv = 1;
        while (!q.isEmpty()) {
            int sz = q.size();
            List<Record> candidates = new ArrayList<>();
            while (sz-- > 0) {
                Record r = q.poll();
                for (int pail : pails) {
                    if (r.x % pail == 0) candidates.add(new Record(0, r, pail));
                    if (!candidates.isEmpty()) continue;
                    for (int px = pail; px <= r.x; px += pail) {
                        q.offer(new Record(r.x - px, r, pail));
                    }
                }
            }
            if (!candidates.isEmpty()) {
                Record ans = null;
                for (Record r : candidates) {
                    if (ans == null || r.compareTo(ans) < 0) ans = r;
                }
                return new Object[]{lv, ans};
            }
            lv++;
        }
        return null;
    }

    static void solve()
    {
        int q = ni(), p = ni();
        pails = na(p);
        Object[] ans = bfs(q);
        Integer lv = (Integer) ans[0];
        Record r = (Record) ans[1];
        List<Integer> rs = Record.getCaps(r);
        out.printf("%d ", lv);
        for (int i = 0; i < lv; i++) {
            boolean newLine = i == lv - 1;
            out.print(rs.get(i));
            out.print(newLine ?  "\n" : " ");
        }
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "milk4";
    
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


