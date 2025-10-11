import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap; // TreeMap to keep keys sorted

class Solution {
    public long maximumTotalDamage(int[] power) {
        // Step 1: Aggregate damage for each distinct spell power.
        // Using TreeMap to automatically sort distinct power values.
        TreeMap<Integer, Long> damageMap = new TreeMap<>();
        for (int p : power) {
            damageMap.put(p, damageMap.getOrDefault(p, 0L) + p);
        }

        // If there are no spells, no damage can be gained.
        if (damageMap.isEmpty()) {
            return 0;
        }

        // Step 2: Extract distinct power values and their aggregated sums into lists
        // for easier indexing in the DP array.
        List<Integer> distinctPowers = new ArrayList<>(damageMap.keySet());
        List<Long> aggregatedSums = new ArrayList<>(damageMap.values());

        int m = distinctPowers.size();
        // dp[i] will store the maximum damage obtainable considering spells
        // with power values up to distinctPowers.get(i).
        long[] dp = new long[m];

        // Base case: For the first distinct power value (distinctPowers.get(0)),
        // we can always choose to cast it, as there are no preceding spells to conflict with.
        dp[0] = aggregatedSums.get(0);

        // Step 3: Fill the dp table from the second distinct power value onwards.
        for (int i = 1; i < m; i++) {
            // Option 1: Do NOT cast any spell with damage distinctPowers.get(i).
            // In this case, the maximum damage is simply the max damage from previous spells.
            long option1 = dp[i-1];

            // Option 2: DO cast all spells with damage distinctPowers.get(i).
            long currentDamage = aggregatedSums.get(i);

            // If we cast distinctPowers.get(i), we cannot cast spells with damage
            // distinctPowers.get(i) - 2, distinctPowers.get(i) - 1, distinctPowers.get(i) + 1, distinctPowers.get(i) + 2.
            // This means we need to find the maximum damage from previously considered spells
            // that have a damage value less than or equal to (distinctPowers.get(i) - 3).
            
            long prevDamageFromDP = 0; // Initialize to 0, if no allowed previous spells are found.
            int searchKey = distinctPowers.get(i) - 3; // The maximum allowed power value from previous steps.
            
            // Use binary search to find the index of searchKey in distinctPowers.
            // Collections.binarySearch returns:
            //   - The index of the search key if it is found (>= 0).
            //   - (-(insertion point) - 1) if the key is not found (< 0).
            //     The insertion point is the index where the key would be inserted to maintain sorted order.
            int searchResult = Collections.binarySearch(distinctPowers, searchKey);
            
            int prevAllowedPowerIndex;
            if (searchResult >= 0) {
                // If searchKey is found, that's the largest allowed power value's index.
                prevAllowedPowerIndex = searchResult;
            } else {
                // If not found, insertionPoint is the index of the first element GREATER than searchKey.
                // So, insertionPoint - 1 is the index of the largest element LESS THAN or EQUAL TO searchKey.
                int insertionPoint = -searchResult - 1;
                prevAllowedPowerIndex = insertionPoint - 1;
            }

            // If a valid previous allowed power index exists (i.e., >= 0),
            // add its corresponding dp value to currentDamage.
            if (prevAllowedPowerIndex >= 0) {
                prevDamageFromDP = dp[prevAllowedPowerIndex];
            }
            
            long option2 = currentDamage + prevDamageFromDP;

            // Choose the maximum of the two options (take or don't take distinctPowers.get(i)).
            dp[i] = Math.max(option1, option2);
        }

        // The maximum total damage achievable is the last value in the dp array,
        // which represents considering all distinct spell power values.
        return dp[m-1];
    }
}