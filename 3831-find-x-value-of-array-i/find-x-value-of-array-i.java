
class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] ans = new long[k];
        long[] dp = new long[k];

        for (int num : nums) {
            int mod = num % k;

            long[] newDp = new long[k];

            // Start a new subarray with only the current number
            newDp[mod]++;

            // Extend previous subarrays
            for (int r = 0; r < k; r++) {
                int newRemainder = (int) ((1L * r * mod) % k);

                newDp[newRemainder] += dp[r];
            }

            // Add counts to the final answer
            for (int r = 0; r < k; r++) {
                ans[r] += newDp[r];
            }

            // Move to the next index
            dp = newDp;
        }

        return ans;
    }
}