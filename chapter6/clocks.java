/*
ID: wyli1231
LANG: JAVA
TASK: clocks
*/


import java.io.*;
import java.util.*;

public class clocks {
    
    static int[][] board = new int[3][3];
    static String finalAns = "";

    static int encode(int[][] b)
    {
        int ans = 0;
        for (int i = 2; i >= 0; i--) {
            for (int j = 2; j >= 0; j--) {
                int val = b[i][j] == 12 ? 0 : b[i][j] / 3;
                ans = (ans << 2) + val;
            }
        }
        return ans;
    }

    static int[][] decode(int key)
    {
        int[][] ans = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int last = key & 1;
                key >>= 1;
                int secLast = (key & 1) << 1;
                key >>= 1;
                int val = last + secLast;
                ans[i][j] = val;
            }
        }
        return ans;
    }
    // Rotate i-th clock by 90 degree
    static int rotate(int b, int i)
    {
        int right = (b >> (i * 2)) & 1;
        int left = (b >> (i * 2 + 1)) & 1;
        int val = right + (left << 1);
        if (++val == 4) val = 0;
        int newRight = val & 1;
        int newLeft = (val >> 1) & 1;
        if (right != newRight)
            b = b ^ (1 << (i * 2));
        if (left != newLeft)
            b = b ^ (1 << (i * 2 + 1));
        return b;
    }
    static String[] moves = new String[] {
        "ABDE",
        "ABC",
        "BCEF",
        "ADG",
        "BDEFH",
        "CFI",
        "DEGH",
        "GHI",
        "EFHI"
    };
    // Move clocks based on 0-8 id
    static int move(int b, int id)
    {
        String m = moves[id];
        int ans = b;
        for (int i = 0; i < m.length(); i++) {
            ans = rotate(ans, m.charAt(i) - 'A');
        }
        return ans;
    }

    
    static void solve()
    {
        Queue<Integer> q = new ArrayDeque<>();
        Queue<String> strQ = new ArrayDeque<>();
        int key = encode(board);
        int nax = (int) Math.pow(4, 9);
        
        boolean[] visited = new boolean[nax];
        q.offer(key);
        visited[key] = true;
        strQ.offer("");
        while (!q.isEmpty()) {
            int sz = q.size();
            while (sz-- > 0) {
                int b = q.poll();
                String s = strQ.poll();
                if (b == 0) {
                    if (finalAns.isEmpty() || finalAns.compareTo(s) > 0) {
                        finalAns = s;
                    }
                    return;
                }
                for (int i = 0; i < 9; i++) {
                    int newB = move(b, i);
                    if (visited[newB]) continue;
                    visited[newB] = true;
                    q.offer(newB);
                    strQ.offer(s + (i+1));
                }
            }
            if (!finalAns.isEmpty()) break;
        }
    }

    static void input()
    {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ni();
            }
        }
    }

    static void output()
    {
        for (int i = 0; i < finalAns.length(); i++) {
            out.print(finalAns.charAt(i));
            if (i < finalAns.length() - 1)
                out.print(' ');
        }
        out.println();
    }

	static void myMain()
    {
		input();
        solve();
        output();
    }
	




    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static boolean logTime = !true;
	static String taskName = "clocks";
    
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
        
        myMain();
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
    
    private static void tr(Object... o) { if(logTime)System.out.println(Arrays.deepToString(o)); }
}