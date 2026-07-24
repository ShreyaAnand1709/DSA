class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int v : nums) {
            boolean[][] next = new boolean[4][MAX];

            for (int cnt = 0; cnt <= 3; cnt++) {
                for (int x = 0; x < MAX; x++) {
                    if (!dp[cnt][x]) continue;

                    // Take 0 copies
                    next[cnt][x] = true;

                    // Take 1 copy
                    if (cnt + 1 <= 3)
                        next[cnt + 1][x ^ v] = true;

                    // Take 2 copies (v ^ v = 0)
                    if (cnt + 2 <= 3)
                        next[cnt + 2][x] = true;

                    // Take 3 copies (v ^ v ^ v = v)
                    if (cnt + 3 <= 3)
                        next[cnt + 3][x ^ v] = true;
                }
            }

            dp = next;
        }

        int ans = 0;
        for (int x = 0; x < MAX; x++) {
            if (dp[3][x]) ans++;
        }
    return ans;
    }
}