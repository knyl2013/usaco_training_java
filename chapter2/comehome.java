/*
ID: wyli1231
LANG: JAVA
TASK: comehome
*/
import java.io.*;
import java.util.*;

public class comehome {
    static List<List<Integer>> adjList;
    static List<List<Integer>> weightList;
    // shortest path from 'from' to any Z
    static int shortest(int from)
    {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->Integer.compare(a[0],b[0]));
        int[] distances = new int[128];
        Arrays.fill(distances, Integer.MAX_VALUE);
        boolean[] seen = new boolean[128];
        pq.offer(new int[]{0, from});
        while (!pq.isEmpty()) {
            int[] p = pq.poll();
            if (seen[p[1]]) continue;
            seen[p[1]] = true;
            if (p[1] == 'Z') return p[0];
            List<Integer> neis = adjList.get(p[1]);
            List<Integer> weights = weightList.get(p[1]);
            for (int i = 0; i < neis.size(); i++) {
                int nei = neis.get(i), w = weights.get(i);
                if (!seen[nei])
                    pq.offer(new int[]{w+p[0], nei});
            }
        }
        throw new RuntimeException("No path has been found");
    }
    static void solve()
    {
        adjList = new ArrayList<>();
        weightList = new ArrayList<>();
        for (int i = 0; i < 128; i++) {
            adjList.add(new ArrayList<>());
            weightList.add(new ArrayList<>());
        }
        for (int P = ni(); P >= 1; P--) {
            char u = nc(), v = nc();
            int w = ni();
            if (u == v) continue;
            adjList.get(u).add((int)v);
            weightList.get(u).add(w);

            adjList.get(v).add((int)u);
            weightList.get(v).add(w);
        }
        char label = ' ';
        int best = Integer.MAX_VALUE;
        for (int c = 'A'; c <= 'Y'; c++) {
            if (adjList.get(c).isEmpty()) continue;
            int cur = shortest(c);
            if (cur < best) {
                best = cur;
                label = (char) c;
            }
        }

        out.printf("%c %d\n", label, best);
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "comehome";
    
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


