class Solution {
    public boolean doesAliceWin(String s) {
        int vowelCount = 0;
        for (char c : s.toCharArray()) {
            if (isVowel(c)) {
                vowelCount++;
            }
        }
        // If no vowels, Alice cannot make a move
        return vowelCount > 0;
    }

    private boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}
