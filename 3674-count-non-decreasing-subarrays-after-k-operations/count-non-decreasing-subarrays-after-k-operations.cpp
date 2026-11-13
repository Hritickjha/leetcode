#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    long long countNonDecreasingSubarrays(vector<int>& nums, int k) {
        int n = nums.size();
        long long ans = 0;
        // Process l from n-1 down to 0; window [l, r], r only moves left.
        // Deque stores indices of prefix-max "blocks" of the window, front = l side.
        // Each block: index i (a prefix maximum), covers [i, nextGreaterEq(i)-1] clipped by r.
        deque<int> dq;      // indices, front is smallest index (most recent l)
        long long cost = 0;
        int r = n - 1;
        for (int l = n - 1; l >= 0; --l) {
            // add nums[l] at the front
            // pop blocks whose value <= nums[l]: they are absorbed into l's block
            while (!dq.empty() && nums[dq.front()] <= nums[l]) {
                int i = dq.front();
                dq.pop_front();
                int end = (dq.empty() ? r : min(r, dq.front() - 1));
                // elements i..end had max nums[i], now max nums[l]
                cost += (long long)(nums[l] - nums[i]) * (end - i + 1);
            }
            dq.push_front(l);
            // l itself: running max = nums[l], contribution 0.
            // shrink r while cost > k
            while (cost > k) {
                // remove element r from the window
                cost -= (long long)(nums[dq.back()] - nums[r]);
                if (dq.back() == r) dq.pop_back();
                --r;
            }
            ans += r - l + 1;
        }
        return ans;
    }
};