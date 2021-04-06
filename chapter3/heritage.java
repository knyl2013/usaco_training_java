/*
ID: wyli1231
LANG: JAVA
TASK: heritage
*/


import java.io.*;
import java.util.*;

public class heritage {
    static char[] inOrder, preOrder;
    static class TreeNode {
        char val;
        TreeNode left;
        TreeNode right;
        public TreeNode(char val)
        {
            left = null;
            right = null;
            this.val = val;
        }
    }
    
    static TreeNode buildTree(int inStart, int inEnd, int preStart, int preEnd)
    {
        if (inStart > inEnd || preStart > preEnd) return null;
        if (inStart == inEnd || preStart == preEnd) return new TreeNode(inOrder[inStart]);
        TreeNode root = new TreeNode(preOrder[preStart]);
        int inLeftEnd = -1; // last one before inOrder meets root.val
        int inCur = inStart;
        int leftSize = 0;
        while (inOrder[inCur] != root.val) {
            inLeftEnd = inCur;
            leftSize++;
            inCur++;
        }
        // now we know [inStart to inLeftEnd] belongs to left subtree
        // [inLeftEnd+1 to inEnd] belongs to right subtree then
        int preLeftEnd = preStart + leftSize;
        root.left = buildTree(inStart, inLeftEnd, preStart + 1, preLeftEnd);
        // inLeftEnd + 2 because middle one is also skipped
        root.right = buildTree(inLeftEnd==-1?inStart+1:inLeftEnd + 2, inEnd, preLeftEnd + 1, preEnd);
        return root;
    }
    static void postOrderUtil(TreeNode root, StringBuilder sb)
    {
        if (root == null) return;
        postOrderUtil(root.left, sb);
        postOrderUtil(root.right, sb);
        sb.append(root.val);
    }
    static String postOrder(TreeNode root)
    {
        StringBuilder sb = new StringBuilder();
        postOrderUtil(root, sb);
        return sb.toString();
    }
    static void solve()
    {
        inOrder = ns().toCharArray();
        preOrder = ns().toCharArray();
        TreeNode root = buildTree(0, inOrder.length - 1, 0, preOrder.length - 1);
        out.printf("%s\n", postOrder(root));
    }





    /*I/O Template from uwi*/
    static InputStream is;
    static PrintWriter out;
    static String INPUT = "";
    // static String taskName = null;
    static String taskName = "heritage";
    
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


