class Solution {
    public int findSmallestInteger(int[] nums, int value) {
        int[] count = new int[value];
        for (int x : nums) {
            int r = (x % value + value) % value;
            count[r]++;
        }
        int i = 0;
        while (true) {
            int r = i % value;
            if (count[r] > 0) {
                count[r]--;
            } else {
                return i;
            }
            i++;
        }
    }
}