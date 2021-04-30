/*
ID: wyli1231
LANG: JAVA
TASK: twofive
*/

import java.io.*;
import java.util.*;

public class twofive {
	static int[] inDeg = new int[25];
	static boolean[][] adj = new boolean[25][25];
	static int[] ans = new int[25];
	
	static void swap(int[] arr, int i, int j)
	{
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	static void reverse(int[] arr, int start, int end)
	{
		while (start < end) {
			swap(arr, start++, end--);
		}
	}
	static int[] targetArr;
	static int targetNum;
	static void permute(int n, char type)
	{
		int[] current = new int[n];
		for (int i = 0; i < n; i++) current[i] = i;
		int order = 0;
		while (true) {
			if (valid(current)) {
				// System.out.println(Arrays.toString(current));
				// System.out.println(targetNum);
				order++;
				if (type == 'W') {
						if (Arrays.toString(current).equals(Arrays.toString(targetArr))) {
						out.printf("%d\n", order);
						return;
					}
				}
				else {
					if (--targetNum == 0) {
						for (int i = 0; i < 25; i++)
							out.print((char) (current[i] + 'A'));
						out.print("\n");
						return;
					}
				}
			}
			
			int greatest = n - 1;
			int idx = n - 1;
			while (idx >= 0 && current[idx] >= current[greatest]) {
				greatest = idx;
				idx--;
			}
			if (idx < 0) break;
			int smallestGreater = greatest;
			for (int i = idx + 1; i < n; i++) {
				if (current[i] > current[idx] && current[i] < current[smallestGreater]) {
					smallestGreater = i;
				}
			}
			swap(current, idx, smallestGreater);
			reverse(current, idx + 1, n - 1);
		}
	}
	static boolean valid(int[] arr)
	{
		for (int i = 0; i < 25; i++) {
			for (int j = i + 1; j % 5 != 0; j++) {
				if (arr[j] < arr[i]) return false;
			}
			for (int j = i + 5; j < 25; j+=5) {
				if (arr[j] < arr[i]) return false;
			}
		}
		return true;
	}
    static void solve()
    {
		char type = nc();
		if (type == 'N') {
			targetNum = ni();
		}
		else {
			targetArr = new int[25];
			for (int i = 0; i < 25; i++) {
				char cur = nc();
				targetArr[i] = cur - 'A';
			}
		}
		permute(25, type);
		/*
		for (int i = 0; i < 25; i++) {
			for (int j = i + 1; j % 5 != 0; j++) {
				adj[i][j] = true;
				inDeg[j]++;
			}
			for (int j = i + 5; j < 25; j+=5) {
				adj[i][j] = true;
				inDeg[j]++;
			}
		}
		// dfs(0, 0);
		// System.out.println(Arrays.toString(inDeg));
		Queue<Integer> q = new PriorityQueue<>();
		q.offer(0);
		int idx = 0;
		// System.out.println(Arrays.toString(inDeg));
		while (!q.isEmpty()) {
			int sz = q.size();
			List<Integer> poss = new ArrayList<>();
			int p = q.poll();
			poss.add(p);
			ans[p] = idx++;
			// System.out.print(p);
			// System.out.print(" ");
			for (int i = 0; i < 25; i++) {
				if (!adj[p][i]) continue;
				if (--inDeg[i] == 0) {
					q.offer(i);
				}
			}
		}
		// while (!q.isEmpty()) {
			// int sz = q.size();
			// List<Integer> poss = new ArrayList<>();
			// while (sz-- > 0) {
				// int p = q.poll();
				// System.out.print((char)(p+'A'));
				// poss.add(p);
			
				// System.out.print(p);
				// System.out.print(" ");
				// for (int i = 0; i < 25; i++) {
					// if (!adj[p][i]) continue;
					// if (--inDeg[i] == 0) {
						// q.offer(i);
					// }
				// }
			// }
			// Collections.sort(poss);
			// for (int pos : poss) {
				// ans[pos] = idx++;
			// }
			// System.out.println();
			
		// }
		System.out.println(Arrays.toString(inDeg));
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(ans[i*5+j] + " ");
			}
			System.out.println();
		}
        // char type = nc();
		// if (type ==
		*/
    }





    /*I/O Template*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    static String taskName = null;
	// static String taskName = "twofive";
    
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

