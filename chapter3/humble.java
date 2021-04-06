/*
ID: wyli1231
LANG: JAVA
TASK: humble
*/


import java.io.*;
import java.util.*;

public class humble {
    static void solve()
    {
        int k = ni(), n = ni();
        long[] primes = new long[k];
        for (int i = 0; i < k; i++) primes[i] = nl();
        int[] pointers = new int[k];
        long[] dp = new long[n + 1];
        dp[0] = 1; // first pseudo-humble no. is 1
        // Generate current minimum humble no. using previous humble no.
        for (int i = 1; i <= n; i++) {
            // current minimum = MIN(dp[pointers[j]] * primes[j])
            // then advance the minimum pointer by one 
            // because anything * primes[minIdx] at the end will be greater than this current minimum
            int minIdx = 0;
            for (int j = 1; j < k; j++) {
                if (primes[j] * dp[pointers[j]] < primes[minIdx] * dp[pointers[minIdx]]) {
                    minIdx = j;
                }
            }
            dp[i] = primes[minIdx] * dp[pointers[minIdx]];
            // again this primes[minIdx] has already multiplied its current pointer value
            // so we need to move the pointer by one to prevent repetition
            for (int j = 0; j < k; j++) {
                if (primes[j] * dp[pointers[j]] == dp[i]) {
                    pointers[j]++;
                }
            }
        } 
        // System.out.println(Arrays.toString(pointers));
        // System.out.println(Arrays.toString(dp));
        out.printf("%d\n", dp[n]);       
    }
    // static void solve()
    // {
    //     int k = ni(), n = ni();
    //     long[] primes = new long[k];
    //     for (int i = 0; i < k; i++) primes[i] = nl();
    //     PriorityQueue<Long> pq = new PriorityQueue<>();
    //     Set<Long> seen = new HashSet<>();
    //     for (long p : primes) {
    //         pq.add(p);
    //     }
    //     int current = 1;
    //     while (!pq.isEmpty()) {
    //         long p = pq.poll();
    //         System.out.println(p);
    //         if (current == n) {
    //             out.printf("%d\n", p);
    //             break;
    //         }
    //         current++;
    //         for (long pr : primes) {
    //             long val = pr * p;
    //             pq.add(val);
    //             // if (!seen.contains(val)) {
    //             //     pq.add(val);
    //             //     seen.add(val);
    //             // }
    //         }
    //         // seen.remove(p);
    //     }
    //     // System.out.println(seen.size());
    // }
    // static Set<Long> primes;
    // static Map<Long, Boolean> isPrimes = new TreeMap<>();
    // static Map<Long, Boolean> isHumbles = new TreeMap<>();
    
    // static boolean isPrime(long x)
    // {
    //     if (x == 2)  return true;
    //     if (x % 2 == 0) return false;
    //     if (isPrimes.containsKey(x)) return isPrimes.get(x);
    //     for (long i = 3; i*i <= x; i+=2) {
    //         if (x % i == 0) {
    //             isPrimes.put(x, false);
    //             return false;
    //         }
    //     }
    //     isPrimes.put(x, true);
    //     return true;
    // }
    // // return true if all prime factors of x are in primes
    // static boolean isHumble(long x)
    // {
    //     if (x % 2 == 0 && !primes.contains(2L)) return false;
    //     if (isHumbles.containsKey(x)) return isHumbles.get(x);
    //     for (long i = 3; i*i <= x; i+=2) {
    //         if (x % i != 0) continue;
    //         long other = x / i;
    //         boolean iGood = !isPrime(i) || primes.contains(i);
    //         boolean otherGood = !isPrime(other) || primes.contains(other);
    //         if (!iGood || !otherGood) {
    //             isHumbles.put(x, false);
    //             return false;
    //         }
    //     }
    //     isHumbles.put(x, true);
    //     return true;
    // }
    // static void solve()
    // {
    //     int k = ni(), n = ni();
    //     long[] ps = new long[k];
    //     primes = new TreeSet<>();
    //     for (int i = 0; i < k; i++) {
    //         ps[i] = nl();
    //         primes.add(ps[i]);
    //     }
    //     PriorityQueue<Long> pq = new PriorityQueue<>();
    //     Set<Long> seen = new TreeSet<>();
    //     for (long val : primes) {
    //         pq.add(val);
    //     }
    //     int current = 1;
    //     while (!pq.isEmpty()) {
    //         long p = pq.poll();
    //         if (current == n) {
    //             out.printf("%d\n", p);
    //             break;
    //         }
    //         current++;
    //         for (long a : primes) {
    //             long val = a * p;
    //             if (isHumble(val) && !seen.contains(val)) {
    //                 seen.add(val);
    //                 pq.add(val);
    //             }
    //         }
    //     }
    // }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "humble";
    
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


