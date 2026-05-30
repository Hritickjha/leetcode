import java.util.*;

class Solution {
    /*
     * APPROACH: Segment Tree (point-set, range-max) + TreeSet
     *
     * Key idea:
     *   - Maintain a sorted set of obstacle positions (0 is always a virtual obstacle).
     *   - For each obstacle position b, store gap[b] = b - predecessor(b) in a segment tree.
     *   - Segment tree supports:
     *       • Point SET (not max-accumulate) at index pos
     *       • Range MAX query over [l, r]
     *
     * When inserting obstacle x (predecessor = prev, successor = succ):
     *   1. Set gap[x] = x - prev          (new gap ending at x)
     *   2. Set gap[succ] = succ - x        (fix succ's gap — it shrinks since x sits before it)
     *
     * For query [2, x, sz]:
     *   • maxCompleteGap = segTree.query(0, x)
     *       → max gap[b] for any obstacle b in [0, x]
     *       → covers all fully contained gaps with right endpoint ≤ x
     *   • partialGap = x - floor(x)
     *       → gap from the last obstacle ≤ x all the way to x
     *   • Answer: max(maxCompleteGap, partialGap) >= sz
     *
     * Why point-set (not max-accumulate)?
     *   When x is inserted between prev and succ, succ's gap decreases from
     *   (succ - prev) to (succ - x). A max-only tree can't reflect this decrease.
     *   Point-set + range-max handles both increases and decreases correctly.
     *
     * Time:  O(Q log M) where M = max coordinate value
     * Space: O(M)
     */

    private int[] tree;
    private int N;

    /** Point-set update: set position pos to val (overwrite, not max). */
    private void update(int node, int start, int end, int pos, int val) {
        if (start == end) {
            tree[node] = val;
            return;
        }
        int mid = (start + end) >>> 1;
        if (pos <= mid) update(2 * node, start, mid, pos, val);
        else            update(2 * node + 1, mid + 1, end, pos, val);
        tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
    }

    /** Range-max query over [l, r]. */
    private int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l) return 0;
        if (l <= start && end <= r) return tree[node];
        int mid = (start + end) >>> 1;
        return Math.max(
            query(2 * node,     start,   mid, l, r),
            query(2 * node + 1, mid + 1, end, l, r)
        );
    }

    public List<Boolean> getResults(int[][] queries) {
        // Find the maximum x value to bound our segment tree size
        int maxX = 1;
        for (int[] q : queries) maxX = Math.max(maxX, q[1]);

        N = maxX + 1;
        tree = new int[4 * N];

        // TreeSet of obstacle positions; 0 is always present as a virtual wall
        TreeSet<Integer> obstacles = new TreeSet<>();
        obstacles.add(0);

        List<Boolean> results = new ArrayList<>();

        for (int[] q : queries) {
            if (q[0] == 1) {
                // ── Type 1: place obstacle at x ──────────────────────────────
                int x = q[1];
                int prev = obstacles.floor(x);          // predecessor (always exists)
                Integer succ = obstacles.higher(x);     // successor (may not exist)

                // Record gap from prev → x
                update(1, 0, N - 1, x, x - prev);

                // Fix successor's gap: it was (succ - prev), now must be (succ - x)
                if (succ != null) {
                    update(1, 0, N - 1, succ, succ - x);
                }

                obstacles.add(x);

            } else {
                // ── Type 2: can we place a block of size sz in [0, x]? ───────
                int x  = q[1];
                int sz = q[2];

                // Max gap among complete intervals [a, b] with b <= x
                int maxGap = query(1, 0, N - 1, 0, x);

                // Also consider the open gap from the last obstacle up to x
                int lastObstacle = obstacles.floor(x);
                maxGap = Math.max(maxGap, x - lastObstacle);

                results.add(maxGap >= sz);
            }
        }

        return results;
    }
}