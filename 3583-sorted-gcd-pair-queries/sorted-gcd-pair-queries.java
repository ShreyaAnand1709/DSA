class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int max = 0;
        for (int x : nums) max = Math.max(max, x);

        int[] freq = new int[max + 1];
        for (int x : nums) freq[x]++;

        long[] countDiv = new long[max + 1];

        // Count numbers divisible by i
        for (int i = 1; i <= max; i++) {
            long cnt = 0;
            for (int j = i; j <= max; j += i) {
                cnt += freq[j];
            }
            countDiv[i] = cnt;
        }

        // gcdCount[i] = number of pairs having gcd exactly i
        long[] gcdCount = new long[max + 1];

        for (int i = max; i >= 1; i--) {
            long pairs = countDiv[i] * (countDiv[i] - 1) / 2;

            for (int j = i + i; j <= max; j += i) {
                pairs -= gcdCount[j];
            }

            gcdCount[i] = pairs;
        }

        // Prefix counts
        long[] prefix = new long[max + 1];
        for (int i = 1; i <= max; i++) {
            prefix[i] = prefix[i - 1] + gcdCount[i];
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            long target = queries[i] + 1; // 1-based

            int l = 1, r = max;
            while (l < r) {
                int mid = (l + r) / 2;
                if (prefix[mid] >= target)
                    r = mid;
                else
                    l = mid + 1;
            }
            ans[i] = l;
        }

        return ans;
    }
}