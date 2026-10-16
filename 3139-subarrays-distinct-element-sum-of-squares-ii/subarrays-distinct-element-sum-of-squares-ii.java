
import java.util.*;

class Solution {
    private static final long MOD = 1_000_000_007L;

    private int n;
    private long[] sum, sq, lazy;

    private void apply(int node, int l, int r, long val) {
        long len = r - l + 1;

        sq[node] = (sq[node] + 2 * val * sum[node]
                    + val * val * len) % MOD;

        sum[node] = (sum[node] + val * len) % MOD;
        lazy[node] += val;
    }

    private void push(int node, int l, int r) {
        if (lazy[node] == 0 || l == r) {
            return;
        }

        int mid = (l + r) / 2;

        apply(node * 2, l, mid, lazy[node]);
        apply(node * 2 + 1, mid + 1, r, lazy[node]);

        lazy[node] = 0;
    }

    private void update(int node, int l, int r,
                        int ql, int qr, int val) {
        if (ql <= l && r <= qr) {
            apply(node, l, r, val);
            return;
        }

        push(node, l, r);

        int mid = (l + r) / 2;

        if (ql <= mid) {
            update(node * 2, l, mid, ql, qr, val);
        }

        if (qr > mid) {
            update(node * 2 + 1, mid + 1, r, ql, qr, val);
        }

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % MOD;
    }

    public int sumCounts(int[] nums) {
        n = nums.length;

        sum = new long[4 * n + 5];
        sq = new long[4 * n + 5];
        lazy = new long[4 * n + 5];

        Map<Integer, Integer> last = new HashMap<>();
        long ans = 0;

        for (int i = 0; i < n; i++) {
            int left = last.getOrDefault(nums[i], -1) + 1;

            update(1, 0, n - 1, left, i, 1);

            ans = (ans + sq[1]) % MOD;
            last.put(nums[i], i);
        }

        return (int) ans;
    }
}
