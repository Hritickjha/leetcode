import java.util.*;

class Solution {
    static final long MOD = 1_000_000_007;

    public int countTrapezoids(int[][] points) {
        Map<Integer, Integer> countByY = new HashMap<>();
        for (int[] p : points) {
            countByY.put(p[1], countByY.getOrDefault(p[1], 0) + 1);
        }

        // Precompute C(cnt, 2) for each horizontal level
        List<Long> combos = new ArrayList<>();
        for (int cnt : countByY.values()) {
            if (cnt >= 2) {
                long c2 = (long) cnt * (cnt - 1) / 2;
                combos.add(c2 % MOD);
            }
        }

        long result = 0;

        // Sum over all unordered pairs of distinct y-lines
        // ∑ C(cnt[y1],2) * C(cnt[y2],2)
        long prefixSum = 0;
        for (long c2 : combos) {
            result = (result + c2 * prefixSum) % MOD;
            prefixSum = (prefixSum + c2) % MOD;
        }

        return (int) result;
    }
}
