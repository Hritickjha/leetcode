import java.util.*;

class TaskManager {
    // taskId -> [userId, priority]
    private Map<Integer, int[]> taskMap;
    
    // Max-heap: sort by priority desc, then taskId desc
    private PriorityQueue<int[]> heap;

    public TaskManager(List<List<Integer>> tasks) {
        taskMap = new HashMap<>();
        heap = new PriorityQueue<>((a, b) -> {
            if (b[1] != a[1]) return Integer.compare(b[1], a[1]); // priority desc
            return Integer.compare(b[0], a[0]); // taskId desc
        });

        for (List<Integer> t : tasks) {
            int userId = t.get(0), taskId = t.get(1), priority = t.get(2);
            taskMap.put(taskId, new int[]{userId, priority});
            heap.offer(new int[]{taskId, priority});
        }
    }

    public void add(int userId, int taskId, int priority) {
        taskMap.put(taskId, new int[]{userId, priority});
        heap.offer(new int[]{taskId, priority});
    }

    public void edit(int taskId, int newPriority) {
        int[] info = taskMap.get(taskId);
        info[1] = newPriority;
        taskMap.put(taskId, info);
        heap.offer(new int[]{taskId, newPriority}); // push new version
    }

    public void rmv(int taskId) {
        taskMap.remove(taskId);
        // Heap entry remains but will be skipped later
    }

    public int execTop() {
        while (!heap.isEmpty()) {
            int[] top = heap.poll();
            int taskId = top[0], priority = top[1];
            int[] info = taskMap.get(taskId);
            if (info != null && info[1] == priority) {
                taskMap.remove(taskId); // executed
                return info[0]; // return userId
            }
            // otherwise stale entry, skip
        }
        return -1;
    }
}
