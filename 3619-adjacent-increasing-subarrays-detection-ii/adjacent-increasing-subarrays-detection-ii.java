import java.util.*;

class Solution {
    public int maxIncreasingSubarrays(List<Integer> numsList) {
        int n = numsList.size();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = numsList.get(i);
        
        int[] incLeft = new int[n];
        int[] incRight = new int[n];
        
        // Step 1: Compute lengths of increasing subarrays ending at each index
        incLeft[0] = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                incLeft[i] = incLeft[i - 1] + 1;
            } else {
                incLeft[i] = 1;
            }
        }
        
        // Step 2: Compute lengths of increasing subarrays starting at each index
        incRight[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                incRight[i] = incRight[i + 1] + 1;
            } else {
                incRight[i] = 1;
            }
        }
        
        // Step 3: Find max possible k
        int ans = 0;
        for (int i = 0; i < n - 1; i++) {
            int k = Math.min(incLeft[i], incRight[i + 1]);
            ans = Math.max(ans, k);
        }
        
        return ans;
    }
}
