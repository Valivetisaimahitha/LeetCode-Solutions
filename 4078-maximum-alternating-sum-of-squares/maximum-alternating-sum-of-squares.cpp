
class Solution {
public:
    long long maxAlternatingSum(vector<int>& nums) {
        vector<long long> squares;

        for (int x : nums) {
            squares.push_back(1LL * x * x);
        }

        sort(squares.begin(), squares.end());

        int n = squares.size();
        int mid = n / 2;
        long long ans = 0;

        for (int i = 0; i < mid; i++) {
            ans -= squares[i];
        }

        for (int i = mid; i < n; i++) {
            ans += squares[i];
        }

        return ans;
    }
};
