class Solution {
    public int latestDayToCross(int row, int col, int[][] cells) {
        int[][] grid = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = 1; // 1 means water (flooded), we'll reverse later
            }
        }

        // Flood all cells initially
        for (int[] cell : cells) {
            grid[cell[0] - 1][cell[1] - 1] = 1;
        }

        DSU dsu = new DSU(row * col + 2);
        int topNode = row * col;
        int bottomNode = row * col + 1;

        // Connect top row to topNode and bottom row to bottomNode
        for (int j = 0; j < col; j++) {
            if (grid[0][j] == 0) dsu.union(j, topNode);
            if (grid[row - 1][j] == 0) dsu.union((row - 1) * col + j, bottomNode);
        }

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        // Initially connect all land cells
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1) continue;
                int index = i * col + j;
                for (int[] d : directions) {
                    int ni = i + d[0];
                    int nj = j + d[1];
                    if (ni >= 0 && ni < row && nj >= 0 && nj < col && grid[ni][nj] == 0) {
                        dsu.union(index, ni * col + nj);
                    }
                }
            }
        }

        // If already connected at last day, return last day index
        if (dsu.find(topNode) == dsu.find(bottomNode)) return cells.length;

        // Reverse process: turn water to land from last day to first
        for (int day = cells.length - 1; day >= 0; day--) {
            int r = cells[day][0] - 1;
            int c = cells[day][1] - 1;
            grid[r][c] = 0;
            int index = r * col + c;

            // Connect top/bottom if on edge
            if (r == 0) dsu.union(index, topNode);
            if (r == row - 1) dsu.union(index, bottomNode);

            // Connect to neighbors
            for (int[] d : directions) {
                int nr = r + d[0];
                int nc = c + d[1];
                if (nr >= 0 && nr < row && nc >= 0 && nc < col && grid[nr][nc] == 0) {
                    dsu.union(index, nr * col + nc);
                }
            }

            if (dsu.find(topNode) == dsu.find(bottomNode)) {
                return day;
            }
        }
        return 0;
    }

    class DSU {
        int[] parent;
        int[] rank;

        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) return;
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
        }
    }
}