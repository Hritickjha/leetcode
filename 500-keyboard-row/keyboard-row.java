import java.util.*;

public class Solution {
    public String[] findWords(String[] words) {
        // Define the three keyboard rows
        String row1 = "qwertyuiop";
        String row2 = "asdfghjkl";
        String row3 = "zxcvbnm";

        List<String> result = new ArrayList<>();

        for (String word : words) {
            String lowerWord = word.toLowerCase();
            if (isInOneRow(lowerWord, row1) || isInOneRow(lowerWord, row2) || isInOneRow(lowerWord, row3)) {
                result.add(word);
            }
        }

        return result.toArray(new String[0]);
    }

    private boolean isInOneRow(String word, String row) {
        for (char c : word.toCharArray()) {
            if (row.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }
}
