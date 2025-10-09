import java.util.Arrays;

class Solution {
    public long minTime(int[] skill, int[] mana) {
        int n = skill.length; // Number of wizards
        int m = mana.length;  // Number of potions

        // prefixSkillSum[k] stores the sum of skill[0]...skill[k-1].
        // prefixSkillSum[0] = 0.
        // This is used to calculate the 'cumulative processing time up to wizard k-1' for a potion.
        long[] prefixSkillSum = new long[n];
        if (n > 0) {
            prefixSkillSum[0] = 0; // For wizard 0, sum of skills of previous wizards is 0
            for (int i = 1; i < n; i++) {
                prefixSkillSum[i] = prefixSkillSum[i-1] + skill[i-1];
            }
        }

        // prevPotionFinishTimes[i] stores the time wizard i finishes the *previous* potion.
        // Initially, all wizards are free at time 0.
        long[] prevPotionFinishTimes = new long[n]; 

        // Iterate through each potion
        for (int j = 0; j < m; j++) {
            long[] currentPotionFinishTimes = new long[n];
            
            // Step 1: Determine the start time for Potion j at Wizard 0 (start_0_j).
            // This is the max of (prevPotionFinishTimes[k] - (cumulative processing time for Potion j up to wizard k-1))
            // This ensures no wizard k is conceptually "waiting" for Potion j-1 after Potion j has finished up to them.
            long start_0_j = 0;
            for (int k = 0; k < n; k++) {
                // (long)mana[j] is crucial to prevent overflow before multiplication.
                start_0_j = Math.max(start_0_j, prevPotionFinishTimes[k] - prefixSkillSum[k] * mana[j]);
            }

            // Step 2: Calculate finish time for Wizard 0 on current Potion j.
            // Wizard 0 starts at start_0_j.
            currentPotionFinishTimes[0] = start_0_j + (long)skill[0] * mana[j];

            // Step 3: Calculate finish times for subsequent wizards on current Potion j.
            for (int i = 1; i < n; i++) {
                // Wizard i can start Potion j only when:
                // 1. Wizard i-1 has finished Potion j (currentPotionFinishTimes[i-1])
                // 2. Wizard i has finished Potion j-1 (prevPotionFinishTimes[i])
                long startTime = Math.max(currentPotionFinishTimes[i-1], prevPotionFinishTimes[i]);
                currentPotionFinishTimes[i] = startTime + (long)skill[i] * mana[j];
            }
            
            // The current potion's finish times become the previous potion's finish times for the next iteration.
            prevPotionFinishTimes = currentPotionFinishTimes;
        }

        // The total minimum time is when the last wizard finishes the last potion.
        return prevPotionFinishTimes[n - 1];
    }
}