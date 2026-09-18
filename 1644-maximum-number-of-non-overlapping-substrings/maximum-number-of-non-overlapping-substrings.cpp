class Solution {
public:
    vector<string> maxNumOfSubstrings(string s) {

        int n = s.size();

        vector<int> first(26, n);
        vector<int> last(26, -1);

        // Find first and last occurrence of each character
        for (int i = 0; i < n; i++) {
            int c = s[i] - 'a';

            first[c] = min(first[c], i);
            last[c] = i;
        }

        vector<pair<int, int>> intervals;

        // Create valid intervals
        for (int c = 0; c < 26; c++) {

            if (last[c] == -1) {
                continue;
            }

            int left = first[c];
            int right = last[c];

            bool valid = true;

            for (int i = left; i <= right; i++) {

                int x = s[i] - 'a';

                // This character occurs before left
                if (first[x] < left) {
                    valid = false;
                    break;
                }

                // Expand right boundary
                right = max(right, last[x]);
            }

            if (valid) {
                intervals.push_back({left, right});
            }
        }

        // Sort by ending position
        sort(intervals.begin(), intervals.end(),
             [](const pair<int, int>& a,
                const pair<int, int>& b) {
                 return a.second < b.second;
             });

        vector<string> ans;

        int previousEnd = -1;

        // Greedily choose non-overlapping intervals
        for (int i = 0; i < intervals.size(); i++) {

            int left = intervals[i].first;
            int right = intervals[i].second;

            if (left > previousEnd) {

                ans.push_back(s.substr(left, right - left + 1));

                previousEnd = right;
            }
        }

        return ans;
    }
};