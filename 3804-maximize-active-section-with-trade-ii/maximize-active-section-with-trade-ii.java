class Solution {
    int[] lg;
    class Run {
        int l;
        int r;
        int type;
        int length;
        Run(int l, int r, int type) {
            this.l = l;
            this.r = r;
            this.type = type;
            this.length = r - l + 1;
        }
    }
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        ArrayList<Run> segments = new ArrayList<>(); // saare segments store krega
        ArrayList<Run> oneSegs = new ArrayList<>();  // 1s segments store krega
        int[] gainToOrigIdx = new int[n + 1]; // ye segment ke starting idx pr store krgea gain ka idx
        int k = 0;
        int idx = 0;
        while (k < n) {
            int j = k;
            gainToOrigIdx[k] = idx;
            while (j + 1 < n && s.charAt(j + 1) == s.charAt(k)) j++;
            segments.add(new Run(k, j, s.charAt(k) - '0'));
            if (s.charAt(k) - '0' == 1) oneSegs.add(new Run(k, j, s.charAt(k) - '0'));
            k = j + 1;
            idx++;
        }
        lg = new int[segments.size() + 1];
        int[] gain = new int[segments.size()]; // each segment kitna gain dega trade krne pr
        for (int j = 0; j < segments.size(); j++) {
            if (segments.get(j).type == 1) {
                if (j - 1 >= 0 && j + 1 < segments.size()) {
                    gain[j] = segments.get(j - 1).length + segments.get(j + 1).length;
                }
            }
        }
        int[][] st = buildSparseTable(segments.size(), gain);
        int activeSession = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '1') activeSession++;
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for (int[] query : queries) {
            int l = query[0], r = query[1];
            // First aur Last valid 1-block dhundo jo strictly inside ho
            int left = findFirstIdx(oneSegs, l, r);
            int right = findLastIdx(oneSegs, l, r);
            if (left == -1) {
                ans.add(activeSession); // ek bhi valid 1s block nhi hai to trade possible nhi
                continue;
            }
            int firstSegIdx = gainToOrigIdx[left];
            int lastSegIdx = gainToOrigIdx[right];

            // First Block Zeros Intersection 
            Run leftZeroFirst = segments.get(firstSegIdx - 1);
            Run rightZeroFirst = segments.get(firstSegIdx + 1);
            int lzFirst = leftZeroFirst.r - Math.max(l, leftZeroFirst.l) + 1;
            int rzFirst = Math.min(r, rightZeroFirst.r) - rightZeroFirst.l + 1;
            int gainFirst = lzFirst + rzFirst;

            if (left == right) { // ek hi valid block hai query me
                ans.add(activeSession + gainFirst);
                continue;
            }
            // Last Block Zeros Intersection 
            Run leftZeroLast = segments.get(lastSegIdx - 1);
            Run rightZeroLast = segments.get(lastSegIdx + 1);
            int lzLast = leftZeroLast.r - Math.max(l, leftZeroLast.l) + 1;
            int rzLast = Math.min(r, rightZeroLast.r) - rightZeroLast.l + 1;
            int gainLast = lzLast + rzLast;

            // Maximum gain dono boundary blocks mese lelo
            int bestGain = Math.max(gainFirst, gainLast);
            // Beech wale blocks ka max Sparse table se O(1) me lelo
            if (firstSegIdx + 2 <= lastSegIdx - 2) {
                bestGain = Math.max(bestGain, query(firstSegIdx + 2, lastSegIdx - 2, st));
            }
            ans.add(activeSession + bestGain);
        }
        return ans;
    }
    private int findFirstIdx(ArrayList<Run> oneSegs, int left, int right) {
        int low = 0, high = oneSegs.size() - 1;
        int idx = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int start = oneSegs.get(mid).l;
            int end = oneSegs.get(mid).r;
            // Block strictly inside hona chahiye
            if (start > left && end < right) {
                idx = start;
                high = mid - 1; // Aur pehle (left) dekhna hai
            } else if (start <= left) {
                low = mid + 1; // Ye bounds cross kr rha hai, right jao
            } else {
                high = mid - 1; // Ye bounds cross kr rha hai, left jao
            }
        }
        return idx;
    }
    private int findLastIdx(ArrayList<Run> oneSegs, int left, int right) {
        int low = 0, high = oneSegs.size() - 1;
        int idx = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int start = oneSegs.get(mid).l;
            int end = oneSegs.get(mid).r;
            // Block strictly inside hona chahiye
            if (start > left && end < right) {
                idx = start;
                low = mid + 1; // Aur aage (right) dekhna hai
            } else if (end >= right) {
                high = mid - 1; // Bounds cross kr rha hai, left jao
            } else {
                low = mid + 1; // Bounds cross kr rha hai, right jao
            }
        }
        return idx;
    }
    private int[][] buildSparseTable(int m, int[] gain) {
        int LOG = 1;
        while ((1 << LOG) <= m) LOG++;

        int[][] st = new int[LOG][m];

        for (int i = 0; i < m; i++)
            st[0][i] = gain[i];

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i + (1 << k) <= m; i++) {
                st[k][i] = Math.max(
                    st[k - 1][i],
                    st[k - 1][i + (1 << (k - 1))]
                );
            }
        }
        for (int i = 2; i <= m; i++) {
            lg[i] = lg[i / 2] + 1;
        }
        return st;
    }
    private int query(int l, int r, int[][] st) {
        if (l > r) return 0;
        int k = lg[r - l + 1];
        return Math.max(
            st[k][l],
            st[k][r - (1 << k) + 1]
        );
    }
    }
