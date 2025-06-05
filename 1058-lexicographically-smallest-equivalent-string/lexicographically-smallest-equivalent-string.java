public class Solution {
    // Union-Find structure
    private int[] parent = new int[26]; // For 26 lowercase English letters

    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        // Initialize parent array: each character is its own parent
        for (int i = 0; i < 26; i++) {
            parent[i] = i;
        }

        // Union characters based on equivalence in s1 and s2
        for (int i = 0; i < s1.length(); i++) {
            union(s1.charAt(i) - 'a', s2.charAt(i) - 'a');
        }

        // Build the smallest equivalent string
        StringBuilder sb = new StringBuilder();
        for (char ch : baseStr.toCharArray()) {
            sb.append((char) (find(ch - 'a') + 'a'));
        }

        return sb.toString();
    }

    // Find with path compression
    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }

    // Union by keeping lexicographically smaller character as root
    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) return;

        if (rootX < rootY) {
            parent[rootY] = rootX;
        } else {
            parent[rootX] = rootY;
        }
    }
}
