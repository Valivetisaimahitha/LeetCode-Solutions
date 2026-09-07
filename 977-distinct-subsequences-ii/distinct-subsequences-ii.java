class Solution {
    public int distinctSubseqII(String s) {

        final int MOD = 1000000007;

        // dp[i] = number of distinct subsequences
        // ending with character ('a' + i)
        long[] dp = new long[26];

        long total = 0;

        for (char ch : s.toCharArray()) {

            int index = ch - 'a';

            // New subsequences created by using this character
            long add = (total - dp[index] + 1 + MOD) % MOD;

            dp[index] = (dp[index] + add) % MOD;

            total = (total + add) % MOD;
        }

        return (int) total;
    }
}