class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = n + 1;

        // best[i] = minimum length of a valid subarray
        // found in arr[0...i]
        int[] best = new int[n];

        for (int i = 0; i < n; i++) {
            best[i] = INF;
        }

        int left = 0;
        long sum = 0;
        int ans = INF;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            // Since all elements are positive,
            // move left while sum is greater than target.
            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            // Found a subarray [left...right] with sum = target
            if (sum == target) {
                int len = right - left + 1;

                // Check if another non-overlapping subarray
                // exists before this one.
                if (left > 0 && best[left - 1] != INF) {
                    ans = Math.min(ans, len + best[left - 1]);
                }

                // Update the best subarray ending at or before right
                best[right] = len;
            }

            // Carry the previous best value forward
            if (right > 0) {
                best[right] = Math.min(best[right], best[right - 1]);
            }
        }

        return ans == INF ? -1 : ans;
    }
}