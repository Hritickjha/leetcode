class Solution {
    public int minJumps(int[] arr) {
        int n = arr.length;
        if (n == 1) return 0;

        // Map each value to all indices that have that value
        Map<Integer, List<Integer>> valueToIndices = new HashMap<>();
        for (int i = 0; i < n; i++) {
            valueToIndices.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }

        // BFS
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;
        int steps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            steps++;

            for (int k = 0; k < size; k++) {
                int i = queue.poll();

                // Try i-1, i+1
                int[] neighbors = {i - 1, i + 1};
                for (int next : neighbors) {
                    if (next >= 0 && next < n && !visited[next]) {
                        if (next == n - 1) return steps;
                        visited[next] = true;
                        queue.offer(next);
                    }
                }

                // Try all same-value jumps
                List<Integer> sameVal = valueToIndices.getOrDefault(arr[i], Collections.emptyList());
                for (int next : sameVal) {
                    if (!visited[next]) {
                        if (next == n - 1) return steps;
                        visited[next] = true;
                        queue.offer(next);
                    }
                }
                // Clear to prevent revisiting — crucial for performance!
                valueToIndices.remove(arr[i]);
            }
        }

        return -1; // unreachable
    }
}