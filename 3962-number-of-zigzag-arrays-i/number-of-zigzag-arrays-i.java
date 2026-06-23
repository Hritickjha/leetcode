class Solution {
    private static final long MOD = 1_000_000_007L;

    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;

        if (n == 1) return m;
        if (n == 2) {
            return (int) ((1L * m * (m - 1)) % MOD);
        }

        long[] up = new long[m + 1];
        long[] down = new long[m + 1];

        // Length = 2
        for (int v = 1; v <= m; v++) {
            up[v] = v - 1;      // previous value < v
            down[v] = m - v;    // previous value > v
        }

        for (int len = 3; len <= n; len++) {
            long[] prefixUp = new long[m + 1];
            long[] prefixDown = new long[m + 1];

            for (int v = 1; v <= m; v++) {
                prefixUp[v] = (prefixUp[v - 1] + up[v]) % MOD;
                prefixDown[v] = (prefixDown[v - 1] + down[v]) % MOD;
            }

            long totalUp = prefixUp[m];

            long[] nextUp = new long[m + 1];
            long[] nextDown = new long[m + 1];

            for (int v = 1; v <= m; v++) {
                // Last move becomes UP, previous move must be DOWN
                nextUp[v] = prefixDown[v - 1];

                // Last move becomes DOWN, previous move must be UP
                nextDown[v] = (totalUp - prefixUp[v] + MOD) % MOD;
            }

            up = nextUp;
            down = nextDown;
        }

        long ans = 0;
        for (int v = 1; v <= m; v++) {
            ans = (ans + up[v] + down[v]) % MOD;
        }

        return (int) ans;
    }
}