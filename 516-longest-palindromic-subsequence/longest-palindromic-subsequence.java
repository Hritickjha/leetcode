class Solution {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        
        // dp[i][j] will store the length of the longest palindromic subsequence
        // of the substring s[i...j].
        int[][] dp = new int[n][n];

        // Base case: A single character is a palindrome of length 1.
        // For substrings of length 1 (i.e., s[i...i]), the LPS length is 1.
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        // Fill the DP table for substrings of increasing length.
        // 'len' represents the length of the current substring.
        for (int len = 2; len <= n; len++) {
            // 'i' represents the starting index of the current substring.
            for (int i = 0; i <= n - len; i++) {
                // 'j' represents the ending index of the current substring.
                int j = i + len - 1;

                // Case 1: The characters at the ends of the substring match.
                // If s[i] == s[j], then these two characters can be part of the LPS.
                // The length will be 2 (for s[i] and s[j]) plus the LPS length
                // of the inner substring s[i+1...j-1].
                // If the inner substring is empty (i.e., len == 2), dp[i+1][j-1]
                // will be 0 as it's outside valid indices for a non-empty string,
                // or simply considered 0 length for an empty sequence.
                // Java initializes array elements to 0 by default, which correctly
                // handles the dp[i+1][j-1] term when i+1 > j-1 (e.g., for len=2,
                // dp[i+1][i] would be 0).
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = 2 + dp[i + 1][j - 1];
                } 
                // Case 2: The characters at the ends of the substring do not match.
                // If s[i] != s[j], then we cannot include both s[i] and s[j] in the LPS.
                // We must either exclude s[i] and find the LPS of s[i+1...j],
                // or exclude s[j] and find the LPS of s[i...j-1].
                // We take the maximum of these two possibilities.
                else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        // The result is the LPS length of the entire string s[0...n-1].
        return dp[0][n - 1];
    }
}