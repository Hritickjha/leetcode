import java.util.*;

public class Solution {
    public int maxFrequencyElements(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();
        
        // Count frequency of each element
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        // Find maximum frequency
        int maxFreq = 0;
        for (int count : freq.values()) {
            maxFreq = Math.max(maxFreq, count);
        }
        
        // Count total elements that have max frequency
        int result = 0;
        for (int count : freq.values()) {
            if (count == maxFreq) {
                result += count;
            }
        }
        
        return result;
    }

    // Example usage
    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] nums1 = {1, 2, 2, 3, 1, 4};
        int[] nums2 = {1, 2, 3, 4, 5};

        System.out.println(sol.maxFrequencyElements(nums1)); // Output: 4
        System.out.println(sol.maxFrequencyElements(nums2)); // Output: 5
    }
}
