#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int maximumSaleItems(vector<vector<int>>& items, int budget) {
        int n = items.size();
        vector<int> val(n);
        int pmin = INT_MAX;
        for (int i = 0; i < n; i++) {
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) c++;
            }
            val[i] = 1 + c;
            pmin = min(pmin, items[i][1]);
        }

        vector<int> dp(budget + 1, 0);

        // 0/1 knapsack: first copy of each item type
        for (int i = 0; i < n; i++) {
            int p = items[i][1];
            for (int b = budget; b >= p; b--) {
                dp[b] = max(dp[b], dp[b - p] + val[i]);
            }
        }

        // unbounded: extra copies of the cheapest item, each worth 1
        for (int b = pmin; b <= budget; b++) {
            dp[b] = max(dp[b], dp[b - pmin] + 1);
        }

        return dp[budget];
    }
};