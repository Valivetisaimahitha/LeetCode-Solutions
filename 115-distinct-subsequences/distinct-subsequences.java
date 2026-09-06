class Solution {
    public int numDistinct(String s, String t) {

        int n = t.length();

        int[] dp = new int[n + 1];

        // Empty string can always be formed in one way
        dp[0] = 1;

        for (int i = 0; i < s.length(); i++) {

            // Go backwards so previous values are not overwritten
            for (int j = n; j >= 1; j--) {

                if (s.charAt(i) == t.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
                }
            }
        }

        return dp[n];
    }
}