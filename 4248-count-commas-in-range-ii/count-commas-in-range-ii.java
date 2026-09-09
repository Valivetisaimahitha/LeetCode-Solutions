class Solution {
    public long countCommas(long n) {
        long ans = 0;
        long x = 1000;

        while (x <= n) {
            // All numbers from x to n have one additional comma
            ans += n - x + 1;

            // Avoid overflow
            if (x > n / 1000) {
                break;
            }

            x *= 1000;
        }

        return ans;
    }
}