import java.util.HashMap;
import java.util.Map;

class Solution {
    int m_val, k_val; 
    int N; // nums.length
    int[] nums;
    long MOD = 1_000_000_007;

    // Memoization table: dp[idx][rem_m][carry_in] stores a map from bit_count_so_far to sum_of_products
    Map<Integer, Long>[][][] memo;

    // Precompute combinations (nCr)
    long[][] nCr;
    
    // Precompute powers of nums[idx] for efficiency: numsPowers[idx][count] = (nums[idx]^count) % MOD
    long[][] numsPowers;

    public int magicalSum(int m_param, int k_param, int[] nums_param) {
        this.m_val = m_param;
        this.k_val = k_param;
        this.nums = nums_param;
        this.N = nums.length;

        memo = new Map[N + 1][m_val + 1][m_val + 1];

        nCr = new long[m_val + 1][m_val + 1];
        for (int i = 0; i <= m_val; i++) {
            nCr[i][0] = 1; 
            for (int j = 1; j <= i; j++) {
                nCr[i][j] = (nCr[i-1][j-1] + nCr[i-1][j]) % MOD;
            }
        }
        
        numsPowers = new long[N][m_val + 1];
        for(int i = 0; i < N; i++) {
            numsPowers[i][0] = 1; 
            for(int j = 1; j <= m_val; j++) {
                numsPowers[i][j] = (numsPowers[i][j-1] * nums[i]) % MOD;
            }
        }

        Map<Integer, Long> finalResults = solve(0, m_val, 0);

        // Corrected line: Use .intValue() to explicitly convert Long object to int primitive.
        return finalResults.getOrDefault(k_val, 0L).intValue();
    }

    private Map<Integer, Long> solve(int idx, int rem_m, int carry_in) {
        if (idx == N) {
            if (rem_m == 0) {
                Map<Integer, Long> res = new HashMap<>();
                res.put(Integer.bitCount(carry_in), 1L);
                return res;
            } else {
                return new HashMap<>(); 
            }
        }

        if (memo[idx][rem_m][carry_in] != null) {
            return memo[idx][rem_m][carry_in];
        }

        Map<Integer, Long> currentResults = new HashMap<>();

        for (int c_idx = 0; c_idx <= rem_m; c_idx++) {
            int bit_val = (c_idx + carry_in) % 2;
            int next_carry_in = (c_idx + carry_in) / 2;

            Map<Integer, Long> nextStates = solve(idx + 1, rem_m - c_idx, next_carry_in);

            if (nextStates.isEmpty()) {
                continue;
            }

            long prod_contrib_from_num = numsPowers[idx][c_idx];
            long combinations_for_this_idx = nCr[rem_m][c_idx];

            for (Map.Entry<Integer, Long> entry : nextStates.entrySet()) {
                int prev_effective_bits_set = entry.getKey();
                long prev_prod_sum = entry.getValue();

                int new_effective_bits_set = prev_effective_bits_set + bit_val;

                if (new_effective_bits_set > k_val) {
                    continue;
                }

                long new_prod_sum = (prev_prod_sum * prod_contrib_from_num) % MOD;
                new_prod_sum = (new_prod_sum * combinations_for_this_idx) % MOD;
                
                currentResults.merge(new_effective_bits_set, new_prod_sum, (oldVal, newVal) -> (oldVal + newVal) % MOD);
            }
        }

        memo[idx][rem_m][carry_in] = currentResults;
        return currentResults;
    }
}