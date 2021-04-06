/*
ID: wyli1231
LANG: JAVA
TASK: shopping
*/

import java.io.*;
import java.util.*;

public class shopping {
    static final int INF = (int) 1e9;
    static int[] codes;
    static int[][][][][] dp;
    static boolean[][][][][] seen;
    static int getDp(int[] state)
    {
        return dp[state[0]][state[1]][state[2]][state[3]][state[4]];
    }
    static void setDp(int[] state, int val)
    {
        dp[state[0]][state[1]][state[2]][state[3]][state[4]] = val;
    }
    static boolean isSeen(int[] state)
    {
        return seen[state[0]][state[1]][state[2]][state[3]][state[4]];
    }
    static void markSeen(int[] state)
    {
        seen[state[0]][state[1]][state[2]][state[3]][state[4]] = true;
    }
    static int getRank(int code)
    {
        // codes.length <= 5
        for (int i = 0; i < codes.length; i++) {
            if (code == codes[i]) return i;
        }
        return -1;
    }
    // return empty array if offer will lead to too many products
    static int[] buy(int[] p, int[][] offer)
    {
        int[] newP = new int[p.length];
        for (int i = 0; i < p.length; i++) newP[i] = p[i];
        for (int[] item : offer) {
            int rank = item[0], quantity = item[1];
            newP[rank] -= quantity;
            if (newP[rank] < 0) return new int[]{}; // invalid move
        }
        return newP;
    }
    static void solve()
    {
        int s = ni();
        // offers[i][x][0] := product code, offers[i][x][1] := quantity
        int[][][] offers = new int[s][][];
        // prices[i] := cost for buying offers[i]
        int[] prices = new int[s];
        
        for (int i = 0; i < s; i++) {
            int n = ni();
            int[][] sp = new int[n][2];
            for (int x = 0; x < n; x++) {
                sp[x][0] = ni();
                sp[x][1] = ni();
            }
            offers[i] = sp;
            prices[i] = ni();
        }

        int b = ni();
        codes = new int[b];
        int[][] wants = new int[b][];
        int[][] oriPrices = new int[b][];
        for (int i = 0; i < b; i++) {
            int code = ni(), quantity = ni(), price = ni();
            codes[i] = code;
            wants[i] = new int[]{code, quantity};
            oriPrices[i] = new int[]{code, price};
        }
        Arrays.sort(codes);

        // wants with reference to rank of the code instead of code itself
        int[] convertedWants = new int[5];
        for (int[] w : wants) {
            int code = w[0], quantity = w[1];
            convertedWants[getRank(code)] = quantity;
        }
        // oriPrices with reference to rank of the code instead of code itself
        int[] convertedPrices = new int[b];
        for (int[] p : oriPrices) {
            int code = p[0], price = p[1];
            convertedPrices[getRank(code)] = price;
        }
        // offers with reference to rank of the code instead of code itself
        for (int[][] offer : offers) {
            for (int i = 0; i < offer.length; i++) {
                offer[i][0] = getRank(offer[i][0]);
            }
        }

        dp = new int[6][6][6][6][6];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                for (int k = 0; k < 6; k++)
                    for (int m = 0; m < 6; m++)
                        Arrays.fill(dp[i][j][k][m], INF);
        seen = new boolean[6][6][6][6][6];

        PriorityQueue<int[]> pq = new PriorityQueue<>((x, y) -> getDp(x) - getDp(y));
        pq.offer(convertedWants);
        setDp(convertedWants, 0);
        while (!pq.isEmpty()) {
            int[] p = pq.poll();
            if (isSeen(p)) continue;
            markSeen(p);
            int curPrice = getDp(p);
            // Buy from special offers
            for (int i = 0; i < offers.length; i++) {
                int[] newP = buy(p, offers[i]);
                if (newP.length == 0 || isSeen(newP) || getDp(newP) <= curPrice + prices[i]) continue;
                setDp(newP, curPrice + prices[i]);
                pq.offer(newP);
            }
            // Buy from default prices
            for (int i = 0; i < convertedPrices.length; i++) {
                if (p[i] == 0) continue;
                int[] newP = new int[p.length];
                for (int j = 0; j < p.length; j++) newP[j] = p[j];
                newP[i]--;
                if (isSeen(newP) || getDp(newP) <= curPrice + convertedPrices[i]) continue;
                setDp(newP, curPrice + convertedPrices[i]);
                pq.offer(newP);
            }
        }
        out.printf("%d\n", dp[0][0][0][0][0]);
    }




    
    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "shopping";
    
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


