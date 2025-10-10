import java.util.Arrays;

class Solution {
    public int maximumEnergy(int[] energy, int k) {
        int n = energy.length;
        
        // dp[i] will store the maximum energy gained if we start at magician i.
        // We initialize it to store the energy values themselves, then update.
        int[] dp = new int[n];
        
        // Initialize maxEnergy to the smallest possible integer value,
        // as energy values can be negative.
        int maxEnergy = Integer.MIN_VALUE;
        
        // Iterate from the last magician down to the first.
        // This ensures that when we calculate dp[i], dp[i+k] is already computed.
        for (int i = n - 1; i >= 0; i--) {
            // Base case: If starting at magician i and jumping k steps
            // leads us out of bounds (i.e., i + k is an invalid index),
            // then we only absorb energy from magician i.
            if (i + k >= n) {
                dp[i] = energy[i];
            } else {
                // Recursive step: We absorb energy from magician i, and then
                // add the maximum energy we can get by starting from magician (i + k).
                dp[i] = energy[i] + dp[i + k];
            }
            
            // Update the overall maximum energy found so far.
            maxEnergy = Math.max(maxEnergy, dp[i]);
        }
        
        return maxEnergy;
    }
}