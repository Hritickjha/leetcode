import java.util.Arrays;

class Solution {
    public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
        // Find the maximum consecutive sequence in both bar sets
        int maxH = getMaxConsecutive(hBars);
        int maxV = getMaxConsecutive(vBars);
        
        // The side of the hole is (number of consecutive bars removed + 1)
        int side = Math.min(maxH + 1, maxV + 1);
        
        return side * side;
    }

    private int getMaxConsecutive(int[] bars) {
        Arrays.sort(bars);
        int maxLen = 1;
        int currentLen = 1;
        
        for (int i = 1; i < bars.length; i++) {
            // Check if current bar is consecutive to the previous one
            if (bars[i] == bars[i - 1] + 1) {
                currentLen++;
            } else {
                currentLen = 1;
            }
            maxLen = Math.max(maxLen, currentLen);
        }
        
        return maxLen;
    }
}