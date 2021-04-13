/*
/*
ID: wyli1231
LANG: JAVA
TASK: stall4
*/

import java.io.*;
import java.util.*;

public class stall4 {
	static final int MAX = 405;
	static final long INF = (long) 1e15;
	static long[][] capMat = new long[MAX][MAX];
	static List<List<Integer>> adjList;
    
    
    static long maxFlow(int source, int sink)
    {
        if (source == sink) return INF;
        int n = adjList.size();
        long total = 0;
        while (true) {
            int[] prevNodes = new int[n];
            Arrays.fill(prevNodes, -1);
            long[] flows = new long[n];
            boolean[] visited = new boolean[n];
            flows[source] = INF;
            long maxFlow = 0;
            int maxLoc = -1;
            while (true) {
                maxFlow = 0;
                maxLoc = -1;
                // find the unvisited node with highest capacity
                for (int i = 0; i < n; i++) {
                    if (flows[i] > maxFlow && !visited[i]) {
                        maxFlow = flows[i];
                        maxLoc = i;
                    }
                }
                if (maxLoc == -1) break;
                if (maxLoc == sink) break;
                visited[maxLoc] = true;
                for (int i : adjList.get(maxLoc)) {
                    if (flows[i] < Math.min(maxFlow, capMat[maxLoc][i])) {
                        prevNodes[i] = maxLoc;
                        flows[i] = Math.min(maxFlow, capMat[maxLoc][i]);
                    }
                }
            }
            // no path
            if (maxLoc == -1) break;
            long pathCap = flows[sink];
			 
            total += pathCap;
            int cur = sink;
            while (cur != source) {
                int next = prevNodes[cur];
                capMat[next][cur] -= pathCap;
                if (capMat[cur][next] == 0) {
                    adjList.get(cur).add(next);
                }
                capMat[cur][next] += pathCap;
                cur = next;
            }
        }
        
        return total;
    }
	
    static void solve()
    {
        int n = ni(), m = ni();
		adjList = new ArrayList<>();
		for (int i = 0; i <= MAX; i++) adjList.add(new ArrayList<>());
		for (int cow = 0; cow < n; cow++) {
			int size = ni();
			while (size-- > 0) {
				int stall = (ni() - 1) + n; // stall id becomes n-based so it won't conflict with cow id
				adjList.get(cow).add(stall);
				capMat[cow][stall] = 1;
			}
		}
		int source = n + m + 1, sink = n + m + 2;
		for (int cow = 0; cow < n; cow++) {
			adjList.get(source).add(cow);
			capMat[source][cow] = 1;
		}
		for (int i = 0; i < m; i++) {
			int stall = i + n;
			adjList.get(stall).add(sink);
			capMat[stall][sink] = 1;
		}
		long ans = maxFlow(source, sink);
		out.printf("%d\n", ans);
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    //static String taskName = null;
	static String taskName = "stall4";
    
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

