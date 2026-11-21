
import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<Integer>[] adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            adj[parent[i]].add(i);
        }

        int[] ans = new int[n];
        Arrays.fill(ans, 1);

        List<Integer>[] path = new ArrayList[26];

        for (int i = 0; i < 26; i++) {
            path[i] = new ArrayList<>();
        }

        Stack<int[]> st = new Stack<>();
        st.push(new int[]{0, -1, 0});

        while (!st.isEmpty()) {
            int[] curr = st.pop();

            int u = curr[0];
            int p = curr[1];
            int state = curr[2];

            int c = s.charAt(u) - 'a';

            if (state == 0) {
                path[c].add(u);

                st.push(new int[]{u, p, 1});

                for (int i = adj[u].size() - 1; i >= 0; i--) {
                    st.push(new int[]{adj[u].get(i), u, 0});
                }
            } else {
                int k = path[c].size() > 1
                    ? path[c].get(path[c].size() - 2)
                    : p;

                if (k != -1) {
                    ans[k] += ans[u];
                }

                path[c].remove(path[c].size() - 1);
            }
        }

        return ans;
    }
}
