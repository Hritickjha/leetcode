import java.util.*;

public class Solution {
    public long minimumDifference(int[] nums) {
        int n = nums.length / 3;

        // Left heap: max heap to maintain n smallest values
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        long[] leftSum = new long[nums.length];
        long currentLeftSum = 0;

        for (int i = 0; i < 2 * n; i++) {
            maxHeap.offer(nums[i]);
            currentLeftSum += nums[i];

            if (maxHeap.size() > n) {
                currentLeftSum -= maxHeap.poll();  // Remove largest (not needed)
            }

            if (maxHeap.size() == n) {
                leftSum[i] = currentLeftSum;
            }
        }

        // Right heap: min heap to maintain n largest values
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        long[] rightSum = new long[nums.length];
        long currentRightSum = 0;

        for (int i = nums.length - 1; i >= n; i--) {
            minHeap.offer(nums[i]);
            currentRightSum += nums[i];

            if (minHeap.size() > n) {
                currentRightSum -= minHeap.poll();  // Remove smallest (not needed)
            }

            if (minHeap.size() == n) {
                rightSum[i] = currentRightSum;
            }
        }

        // Find the minimum difference between left and right sum
        long result = Long.MAX_VALUE;
        for (int i = n - 1; i < 2 * n; i++) {
            if (rightSum[i + 1] != 0) {
                result = Math.min(result, leftSum[i] - rightSum[i + 1]);
            }
        }

        return result;
    }
}
