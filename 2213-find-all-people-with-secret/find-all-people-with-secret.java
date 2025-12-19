import java.util.*;

class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        // Sort meetings by time
        Arrays.sort(meetings, (a, b) -> a[2] - b[2]);

        // People who know the secret
        Set<Integer> secret = new HashSet<>();
        secret.add(0);
        secret.add(firstPerson);

        int i = 0;
        while (i < meetings.length) {
            int time = meetings[i][2];

            // Graph for this time
            Map<Integer, List<Integer>> graph = new HashMap<>();
            Set<Integer> participants = new HashSet<>();

            // Collect all meetings at the same time
            while (i < meetings.length && meetings[i][2] == time) {
                int x = meetings[i][0];
                int y = meetings[i][1];

                graph.computeIfAbsent(x, k -> new ArrayList<>()).add(y);
                graph.computeIfAbsent(y, k -> new ArrayList<>()).add(x);

                participants.add(x);
                participants.add(y);
                i++;
            }

            // BFS to find connected components
            Set<Integer> visited = new HashSet<>();
            for (int person : participants) {
                if (visited.contains(person)) continue;

                Queue<Integer> queue = new LinkedList<>();
                List<Integer> component = new ArrayList<>();
                boolean hasSecret = false;

                queue.offer(person);
                visited.add(person);

                while (!queue.isEmpty()) {
                    int curr = queue.poll();
                    component.add(curr);
                    if (secret.contains(curr)) {
                        hasSecret = true;
                    }

                    for (int nei : graph.getOrDefault(curr, Collections.emptyList())) {
                        if (!visited.contains(nei)) {
                            visited.add(nei);
                            queue.offer(nei);
                        }
                    }
                }

                // If anyone in component knows secret, everyone gets it
                if (hasSecret) {
                    secret.addAll(component);
                }
            }
        }

        return new ArrayList<>(secret);
    }
}
