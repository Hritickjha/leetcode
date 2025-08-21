class Solution {
    public int numSubmat(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[][] height = new int[m][n];

        // Build heights: consecutive ones vertically
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                if (mat[i][j] == 1) {
                    height[i][j] = (i == 0 ? 1 : height[i - 1][j] + 1);
                }
            }
        }

        int result = 0;
        // For each row, use a monotonic stack to count rectangles
        for (int i = 0; i < m; i++) {
            result += countRow(height[i]);
        }

        return result;
    }

    private int countRow(int[] h) {
        int n = h.length;
        int[] sum = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        int res = 0;

        for (int j = 0; j < n; j++) {
            while (!stack.isEmpty() && h[stack.peek()] >= h[j]) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                int prev = stack.peek();
                sum[j] = sum[prev] + h[j] * (j - prev);
            } else {
                sum[j] = h[j] * (j + 1);
            }

            stack.push(j);
            res += sum[j];
        }

        return res;
    }
}
