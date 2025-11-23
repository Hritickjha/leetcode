class Solution {
    public int maxSumDivThree(int[] nums) {
        int sum = 0;

        // Track the smallest two numbers with remainder 1 and 2
        int min1_1 = Integer.MAX_VALUE, min1_2 = Integer.MAX_VALUE;
        int min2_1 = Integer.MAX_VALUE, min2_2 = Integer.MAX_VALUE;

        for (int num : nums) {
            sum += num;

            int r = num % 3;
            if (r == 1) {
                if (num < min1_1) {
                    min2_1 = min1_1;
                    min1_1 = num;
                } else if (num < min2_1) {
                    min2_1 = num;
                }
            } else if (r == 2) {
                if (num < min1_2) {
                    min2_2 = min1_2;
                    min1_2 = num;
                } else if (num < min2_2) {
                    min2_2 = num;
                }
            }
        }

        int remainder = sum % 3;

        if (remainder == 0) return sum;

        int remove1 = Integer.MAX_VALUE;
        int remove2 = Integer.MAX_VALUE;

        if (remainder == 1) {
            remove1 = min1_1;                    // remove one num % 3 == 1
            if (min1_2 != Integer.MAX_VALUE && min2_2 != Integer.MAX_VALUE) {
                remove2 = min1_2 + min2_2;      // remove two nums % 3 == 2
            }
        } else {  // remainder == 2
            remove1 = min1_2;                    // remove one num % 3 == 2
            if (min1_1 != Integer.MAX_VALUE && min2_1 != Integer.MAX_VALUE) {
                remove2 = min1_1 + min2_1;      // remove two nums % 3 == 1
            }
        }

        int remove = Math.min(remove1, remove2);

        if (remove == Integer.MAX_VALUE) return 0;

        return sum - remove;
    }
}
