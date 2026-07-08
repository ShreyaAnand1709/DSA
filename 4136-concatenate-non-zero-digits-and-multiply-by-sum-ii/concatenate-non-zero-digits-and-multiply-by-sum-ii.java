class Solution {
    static final int MOD = 1_000_000_007;
    public int[] sumAndMultiply(String s, int[][] queries) {
        int n = s.length();

        // Prefix count of non-zero digits
        int[] prefNZ = new int[n + 1];

        int totalNZ = 0;
        for (int i = 0; i < n; i++) {
            prefNZ[i + 1] = prefNZ[i];
            if (s.charAt(i) != '0') {
                prefNZ[i + 1]++;
                totalNZ++;
            }
        }

        // Arrays for non-zero digits
        long[] prefValue = new long[totalNZ + 1];
        long[] prefSum = new long[totalNZ + 1];
        long[] pow10 = new long[totalNZ + 1];

        pow10[0] = 1;
        for (int i = 1; i <= totalNZ; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        int idx = 0;
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);

            if (ch != '0') {
                int digit = ch - '0';

                prefSum[idx + 1] = prefSum[idx] + digit;
                prefValue[idx + 1] = (prefValue[idx] * 10 + digit) % MOD;

                idx++;
            }
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int l = queries[i][0];
            int r = queries[i][1];

            int left = prefNZ[l];
            int right = prefNZ[r + 1];

            int len = right - left;

            if (len == 0) {
                ans[i] = 0;
                continue;
            }

            long x = (prefValue[right]
                    - (prefValue[left] * pow10[len]) % MOD
                    + MOD) % MOD;

            long sum = prefSum[right] - prefSum[left];

            ans[i] = (int) ((x * sum) % MOD);
        }

        return ans;
    }
}