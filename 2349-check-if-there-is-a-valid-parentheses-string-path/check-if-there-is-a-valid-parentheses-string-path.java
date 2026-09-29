
class Solution {
    int m, n;
    Boolean[][][] dp;

    boolean solve(int i, int j, int cnt, char[][] grid) {
        if (cnt < 0) return false;

        if (grid[i][j] == '(') {
            cnt++;
        } else {
            cnt--;
        }

        if (cnt < 0) return false;

        if (i == m - 1 && j == n - 1) {
            return cnt == 0;
        }

        int rem = (m - 1 - i) + (n - 1 - j);
        if (cnt > rem) return false;

        if (dp[i][j][cnt] != null) {
            return dp[i][j][cnt];
        }

        boolean ans = false;

        if (i + 1 < m) {
            ans = solve(i + 1, j, cnt, grid);
        }

        if (!ans && j + 1 < n) {
            ans = solve(i, j + 1, cnt, grid);
        }

        dp[i][j][cnt] = ans;
        return ans;
    }

    public boolean hasValidPath(char[][] grid) {
        m = grid.length;
        n = grid[0].length;

        if ((m + n) % 2 == 0) return false;

        dp = new Boolean[m][n][m + n];

        return solve(0, 0, 0, grid);
    }
}