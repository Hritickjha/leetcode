import java.util.*;

public class Solution {
    public int[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];

        // Sliding window of size k
        for (int i = 0; i <= n - k; i++) {
            // Extract current subarray
            int[] sub = Arrays.copyOfRange(nums, i, i + k);

            // Calculate x-sum for this subarray
            ans[i] = getXSum(sub, x);
        }

        return ans;
    }

    private int getXSum(int[] arr, int x) {
        // Count frequencies
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : arr) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        // Sort elements by (frequency desc, value desc)
        List<int[]> freqList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            freqList.add(new int[]{e.getKey(), e.getValue()});
        }

        freqList.sort((a, b) -> {
            if (b[1] != a[1]) return b[1] - a[1]; // Higher frequency first
            return b[0] - a[0]; // If tie, higher number first
        });

        // Take top x elements
        Set<Integer> topX = new HashSet<>();
        for (int i = 0; i < Math.min(x, freqList.size()); i++) {
            topX.add(freqList.get(i)[0]);
        }

        // Sum only top x frequent elements
        int sum = 0;
        for (int num : arr) {
            if (topX.contains(num)) sum += num;
        }

        return sum;
    }

    // Example usage
    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] nums1 = {1, 1, 2, 2, 3, 4, 2, 3};
        int k1 = 6, x1 = 2;
        System.out.println(Arrays.toString(sol.findXSum(nums1, k1, x1))); // [6, 10, 12]

        int[] nums2 = {3, 8, 7, 8, 7, 5};
        int k2 = 2, x2 = 2;
        System.out.println(Arrays.toString(sol.findXSum(nums2, k2, x2))); // [11, 15, 15, 15, 12]
    }
}
