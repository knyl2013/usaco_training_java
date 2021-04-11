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
    static int[][][] fonts = new int[27][20][20];
    static int[][] images;
    static char[] chars = new char[] {' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '?'};
    static int[] chosenChars;
    static boolean[] dups;
    static int imgEnd;
    static final int INF = (int)1e6;
    static final int QUESTION_MARK = 27;
    // ans[0] := corruptions, ans[1] := character index
    static int[] match(int imgStart)
    {
        return match(imgStart, -1, -1);
    }
    static int[] match(int imgStart, int missIdx, int dupIdx)
    {
        int[] ans = new int[]{INF, QUESTION_MARK};
        for (int i = 0; i < 27; i++) {
            int[][] font = fonts[i];
            int corrupt = 0;
            int imgIdx = imgStart;
            for (int j = 0; j < 20; j++) {
                if (j == missIdx) continue;
                if (imgIdx == dupIdx) imgIdx++;
                int[] img = images[imgIdx++];
                for (int k = 0; k < 20; k++) {
                    if (font[j][k] != img[k])
                        corrupt++;
                }
            }
            if (ans[0] == -1 || ans[0] > corrupt) {
                ans[0] = corrupt;
                if (corrupt <= 120)
                    ans[1] = i;
            }
        }
        return ans;
    }
    static int[] miss(int imgStart)
    {
        int[] ans = new int[]{INF, -1};
        for (int i = 0; i < 20; i++) {
            int[] cur = match(imgStart, i, -1);
            if (ans[0] > cur[0])
                ans = cur;
        }
        return ans;
    }
    static int[] dup(int imgStart)
    {
        int[] ans = new int[]{INF, -1};
        for (int i = imgStart + 1; i <= imgStart + 20; i++) {
            if (!dups[i]) continue;
            int[] cur = match(imgStart, -1, i);
            if (ans[0] < cur[0]) {
                ans = cur;
            }
        }
        return ans;
    }
    static int dfs(int imgStart)
    {
        if (imgStart == images.length)
            return 0;
        int imgEnd = images.length - 1;
        int remain = imgEnd - imgStart + 1;
        if (remain < 19)
            return INF;
        if (remain == 19) {
            int[] ans = miss(imgStart);
            chosenChars[imgStart] = ans[1];
            return ans[0];
        }
        int[] ifMatch = match(imgStart);
        int ifMatchAfter = ifMatch[0] + dfs(imgStart + 20);
        int ans = ifMatchAfter;
        int chosenChar = ifMatch[1];
        if (remain >= 21) {
            int[] ifDup = dup(imgStart);
            int ifDupAfter = ifDup[0] + dfs(imgStart + 21);
            if (ifDupAfter < ans) {
                ans = ifDupAfter;
                chosenChar = ifDup[1];
            }
        }
        int[] ifMiss = miss(imgStart);
        int ifMissAfter = ifMiss[0] + dfs(imgStart + 19);
        if (ifMissAfter < ans) {
            ans = ifMissAfter;
            chosenChar = ifMiss[1];
        }
        chosenChars[imgStart] = chosenChar;
        return ans;
    }
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
        dups = new boolean[n];
        chosenChars = new int[n];
        Arrays.fill(chosenChars, -1);
        imgEnd = images.length - 1;
        String prev = "";
        for (int i = 0; i < n; i++) {
            String s = ns();
            if (s.equals(prev)) dups[i] = true;
            prev = s;
            for (int j = 0; j < 20; j++) {
                images[i][j] = s.charAt(j) == '0' ? 0 : 1;
            }
        }
        

        StringBuilder sb = new StringBuilder();
        int corruptions = dfs(0);
        System.out.println(corruptions);
        System.out.println(chars[chosenChars[0]]);
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


