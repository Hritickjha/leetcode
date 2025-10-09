class Solution {
    public int change(int amount, int[] coins) {
        // dp[i] will store the number of combinations to make up amount i.
        // The size of dp array will be amount + 1, to include dp[0] up to dp[amount].
        int[] dp = new int[amount + 1];

        // Base case: There is exactly one way to make an amount of 0 (by using no coins).
        dp[0] = 1;

        // Iterate through each coin denomination.
        // This outer loop ensures that combinations are counted, not permutations.
        // For example, if coins are [1, 2], (1, 2) is counted once for amount 3, not as (1, 2) and (2, 1).
        for (int coin : coins) {
            // For each coin, iterate from the coin's value up to the total amount.
            // This is because we can only use a coin if the current amount 'j' is at least its value.
            for (int j = coin; j <= amount; j++) {
                // dp[j] is the number of ways to make amount j.
                // If we decide to use the current 'coin', then the remaining amount we need to make is 'j - coin'.
                // The number of ways to make 'j - coin' is dp[j - coin].
                // By adding dp[j - coin] to dp[j], we are including all combinations that include the current 'coin'
                // at least once.
                dp[j] += dp[j - coin];
            }
        }

        // The final answer is the number of combinations for the given 'amount'.
        return dp[amount];
    }
}