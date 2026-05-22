import java.util.*;

class Solution {
    public int minimumPairRemoval(int[] nums) {
        int n = nums.length;
        if (n <= 1) return 0;

        long[] val = new long[n];
        for (int i = 0; i < n; i++) val[i] = nums[i];

        int[] prev = new int[n];
        int[] next = new int[n];
        for (int i = 0; i < n; i++) {
            prev[i] = i - 1;
            next[i] = i + 1;
        }

        boolean[] alive = new boolean[n];
        Arrays.fill(alive, true);

        int bad = 0;

        TreeSet<long[]> pairSet = new TreeSet<>((a, b) -> {
            if (a[0] != b[0]) return Long.compare(a[0], b[0]);
            return Long.compare(a[1], b[1]);
        });

        long[] pairSumOf = new long[n];

        for (int i = 0; i < n - 1; i++) {
            long s = val[i] + val[i + 1];
            pairSumOf[i] = s;
            pairSet.add(new long[]{s, i});
            if (val[i] > val[i + 1]) bad++;
        }

        int ops = 0;
        while (bad > 0) {
            long[] best = pairSet.first();
            int idx = (int) best[1];
            pairSet.remove(best);

            int r = next[idx];
            boolean wasBad = val[idx] > val[r];
            long newVal = val[idx] + val[r];

            int p = prev[idx];
            if (p >= 0) {
                pairSet.remove(new long[]{pairSumOf[p], p});
                if (val[p] > val[idx]) bad--;
            }

            int rNext = next[r];
            if (rNext < n) {
                pairSet.remove(new long[]{pairSumOf[r], r});
                if (val[r] > val[rNext]) bad--;
            }

            if (wasBad) bad--;

            val[idx] = newVal;
            alive[r] = false;

            next[idx] = rNext;
            if (rNext < n) prev[rNext] = idx;

            if (p >= 0) {
                long s = val[p] + val[idx];
                pairSumOf[p] = s;
                pairSet.add(new long[]{s, p});
                if (val[p] > val[idx]) bad++;
            }

            if (rNext < n) {
                long s = val[idx] + val[rNext];
                pairSumOf[idx] = s;
                pairSet.add(new long[]{s, idx});
                if (val[idx] > val[rNext]) bad++;
            }

            ops++;
        }

        return ops;
    }
}