import java.util.*;

public class cryptcow_helper {
	static Map<String, Boolean> memo = new HashMap<>();
	static boolean isGood(String current)
	{
		if (current.isEmpty()) return true;
		if (memo.containsKey(current)) return memo.get(current);
		int k = current.length() / 3;
		int[] cIdxs = new int[k], oIdxs = new int[k], wIdxs = new int[k];
		int cPt = 0, oPt = 0, wPt = 0;
		for (int i = 0; i < current.length(); i++) {
			char ch = current.charAt(i);
			if (ch == 'C')
				cIdxs[cPt++] = i;
			else if (ch == 'O')
				oIdxs[oPt++] = i;
			else if (ch == 'W')
				wIdxs[wPt++] = i;
		}
		oPt = 0;
		wPt = 0;
		for (int cIdx : cIdxs) {
			while (oPt < k && oIdxs[oPt] < cIdx) oPt++;
			if (oPt == k) {
				memo.put(current, false);
				return false;
			}
			while (wPt < k && wIdxs[wPt] < oIdxs[oPt]) wPt++;
			if (wPt == k) {
				memo.put(current, false);
				return false;
			}
			for (int i = oPt; i < k; i++) {
				int oIdx = oIdxs[i];
				for (int j = wPt; j < k; j++) {
					int wIdx = wIdxs[j];
					if (wIdx < oIdx) return false;
					if (isGood(decrypt(current, cIdx, oIdx, wIdx))) {
						memo.put(current, true);
						return true;
					}
				}
			}
		}
		memo.put(current, false);
		return false;
	}
	static String decrypt(String s, int cIdx, int oIdx, int wIdx)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cIdx; i++)
			sb.append(s.charAt(i));
		for (int i = oIdx+1; i < wIdx; i++)
			sb.append(s.charAt(i));
		for (int i = cIdx+1; i < oIdx; i++)
			sb.append(s.charAt(i));
		for (int i = wIdx+1; i < s.length(); i++)
			sb.append(s.charAt(i));
		return sb.toString();
	}
	static int[] randomArr(int n, int lo, int hi)
    {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) ((Math.random() * (hi - lo + 1)) + lo);
        }
        return ans;
    }

	public static void main(String[] args) 
	{

		System.out.println(isGood(""));
		System.out.println(isGood("COW"));
		System.out.println(isGood("WOOCOCOCWWWC"));
		int k = 2;
		List<Character> chars = new ArrayList<>();
		Set<String> seen = new HashSet<>();
		for (int i = 0; i < k; i++) {
			if (i>0) {
				chars.add('C');
				chars.add('W');
			}
			chars.add('O');
		}
		while (true) {
			Collections.shuffle(chars);
			char[] carr = new char[chars.size()+2];
			carr[0] = 'C';
			carr[carr.length-1] = 'W';
			for (int i = 0; i < chars.size(); i++)
				carr[i+1] = chars.get(i);
			String s = new String(carr);
			if (!isGood(s) && !seen.contains(s)) {
				// System.out.println(s + ": " + isGood(s));
				System.out.println(s);
				seen.add(s);
			}
			// System.out.println(seen.size());
		}
	}
}