class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int k = 1; k <= n; k++) {
            if (n % k == 0 && check(nums, k)) {
                ans += k;
            }
        }
        return ans;
    }

    private boolean check(int[] nums, int k) {
        int n = nums.length;
        int prevMax = Integer.MIN_VALUE;

        for (int start = 0; start < n; start += k) {
            int descents = 0;
            int pos = -1; // index p with nums[p] > nums[p+1] inside the block

            for (int i = start; i + 1 < start + k; i++) {
                if (nums[i] > nums[i + 1]) {
                    descents++;
                    pos = i;
                    if (descents > 1) return false;
                }
            }

            // circular drop from the last element back to the first
            if (k > 1 && nums[start + k - 1] > nums[start]) {
                descents++;
            }
            if (descents > 1) return false;

            int mn, mx;
            if (descents == 0) {
                // constant block
                mn = nums[start];
                mx = nums[start];
            } else if (pos == -1) {
                // only the wrap-around drop: block is already sorted
                mn = nums[start];
                mx = nums[start + k - 1];
            } else {
                // one internal descent: rotating to start at pos+1 sorts it
                mn = nums[pos + 1];
                mx = nums[pos];
            }

            if (prevMax > mn) return false;
            prevMax = mx;
        }
        return true;
    }
}