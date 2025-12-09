import java.util.*;

class Solution {
    public int specialTriplets(int[] nums) {
        final int MOD = 1_000_000_007;
        int n = nums.length;

        // Suffix frequency map
        Map<Integer, Integer> suffix = new HashMap<>();
        for (int num : nums) {
            suffix.put(num, suffix.getOrDefault(num, 0) + 1);
        }

        // Prefix frequency map
        Map<Integer, Integer> prefix = new HashMap<>();
        long result = 0;

        for (int j = 0; j < n; j++) {
            // Remove nums[j] from suffix (we are at j now)
            suffix.put(nums[j], suffix.get(nums[j]) - 1);
            if (suffix.get(nums[j]) == 0) {
                suffix.remove(nums[j]);
            }

            int target = nums[j] * 2;

            // Count of nums[i] == target before j
            int leftCount = prefix.getOrDefault(target, 0);

            // Count of nums[k] == target after j
            int rightCount = suffix.getOrDefault(target, 0);

            // Contribution for this j
            result = (result + (1L * leftCount * rightCount) % MOD) % MOD;

            // Add nums[j] to prefix
            prefix.put(nums[j], prefix.getOrDefault(nums[j], 0) + 1);
        }

        return (int) result;
    }
}