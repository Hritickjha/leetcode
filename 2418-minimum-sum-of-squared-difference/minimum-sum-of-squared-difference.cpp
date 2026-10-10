
class Solution {
public:
    long long minSumSquareDiff(vector<int>& nums1, vector<int>& nums2,
                               int k1, int k2) {
        int n = nums1.size();
        long long k = (long long)k1 + k2;
        vector<int> diff(n);

        long long total = 0;
        int maxDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = abs(nums1[i] - nums2[i]);
            total += diff[i];
            maxDiff = max(maxDiff, diff[i]);
        }

        // If all differences can be reduced to zero
        if (total <= k) return 0;

        // Binary search for the maximum difference level
        int left = 0, right = maxDiff;

        while (left < right) {
            int mid = left + (right - left) / 2;
            long long operations = 0;

            for (int d : diff) {
                if (d > mid) {
                    operations += d - mid;
                }
            }

            if (operations <= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        int level = left;
        long long operations = 0;
        long long ans = 0;

        // Reduce every difference to at most 'level'
        for (int d : diff) {
            if (d > level) {
                operations += d - level;
                d = level;
            }
            ans += 1LL * d * d;
        }

        // Use remaining operations to reduce differences at 'level'
        long long remaining = k - operations;

        for (int i = 0; i < n && remaining > 0; i++) {
            if (diff[i] >= level && diff[i] > 0) {
                ans -= 2LL * level - 1;
                remaining--;
            }
        }

        return ans;
    }
};
