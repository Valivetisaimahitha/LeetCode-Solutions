class Solution {
    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        int[] count = new int[26];

        // Count characters
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        // Check whether palindrome is possible
        int odd = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 == 1) {
                odd++;
                middle = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        // We only need to construct the left half
        int half = n / 2;

        int[] leftCount = new int[26];

        for (int i = 0; i < 26; i++) {
            leftCount[i] = count[i] / 2;
        }

        /*
         * First try to make the left half exactly
         * equal to target's left half.
         */
        for (int i = 0; i < half; i++) {
            leftCount[target.charAt(i) - 'a']--;
        }

        // If exact prefix is possible, check the palindrome
        // formed by mirroring target's left half.
        boolean possible = true;

        for (int x : leftCount) {
            if (x < 0) {
                possible = false;
                break;
            }
        }

        if (possible) {

            String left = target.substring(0, half);

            StringBuilder right = new StringBuilder(left).reverse();

            String candidate;

            if (n % 2 == 1) {
                candidate = left + middle + right;
            } else {
                candidate = left + right;
            }

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        /*
         * Backtrack from the right side of the left half.
         *
         * We want to change the latest possible position
         * to the smallest character greater than target[i].
         */
        for (int i = half - 1; i >= 0; i--) {

            // Restore the character at position i
            int current = target.charAt(i) - 'a';
            leftCount[current]++;

            // Check whether target[0 ... i-1] can be kept
            boolean valid = true;

            for (int x : leftCount) {
                if (x < 0) {
                    valid = false;
                    break;
                }
            }

            if (!valid) {
                continue;
            }

            // Find the smallest character > target[i]
            for (int j = current + 1; j < 26; j++) {

                if (leftCount[j] > 0) {

                    leftCount[j]--;

                    StringBuilder left = new StringBuilder();

                    // Prefix equal to target
                    left.append(target, 0, i);

                    // Make current position larger
                    left.append((char) ('a' + j));

                    // Fill remaining positions smallest first
                    for (int c = 0; c < 26; c++) {
                        for (int x = 0; x < leftCount[c]; x++) {
                            left.append((char) ('a' + c));
                        }
                    }

                    String leftPart = left.toString();

                    StringBuilder right =
                            new StringBuilder(leftPart).reverse();

                    String answer;

                    if (n % 2 == 1) {
                        answer = leftPart + middle + right;
                    } else {
                        answer = leftPart + right;
                    }

                    return answer;
                }
            }
        }

        return "";
    }
}