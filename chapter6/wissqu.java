/*
ID: wyli1231
LANG: JAVA
TASK: wissqu
*/

import java.io.*;
import java.util.*;

public class wissqu {
    static class Task {
        char type;
        int row, col;
        public Task(char type, int row, int col)
        {
            this.type = type;
            this.row = row;
            this.col = col;
        }
    }
    static Stack<Task> stk = new Stack<>();
    static Stack<Task> buffer = new Stack<>();
    static int cnt = 0;
    static int level = 0;
    static char[][] board = new char[4][4];
    static boolean[][] marked = new boolean[4][4];
    static int[] freq = new int[] {3, 3, 3, 4, 3};
    static boolean isGood(char type, int i, int j)
    {
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                int neiI = i+di, neiJ = j+dj;
                if (neiI < 0 || neiJ < 0 || neiI >= 4 || neiJ >= 4) continue;
                if (board[neiI][neiJ] == type) return false;
            }
        }
        return true;
    }
    static void dfs()
    {
        if (level == 16) {
            cnt++;
            if (stk.isEmpty()) return;
            while (!stk.isEmpty()) {
                buffer.push(stk.pop());
            }
            while (!buffer.isEmpty()) {
                Task t = buffer.pop();
                out.printf("%c %d %d\n", t.type, t.row, t.col);
            }
            return;
        }
        for (char type = 'A'; type <= 'E'; type++) {
            if (freq[type-'A']==0) continue;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (marked[i][j]) continue;
                    if (!isGood(type, i, j)) continue;
                    char oldHerd = board[i][j];
                    board[i][j] = type;
                    level++;
                    freq[type-'A']--;
                    marked[i][j] = true;
                    if (cnt == 0) stk.push(new Task(type, i+1, j+1));
                    dfs();
                    board[i][j] = oldHerd;
                    level--;
                    freq[type-'A']++;
                    marked[i][j] = false;
                    if (cnt == 0) stk.pop();
                }
            }
        }
    }
    static void solve()
    {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                board[i][j] = nc();
        // for (char[] row : board)
        //     System.out.println(Arrays.toString(row));
        // System.out.println(isGood('D', 2, 3));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!isGood('D', i, j)) continue;
                char oldHerd = board[i][j];
                board[i][j] = 'D';
                marked[i][j] = true;
                if (cnt == 0) stk.push(new Task('D', i+1, j+1));
                level++;
                dfs();
                level--;
                if (cnt == 0) stk.pop();
                marked[i][j] = false;
                board[i][j] = oldHerd;
            }
        }
        out.printf("%d\n", cnt);
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "wissqu";
    static boolean logTime = !true;
    
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
    
    private static void tr(Object... o) { if(logTime)System.out.println(Arrays.deepToString(o)); }
}


