/*
ID: wyli1231
LANG: JAVA
TASK: ariprog
*/

import java.io.*;
import java.util.*;

public class ariprog {
    static int nax = 125001;
    static boolean[] exists = new boolean[nax];
    static void solve()
    {
        int n = ni(), m = ni();
        Set<Integer> s = new TreeSet<>();
        for (int p = 0; p <= m; p++) {
            for (int q = 0; q <= p; q++) {
                s.add(p*p+q*q);
                exists[p*p+q*q] = true;
            }
        }
        List<int[]> ans = new ArrayList<>();
        int[] starts = new int[s.size()];
        int i = 0;
        for (int x : s) starts[i++] = x;
        for (i = 0; i < starts.length; i++) {
            for (int j = 0; j < i; j++) {
                int last = starts[i], secLast = starts[j];
                int b = last - secLast;
                int cur = secLast - b;
                int cnt = 2;
                while (cur >= 0 && cnt < n && exists[cur]) {
                    cnt++;
                    cur -= b;
                }
                if (cnt == n) {
                    ans.add(new int[] {(last-(n-1)*b), b});
                }
            }
        }
        Collections.sort(ans, (a,b)-> {
            if (a[1] != b[1])
                return a[1] - b[1];
            else
                return a[0] - b[0];
        });
        for (int[] a : ans) {
            out.printf("%d %d\n", a[0], a[1]);
        }
        if (ans.isEmpty()) {
            out.printf("NONE\n");
        }
    }



    // static int n;
    // static int[] values;
    // static int[] current;
    // static List<int[]> ans;
    // // ok if current is arithmetic progression
    // static boolean ok() 
    // {
    //     int b = current[1] - current[0];
    //     for (int i = 2; i < n; i++) {
    //         if (current[i] - current[i-1] != b) return false;
    //     }
    //     return true;
    // }
    // static void backtrack(int idx, int start)
    // {
    //     if (idx == n) {
    //         if (ok()) {
    //             ans.add(new int[]{current[0], current[1]-current[0]});
    //         }
    //         return;
    //     }
    //     for (int i = start; i < values.length; i++) {
    //         current[idx] = values[i];
    //         backtrack(idx + 1, i + 1);
    //     }
    // }
    // static void solve()
    // {
    //     n = ni();
    //     int upperBound = ni();
    //     Set<Integer> s = new TreeSet<>();
    //     for (int p = 0; p <= upperBound; p++) {
    //         for (int q = 0; q <= p; q++) {
    //             int val = p*p + q*q;
    //             s.add(val);
    //         }
    //     }
    //     values = new int[s.size()];
    //     current = new int[n];
    //     ans = new ArrayList<>();
    //     int i = 0;
    //     for (int x : s) {
    //         values[i++] = x;
    //     }
    //     backtrack(0, 0);
    //     Collections.sort(ans, (a,b)-> {
    //         if (a[1] != b[1])
    //             return a[1] - b[1];
    //         else
    //             return a[0] - b[0];
    //     });
    //     for (int[] a : ans) {
    //         out.printf("%d %d\n", a[0], a[1]);
    //     }
    // }


    // static final int MAX_M = 255;
    // static boolean[] contain;
    // static int[] starts;
    // static int[][] memo;

    // // static void solve()
    // // {
    // //     int n = ni(), upperBound = ni();
    // //     int maxi = 0;
    // //     Set<Integer> s = new TreeSet<>();
    // //     for (int p = 0; p <= upperBound; p++) {
    // //         for (int q = 0; q <= p; q++) {
    // //             int val = p*p + q*q;
    // //             maxi = Math.max(maxi, val);
    // //             s.add(val);
    // //         }
    // //     }
    // //     // int dum = 0;
    // //     // for (int i = 0; i < maxi*25; i++) {
    // //     //     dum++;
    // //     // }
    // //     System.out.println(maxi);
    // //     System.out.println(s.size());
    // // }

    // // Maximum 'diff' steps can move from 'start'
    // static int dfs(int start, int diff)
    // {
    //     if (!contain[start]) return 0;
    //     // if (memo[start][diff] != 0) return memo[start][diff];
    //     // memo[start][diff] = 1 + dfs(start+diff, diff);
    //     // return memo[start][diff];
    //     return 1 + dfs(start+diff, diff);
    // }
    // static void solve()
    // {
    //     int n = ni(), upperBound = ni();
    //     int maxi = 0;
    //     // int nax = MAX_M*MAX_M*2;
    //     contain = new boolean[MAX_M*MAX_M*2];
    //     List<Integer> starts = new ArrayList<>();
    //     TreeSet<Integer> s = new TreeSet<>();
    //     for (int p = 0; p <= upperBound; p++) {
    //         for (int q = 0; q <= p; q++) {
    //             int val = p*p + q*q;
    //             contain[val] = true;
    //             starts.add(val);
    //             maxi = Math.max(maxi, val);
    //             // s.add(p*p + q*q);
    //         }
    //     }
    //     // int i = 0;
    //     // memo = new int[maxi+1][maxi+1];
    //     // for (int start : s) {
    //     //     starts[i++] = start;
    //     // }
    //     Collections.sort(starts);
    //     // System.out.println(starts);
    //     boolean none = true;
    //     for (int diff = 4; diff <= maxi; diff += 4) {
    //         int prev = -1;
    //         for (int start : starts) {
    //             if (start == prev) continue;
    //             prev = start;
    //             // if (dfs(start, diff) >= n) {
    //             //     none = false;
    //             //     out.printf("%d %d\n", start, diff);
    //             // }
    //             int cnt = 0, current = start;
    //             while (cnt < n && contain[current]) {
    //                 cnt++;
    //                 current += diff;
    //             }
    //             if (cnt == n) {
    //                 none = false;
    //                 out.printf("%d %d\n", start, diff);
    //             }
    //         }
    //     }
    //     if (none) {
    //         out.printf("NONE\n");
    //     }
    // }



    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "ariprog";
    
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


