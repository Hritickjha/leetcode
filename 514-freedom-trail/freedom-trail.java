import java.util.*;

class Solution {
    public int findRotateSteps(String ring, String key) {
        int n = ring.length();
        Map<Character, List<Integer>> charPos = new HashMap<>();
        
        // Precompute positions of each character in the ring
        for (int i = 0; i < n; i++) {
            char c = ring.charAt(i);
            charPos.computeIfAbsent(c, k -> new ArrayList<>()).add(i);
        }
        
        // Memoization: dp[index in key][position in ring]
        Integer[][] memo = new Integer[key.length()][n];
        
        return dfs(ring, key, 0, 0, charPos, memo);
    }
    
    private int dfs(String ring, String key, int i, int pos, 
                    Map<Character, List<Integer>> charPos, Integer[][] memo) {
        if (i == key.length()) return 0; // finished spelling
        
        if (memo[i][pos] != null) return memo[i][pos];
        
        char target = key.charAt(i);
        int n = ring.length();
        int res = Integer.MAX_VALUE;
        
        // Try all positions of the target character in the ring
        for (int nextPos : charPos.get(target)) {
            int dist = Math.abs(pos - nextPos);
            int step = Math.min(dist, n - dist); // rotate
            res = Math.min(res, step + 1 + dfs(ring, key, i + 1, nextPos, charPos, memo));
        }
        
        return memo[i][pos] = res;
    }
}
