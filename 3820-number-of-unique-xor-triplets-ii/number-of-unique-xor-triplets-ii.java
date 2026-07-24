class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAXX = 2048; // nums[i] <= 1500 < 2^11

        boolean[][] dp = new boolean[4][MAXX];
        dp[0][0] = true;

        for (int v : nums) {
            boolean[][] next = new boolean[4][MAXX];

            // Skip current index
            for (int c = 0; c <= 3; c++) {
                System.arraycopy(dp[c], 0, next[c], 0, MAXX);
            }

            // Take current index 1, 2, or 3 times
            for (int used = 0; used <= 3; used++) {
                for (int x = 0; x < MAXX; x++) {
                    if (!dp[used][x]) continue;

                    for (int take = 1; used + take <= 3; take++) {
                        int contrib = (take % 2 == 1) ? v : 0;
                        next[used + take][x ^ contrib] = true;
                    }
                }
            }

            dp = next;
        }

        int ans = 0;
        for (int x = 0; x < MAXX; x++) {
            if (dp[3][x]) ans++;
        }
        return ans;
    }
}