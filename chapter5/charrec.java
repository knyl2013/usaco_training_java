/*
ID: wyli1231
LANG: JAVA
TASK: charrec
*/

import java.io.*;
import java.util.*;

public class charrec {
    static String[] readFile(String fileName) throws Exception
    {
        List<String> tmp = new ArrayList<>();
        BufferedReader myReader = new BufferedReader(new FileReader(fileName));
        String data;
        while ((data = myReader.readLine()) != null) {    
            if (data.equals("540")) continue;
            tmp.add(data);
        }
        myReader.close();
        String[] ans = new String[tmp.size()];
        for (int i = 0; i < ans.length; i++) ans[i] = tmp.get(i);
        return ans;
    }
    static enum Type {
        Duplicate,
        Miss,
        None
    }
    static class Result {
        Type type;
        int match;
        char ch;
        public Result(int m, char c) {match = m; ch = c;}
    }
    static Result max(Result a, Result b)
    {
        return a.match > b.match ? a : b;
    }
    static Result match(int chIdx, Type type, int fontStart, int fontEnd, int imgStart, int imgEnd, int skipFont, int skipImg)
    {
        int fontIdx = fontStart, imgIdx = imgStart;
        Result ans = new Result(279, '?'); // only >= 280 will be counted as match
        int cnt = 0;
        while (fontIdx <= fontEnd && imgIdx <= imgEnd) {
            int[] font = fonts[chIdx][fontIdx++], img = images[imgIdx++];
            for (int i = 0; i < 20; i++)
                if (font[i] == img[i])
                    cnt++;
            if (fontIdx == skipFont) {
                fontIdx++;
                cnt+=20;
            }
            if (imgIdx == skipImg) imgIdx++;
        }
        if (cnt > ans.match) {
            ans = new Result(cnt, chars[chIdx]);
            ans.type = type;
        }
        return ans;
    }
    static Result skip(int[][] toSkip, int[][] other, int skipIdx)
    {
        int idx = 0;
        int cnt = 0;
        Result ans = new Result(279, '?'); // only >= 280 will be counted as match
        for (int i = 0; i < other.length; i++) {
            if (idx == skipIdx) idx++;
            int[] line1 = toSkip[idx++], line2 = other[i];
            for (int j = 0; j < 20; j++) {
                if (line1[j] == line2[j])
                    cnt++;
            }
            if (cnt > ans.match) {
                ans = new Result(cnt, (char)(i+'a'));
            }
        }
        return ans;
    }
    static Result miss(int[][] font, int[][] img)
    {
        Result best = skip(font, img, 0);
        for (int i = 1; i < 20; i++) {
            best = max(best, skip(font, img, i));
        }
        best.type = Type.Miss;
        return best;
    }
    static Result duplicate(int[][] font, int[][] img)
    {
        Result best = skip(img, font, 1);
        for (int i = 2; i < 20; i++) {
            best = max(best, skip(img, font, i));
        }
        best.type = Type.Duplicate;
        return best;
    }
    static int[][][] fonts = new int[27][20][20];
    static int[][] images;
    static char[] chars = new char[] {' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    static void solve() throws Exception
    {
        String[] fontIn = readFile("font.in");
        int idx = 0;
        for (int i = 0; i < 27; i++) {
            for (int j = 0; j < 20; j++) {
                String font = fontIn[idx++];
                for (int k = 0; k < 20; k++) {
                    fonts[i][j][k] = font.charAt(k) == '0' ? 0 : 1;
                }
            }
        }
        int n = ni();
        images = new int[n][20];
        for (int i = 0; i < n; i++) {
            String s = ns();
            for (int j = 0; j < 20; j++) {
                images[i][j] = s.charAt(j) == '0' ? 0 : 1;
            }
        }
        StringBuilder sb = new StringBuilder();
        idx = 0;
        while (idx < n) {
            Result cur = new Result(279, '?');
            for (int i = 0; i < 27; i++) {
                Result temp = match(i, Type.None, 0, 19, idx, Math.min(n-1,idx+19), -1, -1);
                cur = max(cur, temp);
            }
            for (int i = 0; i < 27; i++) {
                Result temp = match(i, Type.Miss, 0, 19, idx, Math.min(n-1,idx+19), 0, -1);
                for (int missIdx = 0; missIdx < 20; missIdx++) {
                    temp = max(temp, match(i, Type.Miss, 0, 19, idx, Math.min(n-1,idx+19), missIdx, -1));
                }
                System.out.println(temp.match);
                cur = max(cur, temp);
            }
            System.out.println(cur.match);
            sb.append(cur.ch);
            idx += 20;
            if (cur.type == Type.Duplicate)
                idx++;
            if (cur.type == Type.Miss)
                idx--;
        }


        out.printf("%s\n", sb.toString());
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


