import java.util.*;

class Solution {
    // Directions in clockwise order: 0=↘, 1=↙, 2=↖, 3=↗
    private static final int[][] DIR = {{1,1},{1,-1},{-1,-1},{-1,1}};

    public int lenOfVDiagonal(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        // F[d][i][j]: longest valid sequence ending at (i,j) along direction d,
        //             following 1,2,0,2,0,... and must start with 1 somewhere.
        int[][][] F = new int[4][n][m];

        // S2[d][i][j]: length of alternating chain (2,0,2,0,...) starting at (i,j) along d
        // S0[d][i][j]: length of alternating chain (0,2,0,2,...) starting at (i,j) along d
        int[][][] S2 = new int[4][n][m];
        int[][][] S0 = new int[4][n][m];

        // 1) Build F (forward along each direction)
        for (int d = 0; d < 4; d++) {
            buildForwardF(grid, F[d], DIR[d][0], DIR[d][1]);
        }

        // 2) Build S arrays (traverse along opposite direction so "next" along d is the previous visited)
        for (int d = 0; d < 4; d++) {
            int opp = (d + 2) % 4;
            buildSuffixS(grid, S2[d], S0[d], DIR[opp][0], DIR[opp][1], d);
        }

        // 3) Combine: for each cell as a possible turning point at the end of the first leg
        //    take max(F) for no-turn, and F + suffix in clockwise direction for single turn.
        int ans = 0;
        for (int d0 = 0; d0 < 4; d0++) {
            int d1 = (d0 + 1) % 4; // clockwise diagonal
            int dr1 = DIR[d1][0], dc1 = DIR[d1][1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    int L = F[d0][i][j];
                    if (L == 0) continue; // not a valid prefix ending here
                    // no turn
                    if (L > ans) ans = L;
                    // turn clockwise once
                    int ni = i + dr1, nj = j + dc1;
                    if (ni >= 0 && nj >= 0 && ni < n && nj < m) {
                        // Next expected after length L:
                        // if L is odd -> expect 2, else expect 0
                        if ((L & 1) == 1) { // expect 2
                            ans = Math.max(ans, L + S2[d1][ni][nj]);
                        } else { // expect 0
                            ans = Math.max(ans, L + S0[d1][ni][nj]);
                        }
                    }
                }
            }
        }

        return ans;
    }

    // Build F for a given direction (dr,dc) by walking each diagonal forward.
    // Recurrence along the diagonal:
    //   if v==1 -> cur=1
    //   else if prev>0 and expectedAfter(prev)==v -> cur=prev+1
    //   else -> cur=0
    private void buildForwardF(int[][] grid, int[][] F, int dr, int dc) {
        int n = grid.length, m = grid[0].length;

        // Enumerate starts for forward traversal along (dr,dc)
        List<int[]> starts = new ArrayList<>();

        if (dr == 1 && dc == 1) { // ↘ : top row and left col
            for (int j = 0; j < m; j++) starts.add(new int[]{0, j});
            for (int i = 1; i < n; i++) starts.add(new int[]{i, 0});
        } else if (dr == 1 && dc == -1) { // ↙ : top row and right col
            for (int j = 0; j < m; j++) starts.add(new int[]{0, j});
            for (int i = 1; i < n; i++) starts.add(new int[]{i, m - 1});
        } else if (dr == -1 && dc == -1) { // ↖ : bottom row and right col
            for (int j = 0; j < m; j++) starts.add(new int[]{n - 1, j});
            for (int i = n - 2; i >= 0; i--) starts.add(new int[]{i, m - 1});
        } else { // dr == -1 && dc == 1  ↗ : bottom row and left col
            for (int j = 0; j < m; j++) starts.add(new int[]{n - 1, j});
            for (int i = n - 2; i >= 0; i--) starts.add(new int[]{i, 0});
        }

        for (int[] s : starts) {
            int i = s[0], j = s[1];
            int prevLen = 0; // length ending at previous cell on this diagonal
            while (i >= 0 && j >= 0 && i < n && j < m) {
                int v = grid[i][j];
                int cur;
                if (v == 1) {
                    cur = 1;
                } else if (prevLen > 0 && expectedAfter(prevLen) == v) {
                    cur = prevLen + 1;
                } else {
                    cur = 0;
                }
                F[i][j] = cur;
                prevLen = cur;
                i += dr; j += dc;
            }
        }
    }

    // Build S2 and S0 for a target direction d by traversing along the opposite direction (drOpp, dcOpp).
    // At each step, the "previous visited" cell equals the "next along d".
    private void buildSuffixS(int[][] grid, int[][] S2, int[][] S0, int drOpp, int dcOpp, int dTarget) {
        int n = grid.length, m = grid[0].length;

        // Starts for forward traversal along (drOpp, dcOpp)
        List<int[]> starts = new ArrayList<>();

        if (drOpp == 1 && dcOpp == 1) { // ↘
            for (int j = 0; j < m; j++) starts.add(new int[]{0, j});
            for (int i = 1; i < n; i++) starts.add(new int[]{i, 0});
        } else if (drOpp == 1 && dcOpp == -1) { // ↙
            for (int j = 0; j < m; j++) starts.add(new int[]{0, j});
            for (int i = 1; i < n; i++) starts.add(new int[]{i, m - 1});
        } else if (drOpp == -1 && dcOpp == -1) { // ↖
            for (int j = 0; j < m; j++) starts.add(new int[]{n - 1, j});
            for (int i = n - 2; i >= 0; i--) starts.add(new int[]{i, m - 1});
        } else { // ↗
            for (int j = 0; j < m; j++) starts.add(new int[]{n - 1, j});
            for (int i = n - 2; i >= 0; i--) starts.add(new int[]{i, 0});
        }

        for (int[] s : starts) {
            int i = s[0], j = s[1];
            int prevS2 = 0, prevS0 = 0; // values at the next cell in the target direction
            while (i >= 0 && j >= 0 && i < n && j < m) {
                int v = grid[i][j];
                int curS2 = 0, curS0 = 0;
                if (v == 2) curS2 = 1 + prevS0;
                else if (v == 0) curS0 = 1 + prevS2;
                // v==1 -> both remain 0

                S2[i][j] = curS2;
                S0[i][j] = curS0;

                prevS2 = curS2;
                prevS0 = curS0;

                i += drOpp; j += dcOpp;
            }
        }
    }

    private int expectedAfter(int lengthSoFar) {
        // After 1 (len=1), expect 2; after 2 (len=2), expect 0; after 0 (len=3), expect 2; ...
        return (lengthSoFar & 1) == 1 ? 2 : 0;
    }
}
