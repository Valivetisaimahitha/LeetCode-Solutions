import java.util.*;

class Solution {

    static class Interval {
        int left, right, weight, index;

        Interval(int left, int right, int weight, int index) {
            this.left = left;
            this.right = right;
            this.weight = weight;
            this.index = index;
        }
    }

    static class State {
        long weight;
        List<Integer> selected;

        State(long weight, List<Integer> selected) {
            this.weight = weight;
            this.selected = selected;
        }
    }

    List<Interval> arr;
    State[][] memo;

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        arr = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            List<Integer> x = intervals.get(i);

            arr.add(new Interval(
                x.get(0),
                x.get(1),
                x.get(2),
                i
            ));
        }

        // Sort by starting point
        arr.sort((a, b) -> {
            if (a.left != b.left)
                return Integer.compare(a.left, b.left);

            return Integer.compare(a.right, b.right);
        });

        memo = new State[n][5];

        State result = dp(0, 4);

        int[] answer = new int[result.selected.size()];

        for (int i = 0; i < result.selected.size(); i++) {
            answer[i] = result.selected.get(i);
        }

        return answer;
    }

    private State dp(int i, int remaining) {

        // No more intervals or cannot select anymore
        if (i == arr.size() || remaining == 0) {
            return new State(0, new ArrayList<>());
        }

        if (memo[i][remaining] != null) {
            return memo[i][remaining];
        }

        // Option 1: Skip current interval
        State skip = dp(i + 1, remaining);

        // Option 2: Take current interval
        Interval cur = arr.get(i);

        int next = findNext(i + 1, cur.right);

        State nextState = dp(next, remaining - 1);

        List<Integer> selected = new ArrayList<>(
            nextState.selected
        );

        selected.add(cur.index);

        // Answer must be in increasing index order
        Collections.sort(selected);

        State take = new State(
            cur.weight + nextState.weight,
            selected
        );

        // Choose the better state
        if (take.weight > skip.weight) {
            return memo[i][remaining] = take;
        }

        if (take.weight < skip.weight) {
            return memo[i][remaining] = skip;
        }

        // Same weight → lexicographically smaller indices
        if (compare(take.selected, skip.selected) < 0) {
            return memo[i][remaining] = take;
        }

        return memo[i][remaining] = skip;
    }

    // First interval whose start > current end
    private int findNext(int start, int end) {

        int left = start;
        int right = arr.size();

        while (left < right) {

            int mid = left + (right - left) / 2;

            if (arr.get(mid).left > end) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    // Lexicographical comparison
    private int compare(List<Integer> a, List<Integer> b) {

        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {

            if (!a.get(i).equals(b.get(i))) {
                return Integer.compare(
                    a.get(i),
                    b.get(i)
                );
            }
        }

        // If one is prefix of another,
        // shorter list is lexicographically smaller
        return Integer.compare(a.size(), b.size());
    }
}