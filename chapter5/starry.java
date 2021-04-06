/*
ID: wyli1231
LANG: JAVA
TASK: starry
*/

import java.io.*;
import java.util.*;

public class starry {
    static Map<String, Integer> mp;
    static boolean[][] visited;
    static boolean[][] marked;
    static int[][] grid;
    static int w, h;

    static void dfs(int i, int j, List<int[]> points)
    {
        points.add(new int[]{i, j});
        visited[i][j] = true;
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (di == 0 && dj == 0) continue;
                int neiI = i + di, neiJ = j + dj;
                if (neiI < 0 || neiJ < 0 || neiI >= h || neiJ >= w || visited[neiI][neiJ] || grid[neiI][neiJ] == 0) 
                    continue;
                dfs(neiI, neiJ, points);
            }
        }
    }
    static String getShape(List<int[]> points)
    {
        int left = points.get(0)[1], right = points.get(0)[1];
        int top = points.get(0)[0], down = points.get(0)[0];
        for (int i = 0; i < points.size(); i++) {
            int r = points.get(i)[0], c = points.get(i)[1];
            left = Math.min(left, c);
            right = Math.max(right, c);
            top = Math.min(top, r);
            down = Math.max(down, r);
            marked[r][c] = true;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = top; i <= down; i++) {
            for (int j = left; j <= right; j++) {
                sb.append(marked[i][j] ? grid[i][j] : 0);
            }
            if (i < down) sb.append('\n');
        }
        for (int i = 0; i < points.size(); i++) {
            int r = points.get(i)[0], c = points.get(i)[1];
            marked[r][c] = false;
        }
        return sb.toString();
    }
    // reverse all rows
    static char[][] type1(char[][] arr)
    {
        return reverse(arr);
    }
    // reverse all columns
    static char[][] type2(char[][] arr)
    {
        char[][] ans = new char[arr.length][arr[0].length];
        for (int i = 0; i < ans.length; i++)
            ans[i] = reverse(arr[i]);
        return ans;
    }
    // reverse all rows and columns
    static char[][] type3(char[][] arr)
    {
        return type2(type1(arr));
    }
    static char[][][] types(char[][] arr)
    {
        return new char[][][] {
            arr,
            type1(arr),
            type2(arr),
            type3(arr)
        };
    }
    static char[][] rotate(char[][] arr)
    {
        int n = arr.length, m = arr[0].length;
        char[][] ans = new char[m][n];
        int r = 0, c = ans[0].length - 1;
        for (int i = 0; i < n; i++, r = 0, c--) {
            for (int j = 0; j < m; j++) {
                ans[r++][c] = arr[i][j];
            }
        }
        return ans;
    }

    static char[][] reverse(char[][] arr)
    {
        char[][] cloned = new char[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) 
            cloned[i] = arr[i].clone();
        arr = cloned;
        int l = 0, r = arr.length - 1;
        while (l < r) {
            char[] tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
            l++;
            r--;
        }
        return cloned;
    }
    static char[] reverse(char[] arr)
    {
        arr = arr.clone();
        int l = 0, r = arr.length - 1;
        while (l < r) {
            char tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
            l++;
            r--;
        }
        return arr;
    }
    static String toString(char[][] arr)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(new String(arr[i]));
            if (i < arr.length - 1) sb.append('\n');
        }
        return sb.toString();
    }
    static List<String> getAllShapes(String shape)
    {
        String[] lines = shape.split("\n");
        int n = lines.length, m = lines[0].length();
        char[][] arr = new char[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                arr[i][j] = lines[i].charAt(j);
        List<String> ans = new ArrayList<>();
        for (char[][] t : types(arr))
            ans.add(toString(t));
        for (char[][] t : types(rotate(arr)))
            ans.add(toString(t));
        return ans;
    }
    static void solve()
    {
        w = ni(); h = ni();
        grid = new int[h][w];
        visited = new boolean[h][w];
        marked = new boolean[h][w];
        mp = new HashMap<>();
        for (int i = 0; i < h; i++) 
            for (int j = 0; j < w; j++) 
                grid[i][j] = nc() == '0' ? 0 : 1;
        int current = 0;
        int[][] ans = new int[h][w];
        for (int[] row : ans)
            Arrays.fill(row, -1);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (visited[i][j] || grid[i][j] == 0) continue;
                List<int[]> points = new ArrayList<>();
                dfs(i, j, points);
                int fill;
                String sh = getShape(points);
                if (mp.containsKey(sh)) {
                    fill = mp.get(sh);
                }
                else {
                    fill = current++;
                    List<String> shapes = getAllShapes(sh);
                    for (String sha : shapes) {
                        mp.put(sha, fill);
                    }
                }
                for (int[] point : points) {
                    ans[point[0]][point[1]] = fill;
                }
            }
        }

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (ans[i][j] == -1) 
                    out.print(0);
                else
                    out.print((char)(ans[i][j] + 'a'));
            }
            out.print("\n");
        }


    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
    // static String taskName = "starry";
    
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


