import java.util.Arrays;

class Solution {
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        int n = spells.length;
        int m = potions.length;
        int[] result = new int[n];

        // 1. Sort the potions array to enable binary search.
        Arrays.sort(potions);

        // 2. Iterate through each spell.
        for (int i = 0; i < n; i++) {
            long currentSpellStrength = spells[i];

            // 3. Calculate the minimum required potion strength for the current spell.
            // potion_strength >= success / currentSpellStrength
            // Using long for calculation to prevent overflow and then Math.ceil to get the smallest integer.
            // We use (success + currentSpellStrength - 1) / currentSpellStrength for ceiling division
            // for positive integers without floating point, which is often safer.
            long requiredPotionStrength = (success + currentSpellStrength - 1) / currentSpellStrength;

            // 4. Perform binary search (lower bound) on the sorted potions array.
            // We need to find the first index 'k' such that potions[k] >= requiredPotionStrength.
            // The number of successful potions will then be m - k.
            
            int low = 0;
            int high = m - 1;
            int firstSuccessfulPotionIndex = m; // Default to m, meaning no potion is strong enough

            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (potions[mid] >= requiredPotionStrength) {
                    // This potion is strong enough, and possibly earlier ones too.
                    firstSuccessfulPotionIndex = mid;
                    high = mid - 1; // Try to find an even smaller index
                } else {
                    // This potion is too weak, need stronger ones.
                    low = mid + 1; // Move to the right half
                }
            }
            
            // The number of successful pairs for the current spell is m - firstSuccessfulPotionIndex.
            result[i] = m - firstSuccessfulPotionIndex;
        }

        return result;
    }
}