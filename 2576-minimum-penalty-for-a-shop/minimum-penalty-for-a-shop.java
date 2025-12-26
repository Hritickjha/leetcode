class Solution {
    public int bestClosingTime(String customers) {
        int n = customers.length();
        
        // Initial penalty: shop closed all day
        int penalty = 0;
        for (char c : customers.toCharArray()) {
            if (c == 'Y') penalty++;
        }

        int minPenalty = penalty;
        int bestHour = 0;

        // Try closing at each hour from 1 to n
        for (int i = 0; i < n; i++) {
            if (customers.charAt(i) == 'Y') {
                penalty--; // now open, so no penalty for this Y
            } else {
                penalty++; // open but no customer
            }

            if (penalty < minPenalty) {
                minPenalty = penalty;
                bestHour = i + 1;
            }
        }

        return bestHour;
    }
}
