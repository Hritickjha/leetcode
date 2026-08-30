class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        int minIndex = 0;
        int maxIndex = 0;

        // Find indices of minimum and maximum
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIndex]) {
                minIndex = i;
            }
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        // Put minIndex before maxIndex
        int left = Math.min(minIndex, maxIndex);
        int right = Math.max(minIndex, maxIndex);

        // Three possible ways:
        // 1. Remove both from the front
        int removeFront = right + 1;

        // 2. Remove both from the back
        int removeBack = n - left;

        // 3. Remove left from front and right from back
        int removeBoth = (left + 1) + (n - right);

        return Math.min(removeFront, Math.min(removeBack, removeBoth));
    }
}