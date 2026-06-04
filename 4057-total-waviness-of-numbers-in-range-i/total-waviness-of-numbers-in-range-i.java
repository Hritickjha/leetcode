class Solution {
    /**
     * Helper method to calculate waviness of a single number
     * Waviness is the count of peaks and valleys
     */
    private int countWaviness(int num) {
        String s = String.valueOf(num);
        
        // Numbers with fewer than 3 digits have waviness 0
        if (s.length() < 3) {
            return 0;
        }
        
        int waviness = 0;
        
        // Check each digit from index 1 to length-2 (excluding first and last)
        for (int i = 1; i < s.length() - 1; i++) {
            int current = s.charAt(i) - '0';
            int prev = s.charAt(i - 1) - '0';
            int next = s.charAt(i + 1) - '0';
            
            // Check if current digit is a peak
            // (strictly greater than both neighbors)
            if (current > prev && current > next) {
                waviness++;
            }
            // Check if current digit is a valley
            // (strictly less than both neighbors)
            else if (current < prev && current < next) {
                waviness++;
            }
        }
        
        return waviness;
    }
    
    /**
     * Calculate total waviness for all numbers in range [num1, num2]
     */
    public int totalWaviness(int num1, int num2) {
        int total = 0;
        
        // Iterate through each number in the range
        for (int num = num1; num <= num2; num++) {
            total += countWaviness(num);
        }
        
        return total;
    }
}

// Test cases
class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Example 1: num1 = 120, num2 = 130
        // Output: 3
        // 120 (peak at 2), 121 (peak at 2), 130 (peak at 3)
        int result1 = solution.totalWaviness(120, 130);
        System.out.println("Example 1: " + result1 + " (expected: 3)");
        
        // Example 2: num1 = 198, num2 = 202
        // Output: 3
        // 198 (peak at 9), 201 (valley at 0), 202 (valley at 0)
        int result2 = solution.totalWaviness(198, 202);
        System.out.println("Example 2: " + result2 + " (expected: 3)");
        
        // Example 3: num1 = 4848, num2 = 4848
        // Output: 2
        // 4848 (peak at 8, valley at 4)
        int result3 = solution.totalWaviness(4848, 4848);
        System.out.println("Example 3: " + result3 + " (expected: 2)");
    }
}