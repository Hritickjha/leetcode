
class Solution {
public:
    long long minimumCost(int cost1, int cost2, int costBoth,
                          int need1, int need2) {
        long long c1 = min(cost1, costBoth);
        long long c2 = min(cost2, costBoth);

        if (costBoth >= cost1 + cost2) {
            return 1LL * need1 * cost1 + 1LL * need2 * cost2;
        }

        long long both = min(need1, need2);

        long long costA = both * costBoth
                        + 1LL * (need1 - both) * c1
                        + 1LL * (need2 - both) * c2;

        long long costB = 1LL * max(need1, need2) * costBoth;

        return min(costA, costB);
    }
};
