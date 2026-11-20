class Solution {
public:
    int maxSelectedElements(vector<int>& nums) {
        sort(nums.begin(), nums.end());
        
        // dp[v] = longest consecutive sequence ending at value v
        vector<int> dp(1000003, 0);
        int ans = 1;
        
        for (int x : nums) {
            // Use x + 1 first (relies on old dp[x]), so the same element isn't reused
            dp[x + 1] = dp[x] + 1;
            // Then use x (relies on dp[x - 1])
            dp[x] = dp[x - 1] + 1;
            
            ans = max(ans, max(dp[x], dp[x + 1]));
        }
        
        return ans;
    }
};