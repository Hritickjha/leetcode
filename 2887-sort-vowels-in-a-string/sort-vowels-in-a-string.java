class Solution {
    public String sortVowels(String s) {
        // Define vowels (both lower and upper case)
        String vowels = "aeiouAEIOU";
        
        // Collect vowels from s
        List<Character> vowelList = new ArrayList<>();
        for (char c : s.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                vowelList.add(c);
            }
        }
        
        // Sort vowels by ASCII value
        Collections.sort(vowelList);
        
        // Build result
        StringBuilder result = new StringBuilder();
        int vowelIndex = 0;
        for (char c : s.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                result.append(vowelList.get(vowelIndex++)); // replace with sorted vowel
            } else {
                result.append(c); // consonant stays the same
            }
        }
        
        return result.toString();
    }
}
