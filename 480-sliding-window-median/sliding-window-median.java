import java.util.*;

class Solution {
    private PriorityQueue<Integer> small; // max heap
    private PriorityQueue<Integer> large; // min heap
    private Map<Integer, Integer> delayed;
    private int smallSize, largeSize;

    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        double[] result = new double[n - k + 1];

        small = new PriorityQueue<>(Collections.reverseOrder());
        large = new PriorityQueue<>();
        delayed = new HashMap<>();
        smallSize = 0;
        largeSize = 0;

        // Build initial window
        for (int i = 0; i < k; i++) {
            insert(nums[i]);
        }
        result[0] = getMedian(k);

        // Slide the window
        for (int i = k; i < n; i++) {
            insert(nums[i]);
            erase(nums[i - k]);
            result[i - k + 1] = getMedian(k);
        }

        return result;
    }

    private void insert(int num) {
        if (small.isEmpty() || num <= small.peek()) {
            small.offer(num);
            smallSize++;
        } else {
            large.offer(num);
            largeSize++;
        }
        balance();
    }

    private void erase(int num) {
        delayed.put(num, delayed.getOrDefault(num, 0) + 1);
        if (!small.isEmpty() && num <= small.peek()) {
            smallSize--;
            if (num == small.peek()) prune(small);
        } else {
            largeSize--;
            if (!large.isEmpty() && num == large.peek()) prune(large);
        }
        balance();
    }

    private void balance() {
        if (smallSize > largeSize + 1) {
            large.offer(small.poll());
            smallSize--;
            largeSize++;
            prune(small);
        } else if (largeSize > smallSize) {
            small.offer(large.poll());
            largeSize--;
            smallSize++;
            prune(large);
        }
    }

    private void prune(PriorityQueue<Integer> heap) {
        while (!heap.isEmpty() && delayed.getOrDefault(heap.peek(), 0) > 0) {
            int num = heap.poll();
            delayed.put(num, delayed.get(num) - 1);
        }
    }

    private double getMedian(int k) {
        if (k % 2 == 1) {
            return (double) small.peek();
        } else {
            return ((long) small.peek() + (long) large.peek()) / 2.0;
        }
    }
}

