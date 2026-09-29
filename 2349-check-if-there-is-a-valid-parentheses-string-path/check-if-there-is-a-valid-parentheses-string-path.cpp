
class Solution {
public:
    int m, n;
    int dp[105][105][205];

    bool solve(int i, int j, int cnt, vector<vector<char>>& grid) {
        if (cnt < 0) return false;

        cnt += (grid[i][j] == '(') ? 1 : -1;

        if (cnt < 0) return false;

        if (i == m - 1 && j == n - 1) {
            return cnt == 0;
        }

        int rem = (m - 1 - i) + (n - 1 - j);
        if (cnt > rem) return false;

        if (dp[i][j][cnt] != -1) {
            return dp[i][j][cnt];
        }

        bool ans = false;

        if (i + 1 < m) {
            ans = ans || solve(i + 1, j, cnt, grid);
        }

        if (j + 1 < n) {
            ans = ans || solve(i, j + 1, cnt, grid);
        }

        return dp[i][j][cnt] = ans;
    }

    bool hasValidPath(vector<vector<char>>& grid) {
        m = grid.size();
        n = grid[0].size();

        if ((m + n) % 2 == 0) return false;

        memset(dp, -1, sizeof(dp));

        return solve(0, 0, 0, grid);
    }
};