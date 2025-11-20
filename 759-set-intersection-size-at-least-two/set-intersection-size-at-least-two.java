import java.util.*;

class Solution {
    public int intersectionSizeTwo(int[][] intervals) {
        // Sort intervals:
        // 1) by end ascending
        // 2) if end ties, by start descending
        Arrays.sort(intervals, (a, b) -> {
            if (a[1] == b[1]) return b[0] - a[0];
            return a[1] - b[1];
        });

        int p1 = -1, p2 = -1; // last two chosen points
        int count = 0;

        for (int[] inter : intervals) {
            int start = inter[0], end = inter[1];

            boolean hasP1 = (p1 >= start);
            boolean hasP2 = (p2 >= start);

            if (hasP1 && hasP2) {
                // Both points already in the interval, do nothing
                continue;
            }

            if (!hasP1 && !hasP2) {
                // Need two points: end-1, end
                p1 = end - 1;
                p2 = end;
                count += 2;
            } else {
                // Only one point inside → add one more (end)
                p1 = p2;
                p2 = end;
                count += 1;
            }
        }

        return count;
    }
}

