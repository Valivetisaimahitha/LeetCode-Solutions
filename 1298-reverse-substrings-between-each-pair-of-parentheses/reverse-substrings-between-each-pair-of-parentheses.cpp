class Solution {
public:
    string reverseParentheses(string s) {
        vector<char> st;

        for (char ch : s) {
            if (ch == ')') {
                string cur;

                while (st.back() != '(') {
                    cur += st.back();
                    st.pop_back();
                }

                st.pop_back();

                for (char x : cur) {
                    st.push_back(x);
                }
            } else {
                st.push_back(ch);
            }
        }

        return string(st.begin(), st.end());
    }
};