import java.util.PriorityQueue;

class Solution {
    public double maxAverageRatio(int[][] classes, int extraStudents) {
        // Max heap based on the gain after adding a student
        PriorityQueue<ClassInfo> pq = new PriorityQueue<>(
            (a, b) -> Double.compare(b.gain(), a.gain())
        );

        // Initialize heap
        for (int[] c : classes) {
            pq.offer(new ClassInfo(c[0], c[1]));
        }

        // Assign extra students
        for (int i = 0; i < extraStudents; i++) {
            ClassInfo best = pq.poll();
            best.addStudent();
            pq.offer(best);
        }

        // Compute final average
        double total = 0.0;
        int n = classes.length;
        for (ClassInfo ci : pq) {
            total += ci.ratio();
        }

        return total / n;
    }

    // Helper class to store class info
    static class ClassInfo {
        int pass, total;

        ClassInfo(int pass, int total) {
            this.pass = pass;
            this.total = total;
        }

        double ratio() {
            return (double) pass / total;
        }

        double gain() {
            return (double)(pass + 1) / (total + 1) - (double) pass / total;
        }

        void addStudent() {
            pass++;
            total++;
        }
    }
}
