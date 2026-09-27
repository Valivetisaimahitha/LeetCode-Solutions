import java.util.*;

class Solution {
    public String reverseParentheses(String s) {
        Stack<Character> st = new Stack<>();

        for (char ch : s.toCharArray()) {
            if (ch == ')') {
                StringBuilder cur = new StringBuilder();

                while (st.peek() != '(') {
                    cur.append(st.pop());
                }

                st.pop(); // remove '('

                for (int i = 0; i < cur.length(); i++) {
                    st.push(cur.charAt(i));
                }
            } else {
                st.push(ch);
            }
        }

        StringBuilder ans = new StringBuilder();

        for (char ch : st) {
            ans.append(ch);
        }

        return ans.toString();
    }
}