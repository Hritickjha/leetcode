import java.util.*;

public class Solution {
    public boolean hasIncreasingSubarrays(List<Integer> nums, int k) {
        int n = nums.size();
        if (2 * k > n) return false;

        // Iterate over possible starting indices for the first subarray
        for (int i = 0; i + 2 * k - 1 < n; i++) {
            boolean firstIncreasing = true;
            boolean secondIncreasing = true;

            // Check first subarray nums[i..i+k-1]
            for (int j = i; j < i + k - 1; j++) {
                if (nums.get(j) >= nums.get(j + 1)) {
                    firstIncreasing = false;
                    break;
                }
            }

            // Check second subarray nums[i+k..i+2*k-1]
            if (firstIncreasing) {
                for (int j = i + k; j < i + 2 * k - 1; j++) {
                    if (nums.get(j) >= nums.get(j + 1)) {
                        secondIncreasing = false;
                        break;
                    }
                }

                if (secondIncreasing) return true;
            }
        }

        return false;
    }
}
