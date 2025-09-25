class Solution {
    public int findMinMoves(int[] machines) {
        int total = 0;
        for (int m : machines) {
            total += m;
        }
        int n = machines.length;

        // If not divisible, it's impossible
        if (total % n != 0) {
            return -1;
        }

        int target = total / n;
        int res = 0;
        int curr = 0; // cumulative balance

        for (int load : machines) {
            int balance = load - target;
            curr += balance;
            res = Math.max(res, Math.max(Math.abs(curr), balance));
        }

        return res;
    }
}
