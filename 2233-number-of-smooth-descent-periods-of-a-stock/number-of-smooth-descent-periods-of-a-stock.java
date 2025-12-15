class Solution {
    public long getDescentPeriods(int[] prices) {
        long result = 0;
        long length = 1; // length of current smooth descent
        
        for (int i = 0; i < prices.length; i++) {
            if (i > 0 && prices[i] == prices[i - 1] - 1) {
                length++; // extend descent
            } else {
                length = 1; // reset descent
            }
            result += length;
        }
        
        return result;
    }
}
