/*
ID: wyli1231
LANG: JAVA
TASK: pprime
*/
import java.io.*;
import java.util.*;

public class pprime {
    static int MAX_LEN = 7;
    static int mini, maxi;
    static List<Integer> ans;
    static Stack<Integer> stack;
    static boolean isPrime(int x)
    {
        int sq = (int) Math.sqrt(x);
        for (int i = 2; i <= sq; i++) {
            if (x % i == 0) return false;
        }
        return true;
    }
    // convert stack into string
    static void convert(boolean lastAppearOnce)
    {
        StringBuilder sb = new StringBuilder();
        for (int x : stack) {
            sb.append(x);
        }
        // append string in reverse to make palindrome
        int begin = lastAppearOnce ? sb.length() - 2 : sb.length() - 1;
        for (int i = begin; i >= 0; i--) {
            sb.append(sb.charAt(i));
        }
        int val = Integer.parseInt(sb.toString());
        if (isPrime(val) && val >= mini && val <= maxi) {
            ans.add(val);
        }
    }
    // backtrack to fill stack as palindrome
    static void backtrack(int len)
    {
        if (len <= 0) {
            // if len is -1, it means the original len is odd, so middle only appears once
            convert(len < 0);
            return;
        }
        for (int dig = 0; dig <= 9; dig++) {
            stack.push(dig);
            backtrack(len - 2);
            stack.pop();
        }
    }
    static void solve()
    {
        ans = new ArrayList<>();
        stack = new Stack<>(); // stack to store digits
        mini = ni(); maxi = ni();
        int[] starts = new int[] {1, 3, 7, 9}; // possible first&last digits of answer
        Collections.sort(ans);
        if (mini <= 5 && maxi >= 5) ans.add(5);
        if (mini <= 7 && maxi >= 7) ans.add(7);
        // Brute-force the desired length of the answer
        for (int len = 2; len <= MAX_LEN; len++) {
            for (int start : starts) {
                stack.push(start);
                backtrack(len - 2);
                stack.pop();
            }
        }
        Collections.sort(ans);
        for (int x : ans) {
            out.printf("%d\n", x);
        }
    }



    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "pprime";
    
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


