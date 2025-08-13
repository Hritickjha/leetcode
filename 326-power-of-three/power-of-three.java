class Solution {
    public boolean isPowerOfThree(int n) {
        // 1162261467 is 3^19, the largest power of three that fits in 32-bit signed int
        return n > 0 && 1162261467 % n == 0;
    }
}
