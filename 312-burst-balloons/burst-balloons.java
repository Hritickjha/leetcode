class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        // Create a new array with 1 padded at both ends
        int[] arr = new int[n + 2];
        arr[0] = 1;
        arr[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            arr[i + 1] = nums[i];
        }

        // dp[left][right] = max coins from bursting balloons in (left, right)
        int[][] dp = new int[n + 2][n + 2];

        // length is the distance between left and right
        for (int len = 2; len < n + 2; len++) {
            for (int left = 0; left < n + 2 - len; left++) {
                int right = left + len;
                // choose the last balloon to burst between left and right
                for (int k = left + 1; k < right; k++) {
                    dp[left][right] = Math.max(
                        dp[left][right],
                        arr[left] * arr[k] * arr[right] + dp[left][k] + dp[k][right]
                    );
                }
            }
        }

        return dp[0][n + 1];
    }
}
