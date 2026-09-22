
class Solution {

    class Node {
        int prod;
        int[] cnt = new int[5];

        Node() {
            prod = 1;
        }
    }

    int n, k;
    Node[] tree;

    Node merge(Node left, Node right) {
        Node parent = new Node();

        parent.prod = (left.prod * right.prod) % k;

        for (int r = 0; r < k; r++) {
            parent.cnt[r] = left.cnt[r];
        }

        for (int r = 0; r < k; r++) {
            int newRemainder = (left.prod * r) % k;
            parent.cnt[newRemainder] += right.cnt[r];
        }

        return parent;
    }

    void build(int[] nums, int node, int l, int r) {
        if (l == r) {
            int val = nums[l] % k;

            tree[node].prod = val;
            tree[node].cnt[val] = 1;

            return;
        }

        int mid = (l + r) / 2;

        build(nums, 2 * node + 1, l, mid);
        build(nums, 2 * node + 2, mid + 1, r);

        tree[node] = merge(
            tree[2 * node + 1],
            tree[2 * node + 2]
        );
    }

    void update(int node, int l, int r, int index, int value) {
        if (l == r) {
            value %= k;

            tree[node] = new Node();
            tree[node].prod = value;
            tree[node].cnt[value] = 1;

            return;
        }

        int mid = (l + r) / 2;

        if (index <= mid) {
            update(2 * node + 1, l, mid, index, value);
        } else {
            update(2 * node + 2, mid + 1, r, index, value);
        }

        tree[node] = merge(
            tree[2 * node + 1],
            tree[2 * node + 2]
        );
    }

    Node query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return tree[node];
        }

        if (r < ql || l > qr) {
            return new Node();
        }

        int mid = (l + r) / 2;

        Node left = query(
            2 * node + 1, l, mid, ql, qr
        );

        Node right = query(
            2 * node + 2, mid + 1, r, ql, qr
        );

        return merge(left, right);
    }

    public int[] resultArray(
        int[] nums,
        int k,
        int[][] queries
    ) {
        n = nums.length;
        this.k = k;

        tree = new Node[4 * n];

        for (int i = 0; i < 4 * n; i++) {
            tree[i] = new Node();
        }

        for (int i = 0; i < n; i++) {
            nums[i] %= k;
        }

        build(nums, 0, 0, n - 1);

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            update(0, 0, n - 1, index, value);

            Node result = query(
                0, 0, n - 1, start, n - 1
            );

            ans[i] = result.cnt[x];
        }

        return ans;
    }
}