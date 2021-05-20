/*
ID: wyli1231
LANG: JAVA
TASK: cowxor
*/


import java.io.*;
import java.util.*;

public class cowxor_bk {
	static void swap(List<List<Integer>> a, List<List<Integer>> b, int idx)
	{
		List<Integer> tmp = a.get(idx);
		a.set(idx, b.get(idx));
		b.set(idx, tmp);
	}
    static int[] randomArr(int n, int lo, int hi)
    {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) ((Math.random() * (hi - lo + 1)) + lo);
        }
        return ans;
    }

	static int[] answer(int[] a)
	{
		int n = a.length;
		int[] ans = new int[3];
		int maxi = (1 << 21) - 1;
		// int[][] ons = new int[21][n], offs = new int[21][n];
		// for (int i = 0; i < 21; i++) {
			// Arrays.fill(ons[i], -1);
			// Arrays.fill(offs[i], -1);
		// }
		Arrays.fill(ans, -1);
		List<List<Integer>> ons = new ArrayList<>(), offs = new ArrayList<>();
		for (int i = 0; i < 21; i++) {
			ons.add(new ArrayList<>());
			offs.add(new ArrayList<>());
		}
		
		ans[0] = 0;
		for (int i = 0; i < n; i++) {
			int lastOk = -1;
			for (int j = 0; j < 21; j++) {
				int bit = (a[i] >> j) & 1;
				if (bit == 0) {
					offs.get(j).add(i);
				}
				else {
					swap(ons, offs, j);
					ons.get(j).add(i);
				}
				if (!ons.get(j).isEmpty())
					lastOk = j;
			}
			if (lastOk == -1) continue;
			int cur = (1 << lastOk);
			TreeSet<Integer> st = new TreeSet<>(ons.get(lastOk));
			for (int j = lastOk-1; j >= 0 && st.size() >= 1; j--) {
                TreeSet<Integer> nxt = new TreeSet<>();
                for (int o : ons.get(j)) {
                    if (!st.contains(o)) continue;
                    nxt.add(o);
                }
                if (nxt.size() > 0) {
                    cur = cur | (1 << j);
                    st = nxt;
                }
			}
            if (ans[0] <= cur) {
                ans[0] = cur;
                ans[1] = st.last() + 1;
                ans[2] = i + 1;
            }
			// System.out.println("i: " + i);
			// System.out.println("lastOk: " + lastOk);
   //          System.out.println("cur: " + cur);
			// System.out.println(st);
			// System.out.println("ons: " + ons);
   //          System.out.println("start: " + st.last());
			// System.out.println("offs: " + offs);
			// System.out.println("\n");
            
			// ans[0] = Math.max(ans[0], cur);
			// int left = 0, right = i;
			// int val = 0;
			// for (int j = 20; j >= 0; j--) {
				// int bit = (a[i] >> j) & 1;
				// int want = bit ^ 1;
				// List<List<Integer>> target = want == 1 ? ons : offs;
				// List<Integer> range = target.get(j);
				// boolean setLeft = false, setRight = false;
				// for (int r : range) {
					// if (r < left || r > right) continue;
					// val = val | (1 << j);
					// if (!setLeft) {
						// left = r;
						// setLeft = true;
					// }
					// right = Math.min(right, r);
				// }
				// System.out.println(left + " " + right);
				// System.out.println(range);
			// }
			// System.out.println("i: " + i + ", val: " + val);
			// System.out.println("\n");
			// int want = maxi ^ a[i];
			// System.out.println(Integer.toBinaryString(want));
			// for (int j = 0; j < 21; j++) {
				// int bit = (a[i] >> j) & 1;
				// int[][] target = want == 1 ? ons : offs;
			// }
			// for (int j = 0; j < 21; j++) {
				// int bit = (a[i] >> j) & 1;
				// if (bit == 1) {
					// ons.get(j).add(i);
				// }
				// else {
					// offs.get(j).add(i);
				// }
			// }
		}
		
		// System.out.println(ons);
		// System.out.println(offs);
		
		return ans;
	}
    static int[] brute(int[] a)
    {
        int n = a.length;
        int[] ans = new int[3];
        Arrays.fill(ans, -1);
        for (int i = 0; i < n; i++) {
            int xor = 0;
            for (int j = i; j < n; j++) {
                xor ^= a[j];
                boolean better = xor > ans[0] || 
                                (xor == ans[0] && j > ans[1]) ||
                                (xor == ans[0] && j == ans[1] && (j-i)>(ans[2]-ans[1]));
                if (better) {
                    ans[0] = xor;
                    ans[1] = i;
                    ans[2] = j;
                }
            }
        }
        ans[1]++;
        ans[2]++;
        return ans;
    }
    static void solve()
    {
        int[] a = na(ni());
        int[] ans = brute(a);
        out.printf("%d %d %d\n", ans[0], ans[1], ans[2]);

        // while (true) {
        //     int[] a = randomArr(100000, 1, 10000);
        //     int[] ans = answer(a);
        //     System.out.printf("%d %d %d\n", ans[0], ans[1], ans[2]);
        // }
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


