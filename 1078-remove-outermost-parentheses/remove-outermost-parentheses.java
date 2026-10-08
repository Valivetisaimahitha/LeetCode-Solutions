class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder ans = new StringBuilder();
        int bal = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                if (bal > 0) {
                    ans.append(c);
                }
                bal++;
            } else {
                bal--;
                if (bal > 0) {
                    ans.append(c);
                }
            }
        }

        return ans.toString();
    }
}