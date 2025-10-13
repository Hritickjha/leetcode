class Solution {
    public boolean detectCapitalUse(String word) {
        // Count the number of capital letters in the word
        int capitalCount = 0;
        for (char c : word.toCharArray()) {
            if (Character.isUpperCase(c)) {
                capitalCount++;
            }
        }

        // Check for the three valid cases:

        // Case 1: All letters are capitals (e.g., "USA")
        if (capitalCount == word.length()) {
            return true;
        }

        // Case 2: All letters are not capitals (e.g., "leetcode")
        if (capitalCount == 0) {
            return true;
        }

        // Case 3: Only the first letter is capital (e.g., "Google")
        // This means there should be exactly one capital letter,
        // and that single capital letter must be at the beginning of the word.
        if (capitalCount == 1 && Character.isUpperCase(word.charAt(0))) {
            return true;
        }

        // If none of the above conditions are met, the capital usage is incorrect.
        return false;
    }
}