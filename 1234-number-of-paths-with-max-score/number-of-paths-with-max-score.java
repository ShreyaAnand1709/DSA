class Solution {
    public int[] pathsWithMaxScore(List<String> board) {
       int n = board.size();
        int MOD = 1_000_000_007;

        int[][] score = new int[n][n];
        long[][] ways = new long[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(score[i], -1);
        }

        score[n - 1][n - 1] = 0;
        ways[n - 1][n - 1] = 1;

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                char ch = board.get(i).charAt(j);

                if (ch == 'X' || (i == n - 1 && j == n - 1))
                    continue;

                int maxScore = -1;
                long count = 0;

                int[][] dirs = {{1, 0}, {0, 1}, {1, 1}};

                for (int[] d : dirs) {
                    int ni = i + d[0];
                    int nj = j + d[1];

                    if (ni >= n || nj >= n)
                        continue;

                    if (score[ni][nj] == -1)
                        continue;

                    if (score[ni][nj] > maxScore) {
                        maxScore = score[ni][nj];
                        count = ways[ni][nj];
                    } else if (score[ni][nj] == maxScore) {
                        count = (count + ways[ni][nj]) % MOD;
                    }
                }

                if (maxScore == -1)
                    continue;

                int value = 0;
                if (ch != 'E' && ch != 'S') {
                    value = ch - '0';
                }

                score[i][j] = maxScore + value;
                ways[i][j] = count % MOD;
            }
        }

        if (ways[0][0] == 0)
            return new int[]{0, 0};

        return new int[]{
            score[0][0],
            (int)(ways[0][0] % MOD)
        }; 
    }
}