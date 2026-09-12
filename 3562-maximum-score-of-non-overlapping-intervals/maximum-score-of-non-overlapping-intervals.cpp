class Solution {
public:
    struct Interval {
        int l, r, w, idx;
    };

    struct State {
        long long score;
        vector<int> ids;
    };

    // Return true if a is lexicographically smaller than b
    bool smaller(const vector<int>& a, const vector<int>& b) {
        return a < b;
    }

    // Return the better of two states
    State better(const State& a, const State& b) {
        if (a.score != b.score) {
            return (a.score > b.score ? a : b);
        }

        return (a.ids < b.ids ? a : b);
    }

    vector<int> maximumWeight(vector<vector<int>>& intervals) {
        int n = intervals.size();

        vector<Interval> a(n);

        for (int i = 0; i < n; i++) {
            a[i] = {
                intervals[i][0],
                intervals[i][1],
                intervals[i][2],
                i
            };
        }

        // Sort by right endpoint
        sort(a.begin(), a.end(), [](const Interval& x,
                                    const Interval& y) {
            if (x.r != y.r)
                return x.r < y.r;
            return x.idx < y.idx;
        });

        /*
         * prev[i] = number of intervals before i
         * whose right endpoint is strictly smaller than a[i].l.
         *
         * Strictly smaller is important because:
         *
         * [1, 3] and [3, 5]
         *
         * overlap at point 3.
         */
        vector<int> prev(n);

        for (int i = 0; i < n; i++) {
            int lo = 0, hi = i;

            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;

                if (a[mid].r < a[i].l)
                    lo = mid + 1;
                else
                    hi = mid;
            }

            prev[i] = lo;
        }

        /*
         * dp[k][i]:
         * Best answer using the first i intervals
         * while choosing at most k intervals.
         */
        vector<vector<State>> dp(5, vector<State>(n + 1));

        // With 0 intervals allowed, score is 0 and selection is empty.
        for (int i = 0; i <= n; i++) {
            dp[0][i] = {0, {}};
        }

        for (int k = 1; k <= 4; k++) {

            for (int i = 1; i <= n; i++) {

                int cur = i - 1;

                // Option 1: skip current interval
                State skip = dp[k][i - 1];

                // Option 2: take current interval
                State take = dp[k - 1][prev[cur]];

                take.score += a[cur].w;
                take.ids.push_back(a[cur].idx);

                // Keep indices sorted because the answer
                // must be lexicographically compared.
                sort(take.ids.begin(), take.ids.end());

                dp[k][i] = better(skip, take);
            }
        }

        return dp[4][n].ids;
    }
};