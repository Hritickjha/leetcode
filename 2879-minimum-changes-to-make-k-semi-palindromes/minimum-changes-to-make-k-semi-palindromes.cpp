#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int minimumChanges(string s, int k) {
        int n = s.size();
        const int INF = 1e9;

        // cost[i][j] = min changes to make s[i..j] a semi-palindrome
        vector<vector<int>> cost(n, vector<int>(n, INF));

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int len = j - i + 1;
                int best = INF;
                for (int d = 1; d < len; d++) {
                    if (len % d != 0) continue;
                    int changes = 0;
                    // each residue class r forms a group: i+r, i+r+d, ...
                    for (int r = 0; r < d; r++) {
                        int cnt = len / d;          // group size
                        int a = i + r;              // first element
                        int b = i + r + (cnt - 1) * d; // last element
                        while (a < b) {
                            if (s[a] != s[b]) changes++;
                            a += d;
                            b -= d;
                        }
                    }
                    best = min(best, changes);
                }
                cost[i][j] = best;
            }
        }

        // dp[p][i] = min cost to split first i chars into p parts
        vector<vector<int>> dp(k + 1, vector<int>(n + 1, INF));
        dp[0][0] = 0;

        for (int p = 1; p <= k; p++) {
            for (int i = 1; i <= n; i++) {
                for (int j = p - 1; j < i; j++) {
                    if (dp[p - 1][j] == INF || cost[j][i - 1] == INF) continue;
                    dp[p][i] = min(dp[p][i], dp[p - 1][j] + cost[j][i - 1]);
                }
            }
        }

        return dp[k][n];
    }
};