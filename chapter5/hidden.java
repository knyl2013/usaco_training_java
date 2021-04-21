/*
ID: wyli1231
LANG: JAVA
TASK: hidden
*/

import java.io.*;
import java.util.*;

public class hidden {
    
    // static void solve()
    // {
    //     int n = ni();
    //     char[] carr = new char[n];
    //     for (int i = 0; i < n; i++) {
    //         carr[i] = nc();
    //     }
    //     String s = new String(carr);
    //     s += s.substring(0, s.length() - 1);
    //     int[] suf = SuffixArray.get(s);
    //     int ans = suf[0];
    //     out.printf("%d\n", ans);
    // }

    static int p = 29;
    static int[] arr;
    static void solve()
    {
        int n = ni();
        char[] carr = new char[n];
        for (int i = 0; i < n; i++) {
            carr[i] = nc();
        }
        String s = new String(carr);
        s = s + s.substring(0, s.length() - 1);
        int[] suf = SuffixArray.get(s);
        int start = -1;
        for (int i = 0; i < suf.length; i++) {
            if ((suf[i] + n - 1) < suf.length) {
                start = suf[i];
                break;
            }
        }
        arr = new int[n*2-1];
        for (int i = 0; i < arr.length; i++)
            arr[i] = s.charAt(i) - 'a';
        long want = hash(start, start+n-1);
        long pow = 1;
        for (int l = 1; l < n; l++)
            pow *= p;
        long h = hash(0, n-1);
        boolean found = false;
        int ans = start;
        if (h == want) {
            found = true;
            ans = 0;
        }
        for (int i = 1; i <= arr.length - n && !found; i++) {
            h = nextHash(pow, h, arr[i-1], arr[i+n-1]);
            if (h == want) {
                found = true;
                ans = i;
            }
        }
        out.printf("%d\n", ans);
    }



// static boolean ok(int len)
//     {
//         Map<Long, Integer> mp = new HashMap<>();
//         int n = arr.length;
//         long h=hash(0, len-1);
//         long pow = 1;
//         mp.put(h, 0);
//         for(int l=1;l<len;l++) pow*=p;
//         for(int i=1;i<=n-len;i++){
//             h=nextHash(pow,h,arr[i-1],arr[i+len-1]);
//             if (mp.containsKey(h)) {
//                 int start = mp.get(h);
//                 if (start + len < i) return true;
//             }
//             else {
//                 mp.put(h, i);
//             }
//         }
//         return false;
//     }

static long nextHash(long pow,long hash,int left,int right){
        return (hash - left*pow)*p + right;
    }  

    static long hash(int start, int end) {
        long hash = 0;
        long mul=1;
        // for (int i = 0; i < length; i++) {
        //     hash += arr[start+i]*mul;
        //     mul*=p;
        // }
        for (int i = end; i >= start; i--) {
            hash += arr[i]*mul;
            mul*=p;
        }
        return hash;
    }   



static class SuffixArray
{
    // Class to store information of a suffix
    public static class Suffix implements Comparable<Suffix>
    {
        int index;
        int rank;
        int next;
 
        public Suffix(int ind, int r, int nr)
        {
            index = ind;
            rank = r;
            next = nr;
        }
         
        // A comparison function used by sort()
        // to compare two suffixes.
        // Compares two pairs, returns 1
        // if first pair is smaller
        public int compareTo(Suffix s)
        {
            if (rank != s.rank) return Integer.compare(rank, s.rank);
            return Integer.compare(next, s.next);
        }
    }
     
    // This is the main function that takes a string 'txt'
    // of size n as an argument, builds and return the
    // suffix array for the given string
    public static int[] get(String s)
    {
        int n = s.length();
        Suffix[] su = new Suffix[n];
         
        // Store suffixes and their indexes in
        // an array of classes. The class is needed
        // to sort the suffixes alphabatically and
        // maintain their old indexes while sorting
        for (int i = 0; i < n; i++)
        {
            su[i] = new Suffix(i, s.charAt(i) - '$', 0);
        }
        for (int i = 0; i < n; i++)
            su[i].next = (i + 1 < n ? su[i + 1].rank : -1);
 
        // Sort the suffixes using the comparison function
        // defined above.
        Arrays.sort(su);
 
        // At this point, all suffixes are sorted
        // according to first 2 characters.
        // Let us sort suffixes according to first 4
        // characters, then first 8 and so on
        int[] ind = new int[n];
         
        // This array is needed to get the index in suffixes[]
        // from original index. This mapping is needed to get
        // next suffix.
        for (int length = 4; length < 2 * n; length <<= 1)
        {
             
            // Assigning rank and index values to first suffix
            int rank = 0, prev = su[0].rank;
            su[0].rank = rank;
            ind[su[0].index] = 0;
            for (int i = 1; i < n; i++)
            {
                // If first rank and next ranks are same as
                // that of previous suffix in array,
                // assign the same new rank to this suffix
                if (su[i].rank == prev &&
                    su[i].next == su[i - 1].next)
                {
                    prev = su[i].rank;
                    su[i].rank = rank;
                }
                else
                {
                    // Otherwise increment rank and assign
                    prev = su[i].rank;
                    su[i].rank = ++rank;
                }
                ind[su[i].index] = i;
            }
             
            // Assign next rank to every suffix
            for (int i = 0; i < n; i++)
            {
                int nextP = su[i].index + length / 2;
                su[i].next = nextP < n ?
                   su[ind[nextP]].rank : -1;
            }
             
            // Sort the suffixes according
            // to first k characters
            Arrays.sort(su);
        }
        // System.out.println();
        // for (int i = 0; i < n; i++)
        //     System.out.println(su[i].index + " " + su[i].rank);
        // System.out.println();
        // Store indexes of all sorted
        // suffixes in the suffix array
        int[] suf = new int[n];
        for (int i = 0; i < n; i++)
            suf[i] = su[i].index;
 
        // Return the suffix array
        return suf;
    }   
} // This code is contributed by AmanKumarSingh








    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "hidden";
    
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


