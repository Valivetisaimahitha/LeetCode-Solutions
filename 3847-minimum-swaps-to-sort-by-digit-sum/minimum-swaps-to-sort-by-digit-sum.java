
import java.util.*;

class Solution {
    public int minSwaps(int[] nums) {
        int n = nums.length;
        int[] sortedNums = nums.clone();

        Arrays.sort(sortedNums);

        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nums[i];
        }

        Arrays.sort(arr, (a, b) -> {
            int sa = digitSum(a);
            int sb = digitSum(b);

            if (sa != sb) {
                return Integer.compare(sa, sb);
            }

            return Integer.compare(a, b);
        });

        Map<Integer, Integer> pos = new HashMap<>();

        for (int i = 0; i < n; i++) {
            pos.put(arr[i], i);
        }

        boolean[] visited = new boolean[n];
        int swaps = 0;

        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }

            int j = i;
            int cycleSize = 0;

            while (!visited[j]) {
                visited[j] = true;
                j = pos.get(nums[j]);
                cycleSize++;
            }

            if (cycleSize > 1) {
                swaps += cycleSize - 1;
            }
        }

        return swaps;
    }

    private int digitSum(int x) {
        int sum = 0;

        while (x > 0) {
            sum += x % 10;
            x /= 10;
        }

        return sum;
    }
}
