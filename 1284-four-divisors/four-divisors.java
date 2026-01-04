class Solution {
    public int sumFourDivisors(int[] nums) {
        int result = 0;

        for (int n : nums) {
            int sum = 0;
            int count = 0;

            for (int d = 1; d * d <= n; d++) {
                if (n % d == 0) {
                    int other = n / d;

                    if (d == other) {
                        count++;
                        sum += d;
                    } else {
                        count += 2;
                        sum += d + other;
                    }

                    // Stop early if more than 4 divisors
                    if (count > 4) {
                        break;
                    }
                }
            }

            if (count == 4) {
                result += sum;
            }
        }

        return result;
    }
}
