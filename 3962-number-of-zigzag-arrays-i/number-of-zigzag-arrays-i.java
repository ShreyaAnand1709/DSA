class Solution {
    static final long MOD = 1_000_000_007L;

    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;

        // Length 2 states
        long[] up = new long[m];
        long[] down = new long[m];

        // Initialize for length = 2
        for (int v = 0; v < m; v++) {
            up[v] = v;              // values smaller than v
            down[v] = m - 1 - v;    // values greater than v
        }

        // Build lengths 3 to n
        for (int len = 3; len <= n; len++) {

            long[] prefixUp = new long[m + 1];
            long[] prefixDown = new long[m + 1];

            for (int i = 0; i < m; i++) {
                prefixUp[i + 1] = (prefixUp[i] + up[i]) % MOD;
                prefixDown[i + 1] = (prefixDown[i] + down[i]) % MOD;
            }

            long[] newUp = new long[m];
            long[] newDown = new long[m];

            for (int v = 0; v < m; v++) {

                // Previous move must be DOWN
                // Choose previous value smaller than v
                newUp[v] = prefixDown[v] % MOD;

                // Previous move must be UP
                // Choose previous value greater than v
                newDown[v] =
                        (prefixUp[m] - prefixUp[v + 1] + MOD) % MOD;
            }

            up = newUp;
            down = newDown;
        }

        // Special case n=2 (not needed for given constraints but safe)
        if (n == 2) {
            long ans = 0;
            for (int v = 0; v < m; v++) {
                ans = (ans + up[v] + down[v]) % MOD;
            }
            return (int) ans;
        }

        long answer = 0;

        for (int v = 0; v < m; v++) {
            answer = (answer + up[v] + down[v]) % MOD;
        }

        return (int) answer;
    }
}