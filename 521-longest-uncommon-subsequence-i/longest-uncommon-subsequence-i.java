public class Solution {
    public int findLUSlength(String a, String b) {
        // If both strings are equal, every subsequence of one
        // is also a subsequence of the other → return -1
        if (a.equals(b)) {
            return -1;
        }

        // Otherwise, the longer string itself is the longest uncommon subsequence
        // because it cannot be a subsequence of the shorter one.
        return Math.max(a.length(), b.length());
    }
}
