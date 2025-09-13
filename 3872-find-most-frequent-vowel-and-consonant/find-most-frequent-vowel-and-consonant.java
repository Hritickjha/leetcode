import java.util.*;

class Solution {
    public int maxFreqSum(String s) {
        Set<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int maxVowelFreq = 0;
        int maxConsonantFreq = 0;

        for (int i = 0; i < 26; i++) {
            char ch = (char) (i + 'a');
            if (vowels.contains(ch)) {
                maxVowelFreq = Math.max(maxVowelFreq, freq[i]);
            } else {
                maxConsonantFreq = Math.max(maxConsonantFreq, freq[i]);
            }
        }

        return maxVowelFreq + maxConsonantFreq;
    }
}
