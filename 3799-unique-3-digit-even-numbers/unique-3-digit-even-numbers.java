class Solution {
    public int totalNumbers(int[] digits) {
        int[] count = new int[10];

        // Count frequency of each digit
        for (int digit : digits) {
            count[digit]++;
        }

        int result = 0;

        // Try every possible 3-digit number
        for (int hundreds = 1; hundreds <= 9; hundreds++) {
            if (count[hundreds] == 0) continue;

            count[hundreds]--;

            for (int tens = 0; tens <= 9; tens++) {
                if (count[tens] == 0) continue;

                count[tens]--;

                // Last digit must be even
                for (int units = 0; units <= 8; units += 2) {
                    if (count[units] > 0) {
                        result++;
                    }
                }

                count[tens]++;
            }

            count[hundreds]++;
        }

        return result;
    }
}