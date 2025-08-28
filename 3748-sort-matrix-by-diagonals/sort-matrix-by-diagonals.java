import java.util.*;

class Solution {
    public int[][] sortMatrix(int[][] grid) {
        int n = grid.length;

        // Map of diagonals (key = row - col, value = list of elements)
        Map<Integer, List<Integer>> diagMap = new HashMap<>();

        // Collect elements of each diagonal
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int key = i - j;
                diagMap.putIfAbsent(key, new ArrayList<>());
                diagMap.get(key).add(grid[i][j]);
            }
        }

        // Sort each diagonal according to rules
        for (Map.Entry<Integer, List<Integer>> entry : diagMap.entrySet()) {
            List<Integer> diag = entry.getValue();
            if (entry.getKey() >= 0) { 
                // bottom-left triangle (including main diagonal) → non-increasing
                diag.sort(Collections.reverseOrder());
            } else { 
                // top-right triangle → non-decreasing
                Collections.sort(diag);
            }
        }

        // Place sorted values back
        Map<Integer, Integer> indexMap = new HashMap<>(); // track index in each diagonal list
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int key = i - j;
                int idx = indexMap.getOrDefault(key, 0);
                grid[i][j] = diagMap.get(key).get(idx);
                indexMap.put(key, idx + 1);
            }
        }

        return grid;
    }
}
