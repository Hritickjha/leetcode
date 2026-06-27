import java.util.*;

class Solution {
    public int maximumLength(int[] nums) {
        Map<Long, Integer> freq = new HashMap<>();

        for (int x : nums) {
            freq.put((long) x, freq.getOrDefault((long) x, 0) + 1);
        }

        int ans = 1;

        // Handle value 1 separately
        if (freq.containsKey(1L)) {
            int cnt = freq.get(1L);
            ans = Math.max(ans, (cnt % 2 == 0) ? cnt - 1 : cnt);
        }

        for (long start : freq.keySet()) {
            if (start == 1L) continue;

            long cur = start;
            int len = 0;

            while (true) {
                Integer cnt = freq.get(cur);
                if (cnt == null) break;

                if (cnt >= 2) {
                    len += 2;

                    // Prevent overflow
                    if (cur > 1000000000L) break;

                    long next = cur * cur;
                    if (next < 0 || next > Long.MAX_VALUE / 2) break;

                    cur = next;
                } else {
                    len += 1;
                    break;
                }
            }

            // If every level had at least two copies, remove one
            // because the middle element should appear only once.
            if (len % 2 == 0) len--;

            ans = Math.max(ans, len);
        }

        return ans;
    }
}