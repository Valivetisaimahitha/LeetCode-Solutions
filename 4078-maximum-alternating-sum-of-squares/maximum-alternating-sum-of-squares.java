
import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        long[] squares = new long[nums.length];

        for (int i = 0; i < nums.length; i++) {
            squares[i] = 1L * nums[i] * nums[i];
        }

        Arrays.sort(squares);

        int mid = nums.length / 2;
        long ans = 0;

        for (int i = 0; i < mid; i++) {
            ans -= squares[i];
        }

        for (int i = mid; i < nums.length; i++) {
            ans += squares[i];
        }

        return ans;
    }
}
