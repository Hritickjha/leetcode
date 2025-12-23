import java.util.*;

public class Solution {
    public int maxTwoEvents(int[][] events) {
        // Sort events by start time
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));

        // Min-heap based on end time
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1])
        );

        int maxValueSoFar = 0;
        int answer = 0;

        for (int[] event : events) {
            int start = event[0];
            int end = event[1];
            int value = event[2];

            // Remove all events that end before current event starts
            while (!pq.isEmpty() && pq.peek()[1] < start) {
                maxValueSoFar = Math.max(maxValueSoFar, pq.poll()[2]);
            }

            // Combine current event with best previous non-overlapping event
            answer = Math.max(answer, maxValueSoFar + value);

            // Add current event to heap
            pq.offer(event);
        }

        return answer;
    }
}
