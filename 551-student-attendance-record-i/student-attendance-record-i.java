class Solution {
    public boolean checkRecord(String s) {
        int absences = 0;
        int lateCount = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == 'A') {
                absences++;
                if (absences >= 2) {
                    return false; // More than 1 absence
                }
                lateCount = 0; // reset late streak
            } else if (c == 'L') {
                lateCount++;
                if (lateCount >= 3) {
                    return false; // 3 consecutive lates
                }
            } else {
                lateCount = 0; // reset on Present
            }
        }

        return true;
    }
}
