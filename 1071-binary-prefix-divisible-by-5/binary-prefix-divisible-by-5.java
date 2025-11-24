import java.util.*;

class Solution {
    public List<Boolean> prefixesDivBy5(int[] nums) {
        List<Boolean> result = new ArrayList<>(nums.length);
        int prefix = 0;  // will store prefix modulo 5

        for (int bit : nums) {
            prefix = ((prefix << 1) + bit) % 5;  // same as prefix * 2 + bit
            result.add(prefix == 0);
        }

        return result;
    }
}
