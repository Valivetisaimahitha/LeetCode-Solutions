class Solution {
    public String lexGreaterPermutation(String s, String target) {

        int[] count = new int[26];

        // Count characters of s
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        // temp is used to try matching target
        int[] temp = count.clone();

        int pos = -1;

        for (int i = 0; i < target.length(); i++) {

            int x = target.charAt(i) - 'a';

            // Before consuming target[i],
            // check if we can put something greater here.
            for (int j = x + 1; j < 26; j++) {
                if (temp[j] > 0) {
                    pos = i;
                    break;
                }
            }

            // Cannot match target[i]
            if (temp[x] == 0) {
                break;
            }

            // Use target[i]
            temp[x]--;
        }

        // No position where we can make the string greater
        if (pos == -1) {
            return "";
        }

        StringBuilder ans = new StringBuilder();

        // Use target prefix before pos
        for (int i = 0; i < pos; i++) {
            char c = target.charAt(i);
            ans.append(c);
            count[c - 'a']--;
        }

        // At pos, choose the smallest character
        // greater than target[pos]
        int x = target.charAt(pos) - 'a';

        for (int j = x + 1; j < 26; j++) {

            if (count[j] > 0) {
                ans.append((char) ('a' + j));
                count[j]--;
                break;
            }
        }

        // Add remaining characters in sorted order
        for (int i = 0; i < 26; i++) {
            while (count[i] > 0) {
                ans.append((char) ('a' + i));
                count[i]--;
            }
        }

        return ans.toString();
    }
}