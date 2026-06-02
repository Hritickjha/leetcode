class Solution {
    public int earliestFinishTime(int[] landStartTime, int[] landDuration, 
                                   int[] waterStartTime, int[] waterDuration) {
        int minFinishTime = Integer.MAX_VALUE;
        
        // Try all combinations of land and water rides
        for (int i = 0; i < landStartTime.length; i++) {
            for (int j = 0; j < waterStartTime.length; j++) {
                
                // Plan 1: Land ride first, then water ride
                int landStart1 = Math.max(0, landStartTime[i]);
                int landEnd1 = landStart1 + landDuration[i];
                int waterStart1 = Math.max(landEnd1, waterStartTime[j]);
                int waterEnd1 = waterStart1 + waterDuration[j];
                minFinishTime = Math.min(minFinishTime, waterEnd1);
                
                // Plan 2: Water ride first, then land ride
                int waterStart2 = Math.max(0, waterStartTime[j]);
                int waterEnd2 = waterStart2 + waterDuration[j];
                int landStart2 = Math.max(waterEnd2, landStartTime[i]);
                int landEnd2 = landStart2 + landDuration[i];
                minFinishTime = Math.min(minFinishTime, landEnd2);
            }
        }
        
        return minFinishTime;
    }
}