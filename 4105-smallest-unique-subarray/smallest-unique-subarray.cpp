class Solution {
    typedef unsigned long long ull;
    static const ull MOD = (1ULL << 61) - 1;

    static ull mul(ull a, ull b) {
        __uint128_t t = (__uint128_t)a * b;
        ull res = (ull)(t & MOD) + (ull)(t >> 61);
        if (res >= MOD) res -= MOD;
        return res;
    }

    vector<ull> h, pw;

    bool check(int L, int n) {
        vector<ull> v;
        v.reserve(n - L + 1);
        for (int i = 0; i + L <= n; i++) {
            ull x = h[i + L] + MOD - mul(h[i], pw[L]);
            if (x >= MOD) x -= MOD;
            v.push_back(x);
        }
        sort(v.begin(), v.end());
        int m = v.size();
        for (int i = 0; i < m;) {
            int j = i;
            while (j < m && v[j] == v[i]) j++;
            if (j - i == 1) return true;  // appears exactly once
            i = j;
        }
        return false;
    }

public:
    int smallestUniqueSubarray(vector<int>& nums) {
        int n = nums.size();
        mt19937_64 rng(chrono::steady_clock::now().time_since_epoch().count());
        ull B = rng() % (MOD - 1000000) + 500000;

        h.assign(n + 1, 0);
        pw.assign(n + 1, 1);
        for (int i = 0; i < n; i++) {
            h[i + 1] = mul(h[i], B) + (ull)nums[i];
            if (h[i + 1] >= MOD) h[i + 1] -= MOD;
            pw[i + 1] = mul(pw[i], B);
        }

        int lo = 1, hi = n;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (check(mid, n)) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }
};