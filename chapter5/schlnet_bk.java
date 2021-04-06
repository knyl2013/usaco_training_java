/*
ID: wyli1231
LANG: JAVA
TASK: schlnet
*/

import java.io.*;
import java.util.*;

public class schlnet_bk {
    static boolean[][] adj;
    static List<List<Integer>> dp;
    static int n;
    static int maxReach;


    // if we pass the software to school i, what schools will get the software
    static List<Integer> bfs(int i)
    {
        boolean[] visited = new boolean[n];
        Queue<Integer> q = new ArrayDeque<>();
        List<Integer> ans = new ArrayList<>();
        ans.add(i);
        q.offer(i);
        visited[i] = true;
        while (!q.isEmpty()) {
            int p = q.poll();
            for (int j = 0; j < n; j++) {
                if (!adj[p][j] || visited[j]) continue;
                visited[j] = true;
                q.add(j);
                ans.add(j);
            }
        }
        return ans;
    }
    static class A implements Comparable<A> {
        boolean[] visited;
        int cnt;
        int cost;
        public A(boolean[] visited, int cnt) 
        {
            this.visited = visited; 
            this.cnt = cnt;
        }
        public A(boolean[] visited, int cnt, int cost) 
        {
            this.visited = visited; 
            this.cnt = cnt;
            this.cost = cost;
        }
        public double f()
        {
            return cost + ((double)(n - cnt) / maxReach);
        }
        public int compareTo(A other)
        {
            double tf = this.f(), of = other.f();
            if (tf > of) return 1;
            else return -1;
        }
    }
    static int taskA()
    {
        if (maxReach == n) return 1;
        PriorityQueue<A> q = new PriorityQueue<>();
        q.offer(new A(new boolean[n], 0, 0));
        while (!q.isEmpty()) {
            A a = q.poll();
            System.out.println(a.f());
            for (int i = 0; i < n; i++) {
                if (a.visited[i]) continue;
                A na = new A(a.visited.clone(), a.cnt, a.cost + 1);
                List<Integer> news = dp.get(i);
                for (int idx : news) {
                    if (na.visited[idx]) continue;
                    na.cnt++;
                    na.visited[idx] = true;
                }
                if (na.cnt == n) return na.cost;
                q.offer(na);
            }
        }
        return -1;
    }
    // static int taskA()
    // {
    //     Queue<A> q = new ArrayDeque<>();
    //     int level = 0;
    //     q.offer(new A(new boolean[n], 0));

    //     while (!q.isEmpty()) {
    //         int sz = q.size();
    //         while (sz-- > 0) {
    //             A a = q.poll();
    //             for (int i = 0; i < n; i++) {
    //                 if (a.visited[i]) continue;
    //                 List<Integer> news = dp.get(i);
    //                 int newCnt = a.cnt;
    //                 boolean[] newVisited = a.visited.clone();
    //                 for (int idx : news) {
    //                     if (newVisited[idx]) continue;
    //                     newCnt++;
    //                     newVisited[idx] = true;
    //                 }
    //                 if (newCnt == n) return level + 1;
    //                 q.offer(new A(newVisited, newCnt));
    //             }
    //         }
    //         level++;
    //     }
    //     return -1;
    // }
    // return no. of schools that can reach all other schools
    static int count(boolean[][] mat)
    {
        boolean[][] tmp = adj;
        adj = mat;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            if (bfs(i).size() == n) ans++;
        }

        adj = tmp;
        return ans;
    }
    static boolean[][] clone(boolean[][] mat)
    {
        boolean[][] ans = new boolean[mat.length][];
        for (int i = 0; i < ans.length; i++)
            ans[i] = mat[i].clone();
        return ans;
    }
    static class B {
        int cnt;
        boolean[][] adj;
        public B(boolean[][] adj, int cnt) {this.adj = adj; this.cnt = cnt;}
    }
    static int taskB()
    {
        int cnt;
        if ((cnt=count(adj)) == n) return 0;
        // System.out.println(cnt);
        Queue<B> q = new ArrayDeque<>();
        int level = 0;
        q.offer(new B(adj, cnt));
        while (!q.isEmpty()) {
            int sz = q.size();
            while (sz-- > 0) {
                B b = q.poll();
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (b.adj[i][j]) continue;
                        B nb = new B(clone(b.adj), 0);
                        nb.adj[i][j] = true;
                        nb.cnt = count(nb.adj);
                        if (nb.cnt == n) return level + 1;
                        q.offer(nb);
                    }
                }
            }
            level++;
        }
        return -1;
    }
    static void solve()
    {
        n = ni();
        adj = new boolean[n][n];
        dp = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int inp;
            while ((inp = ni()) != 0) {
                adj[i][inp - 1] = true;
            }
        }
        for (int i = 0; i < n; i++) {
            dp.add(bfs(i));
            maxReach = Math.max(maxReach, dp.get(i).size());
        }
        // System.out.println(dp.get(0));
        // int aAns = taskA();
        int bAns = taskB();
        // System.out.println(aAns);
        System.out.println(bAns);
        // out.printf("%d\n%d\n", aAns, bAns);
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
    
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


