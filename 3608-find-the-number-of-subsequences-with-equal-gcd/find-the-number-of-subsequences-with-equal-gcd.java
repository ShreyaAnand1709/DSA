class Solution {
    private static final int MAX = 200;
    private static final int MOD = 1_000_000_007;
    public int subsequencePairCount(int[] nums) {
        int[][] gcd = new int[MAX + 1][MAX + 1];
        for (int i = 0; i <= MAX; i++) {
            for (int j = 0; j <= MAX; j++) {
                if (i == 0) gcd[i][j] = j;
                else if (j == 0) gcd[i][j] = i;
                else gcd[i][j] = gcd(i, j);
            }
        }

        long[][] dp = new long[MAX + 1][MAX + 1];
        dp[0][0] = 1;

        for (int x : nums) {
            long[][] next = new long[MAX + 1][MAX + 1];

            for (int g1 = 0; g1 <= MAX; g1++) {
                for (int g2 = 0; g2 <= MAX; g2++) {

                    long ways = dp[g1][g2];
                    if (ways == 0) continue;

                    // Skip
                    next[g1][g2] = (next[g1][g2] + ways) % MOD;

                    // Put in seq1
                    int ng1 = gcd[g1][x];
                    next[ng1][g2] = (next[ng1][g2] + ways) % MOD;

                    // Put in seq2
                    int ng2 = gcd[g2][x];
                    next[g1][ng2] = (next[g1][ng2] + ways) % MOD;
                }
            }

            dp = next;
        }

        long ans = 0;
        for (int g = 1; g <= MAX; g++) {
            ans = (ans + dp[g][g]) % MOD;
        }

        return (int) ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}