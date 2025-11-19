class Solution {
    public int findFinalValue(int[] nums, int original) {
        // Use a HashSet for O(1) lookup time
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        // Keep doubling original while it exists in the set
        while (set.contains(original)) {
            original *= 2;
        }

        return original;
    }
}
