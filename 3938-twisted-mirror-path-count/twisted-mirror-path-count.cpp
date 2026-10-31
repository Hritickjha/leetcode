#include <vector>
using namespace std;

class Solution {
public:
    int uniquePaths(vector<vector<int>>& grid) {
        const int MOD = 1e9 + 7;
        int m = grid.size(), n = grid[0].size();
        // a[i][j]: ways arriving at (i,j) while moving right
        // b[i][j]: ways arriving at (i,j) while moving down
        vector<vector<long long>> a(m + 1, vector<long long>(n + 1, 0));
        vector<vector<long long>> b(m + 1, vector<long long>(n + 1, 0));
        a[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                long long ra = a[i][j] % MOD, rb = b[i][j] % MOD;
                if (grid[i][j] == 0) {
                    long long land = (ra + rb) % MOD;
                    if (j + 1 < n) a[i][j + 1] = (a[i][j + 1] + land) % MOD;
                    if (i + 1 < m) b[i + 1][j] = (b[i + 1][j] + land) % MOD;
                } else {
                    // moving right into mirror -> go down
                    if (i + 1 < m) b[i + 1][j] = (b[i + 1][j] + ra) % MOD;
                    // moving down into mirror -> go right
                    if (j + 1 < n) a[i][j + 1] = (a[i][j + 1] + rb) % MOD;
                }
            }
        }
        return (int)((a[m - 1][n - 1] + b[m - 1][n - 1]) % MOD);
    }
};